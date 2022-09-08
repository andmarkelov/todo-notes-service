package com.wmrk.todo.notes.repo;

import com.wmrk.todo.notes.domain.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepo extends CrudRepository<Note, Long> {
    List<Note> getAllByOwnerId(int ownerId);
    void deleteByOwnerId(int ownerId);
    void deleteAll();
}
