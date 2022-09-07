package com.wmrk.todo.controller;

import com.wmrk.todo.entity.Note;
import com.wmrk.todo.jwt.JwtAuthentication;
import com.wmrk.todo.repo.NoteRepo;
import com.wmrk.todo.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notes")
// check object for owner's permissions:
@PreAuthorize("hasRole('ADMIN') || #owned == null || #owned.ownerId == authentication.userId")
public class NotesController {

    private NoteRepo noteRepo;
    private NoteService noteService;

    @Autowired
    public NotesController(NoteRepo noteRepo, NoteService noteService) {
        this.noteRepo = noteRepo;
        this.noteService = noteService;
    }

    @GetMapping
    public Iterable<Note> getAllNotes(JwtAuthentication auth) {
        return noteRepo.getAllByOwnerId(auth.getUserId());
    }

    @GetMapping("{id}")
    public Note getNote(@PathVariable("id") @P("owned") Note note) {
        return note;
    }

    @PostMapping
    public Note createNote(@RequestBody Note newNote, JwtAuthentication auth) {
        newNote.setId(0);
        newNote.setOwnerId(auth.getUserId());
        noteRepo.save(newNote);
        return newNote;
    }

    @PatchMapping("{id}")
    public Note updateNote(
            @PathVariable("id") @P("owned") Note curNote,
            @RequestBody Note newNote
    ) {
        BeanUtils.copyProperties(newNote, curNote, "id", "ownerId");
        noteRepo.save(curNote);
        return curNote;
    }

    @DeleteMapping
    void deleteAllNotes(JwtAuthentication auth) {
        //noteRepo.deleteByOwnerId(auth.getUserId());
        noteService.deleteNoteByOwnerId(auth.getUserId());

    }

    @DeleteMapping("{id}")
    void deleteNote(@PathVariable("id") @P("owned") Note curNote) {
        noteRepo.deleteById(curNote.getId());

    }

}
