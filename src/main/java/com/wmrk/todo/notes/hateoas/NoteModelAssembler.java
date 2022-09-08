package com.wmrk.todo.notes.hateoas;

import com.wmrk.todo.notes.controller.NotesController;
import com.wmrk.todo.notes.domain.Note;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, EntityModel<Note>> {
    
    @Override
    public EntityModel<Note> toModel(Note note) {

        return EntityModel.of(note, //
                WebMvcLinkBuilder.linkTo(methodOn(NotesController.class).getNote(note)).withSelfRel(),
                linkTo(methodOn(NotesController.class).getAllNotes(null)).withRel("notes"));
    }
}