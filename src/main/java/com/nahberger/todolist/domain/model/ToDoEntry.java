package com.nahberger.todolist.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ToDoEntry {

    final String id;
    final String description;
    final LocalDate dueDate;

    public ToDoEntry(final String description, final LocalDate dueDate) {
        this.id = null;
        this.description = description;
        this.dueDate = dueDate;
    }
}
