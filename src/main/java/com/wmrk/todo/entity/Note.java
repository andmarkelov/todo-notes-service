package com.wmrk.todo.entity;

import lombok.Data;

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
    private int ownerId;
}
