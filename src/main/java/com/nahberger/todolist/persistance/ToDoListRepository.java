package com.nahberger.todolist.persistance;

import com.nahberger.todolist.domain.exception.ResourceAlreadyExistsException;
import com.nahberger.todolist.domain.exception.ResourceNotFoundException;
import com.nahberger.todolist.domain.model.SortOrder;
import com.nahberger.todolist.domain.model.ToDoEntry;

import java.util.List;

public interface ToDoListRepository {
    List<ToDoEntry> getAll(final SortOrder sortOrder);

    void create(ToDoEntry entry) throws ResourceAlreadyExistsException;

    void delete(final String id) throws ResourceNotFoundException;
}
