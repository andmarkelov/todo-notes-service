package com.wmrk.todo.service;

import com.wmrk.todo.repo.NoteRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NoteService {
    public NoteRepo noteRepo;

    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public void deleteNoteByOwnerId(int id) {
        noteRepo.deleteByOwnerId(id);
    }
}
