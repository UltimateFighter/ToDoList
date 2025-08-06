package com.nahberger.todolist.domain;

import com.nahberger.todolist.domain.model.ToDoEntry;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class ToDoListFactory {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ToDoEntry create(final ToDoEntry toDoEntry) {
        final String id = FORMATTER.format(toDoEntry.getDueDate());
        return new ToDoEntry(id, toDoEntry.getDescription(), toDoEntry.getDueDate());
    }
}
