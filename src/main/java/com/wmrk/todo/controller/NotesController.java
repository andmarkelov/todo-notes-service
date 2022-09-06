package com.wmrk.todo.controller;

import com.wmrk.todo.entity.Note;
import com.wmrk.todo.jwt.JwtAuthentication;
import com.wmrk.todo.repo.NoteRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notes")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PreAuthorize("hasRole('ADMIN') || #owned == null || #owned.userId == authentication.userId")
public class NotesController {

    private NoteRepo noteRepo;

    @Autowired
    public NotesController(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @GetMapping
    public Iterable<Note> getAllNotes() {
        return noteRepo.getAllByOwnerId(1);
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

    @PutMapping("{id}")
    public Note update(
            @PathVariable("id") @P("owned") Note curNote,
            @RequestBody Note newNote
    ) {
        BeanUtils.copyProperties(newNote, curNote, "id", "owner");
        noteRepo.save(curNote);
        return curNote;
    }


}
