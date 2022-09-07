package com.wmrk.todo.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.wmrk.todo.controller.NotesController;
import com.wmrk.todo.domain.Note;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, EntityModel<Note>> {

    @Override
    public EntityModel<Note> toModel(Note note) {

        return EntityModel.of(note, //
                linkTo(methodOn(NotesController.class).getNote(note)).withSelfRel(),
                linkTo(methodOn(NotesController.class).getAllNotes(null)).withRel("notes"));
    }
}