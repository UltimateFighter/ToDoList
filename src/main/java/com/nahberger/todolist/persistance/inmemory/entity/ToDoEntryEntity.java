package com.nahberger.todolist.persistance.inmemory.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ToDoEntryEntity {
    private String id;
    private String description;
    private LocalDate dueDate;
}
