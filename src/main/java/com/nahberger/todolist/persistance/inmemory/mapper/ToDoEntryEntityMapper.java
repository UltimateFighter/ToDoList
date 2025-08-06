package com.nahberger.todolist.persistance.inmemory.mapper;

import com.nahberger.todolist.domain.model.ToDoEntry;
import com.nahberger.todolist.persistance.inmemory.entity.ToDoEntryEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ToDoEntryEntityMapper {

    public ToDoEntry toDomain(final ToDoEntryEntity toDoEntryEntity) {
        if (toDoEntryEntity == null) {
            return null;
        }
        return new ToDoEntry(toDoEntryEntity.getId(), toDoEntryEntity.getDescription(), toDoEntryEntity.getDueDate());
    }

    public List<ToDoEntry> toDomain(final List<ToDoEntryEntity> toDoEntryEntity) {
        return toDoEntryEntity.stream()
                .map(this::toDomain)
                .toList();
    }

    public ToDoEntryEntity toEntity(final ToDoEntry entry) {
        return new ToDoEntryEntity(entry.getId(), entry.getDescription(), entry.getDueDate());
    }
}