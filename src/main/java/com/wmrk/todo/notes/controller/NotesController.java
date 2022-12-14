package com.wmrk.todo.notes.controller;

import com.wmrk.todo.notes.domain.Note;
import com.wmrk.todo.notes.repo.NoteRepo;
import com.wmrk.todo.notes.service.NoteService;
import com.wmrk.todo.notes.hateoas.NoteModelAssembler;
import com.wmrk.todo.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/notes")
// check object for owner's permissions:
@PreAuthorize("hasRole('ADMIN') || #owned == null || #owned.ownerId == authentication.userId")
@RequiredArgsConstructor
public class NotesController {

    private final NoteRepo noteRepo;
    private final NoteService noteService;
    private final NoteModelAssembler modelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Note>>> getAllNotes(JwtAuthentication auth) {
        return ResponseEntity.ok(
                modelAssembler.toCollectionModel(
                        noteRepo.getAllByOwnerId(auth.getUserId())
                ));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Note>> getNote(@PathVariable("id") @P("owned") Note note) {
        return wrapNote(note);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Note>> createNote(@RequestBody Note newNote, JwtAuthentication auth) {
        return wrapNote(noteService.createNote(newNote, auth.getUserId()));
    }

    @PatchMapping("{id}")
    public ResponseEntity<EntityModel<Note>> updateNote(
            @PathVariable("id") @P("owned") Note curNote,
            @RequestBody Note newNote
    ) {
        return wrapNote(noteService.updateNote(curNote, newNote));
    }

    @DeleteMapping
    void deleteAllNotes(JwtAuthentication auth) {
        noteService.deleteNotesByOwnerId(auth.getUserId());
    }

    @DeleteMapping("{id}")
    void deleteNote(@PathVariable("id") @P("owned") Note curNote) {
        noteRepo.deleteById(curNote.getId());
    }

    private ResponseEntity<EntityModel<Note>> wrapNote(Note note) {
        return Optional.of(note)
                .map(modelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
