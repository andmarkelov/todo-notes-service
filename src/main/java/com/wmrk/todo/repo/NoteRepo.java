package com.wmrk.todo.repo;

import com.wmrk.todo.entity.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepo extends CrudRepository<Note, Long> {
    List<Note> getAllByOwnerId(int owner_id);
}
