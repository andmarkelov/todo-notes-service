package com.wmrk.todo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Note {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private boolean done;
    @Column(nullable = false)
    private int ownerId;
}
