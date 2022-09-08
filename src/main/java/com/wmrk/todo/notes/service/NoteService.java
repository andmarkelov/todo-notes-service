package com.wmrk.todo.notes.service;

import com.wmrk.todo.notes.domain.Note;
import com.wmrk.todo.notes.repo.NoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class NoteService {
    public final NoteRepo noteRepo;

    public void deleteNotesByOwnerId(int ownerId) {
        noteRepo.deleteByOwnerId(ownerId);
    }

    public Note createNote(Note newNote, int ownerId) {
        newNote.setId(0);
        newNote.setOwnerId(ownerId);
        noteRepo.save(newNote);
        return newNote;
    }

    public Note updateNote(Note curNote, @RequestBody Note newNote) {
        BeanUtils.copyProperties(newNote, curNote, "id", "ownerId");
        noteRepo.save(curNote);
        return curNote;
    }
}
