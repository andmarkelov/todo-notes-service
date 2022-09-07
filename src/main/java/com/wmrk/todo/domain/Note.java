package com.wmrk.todo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
