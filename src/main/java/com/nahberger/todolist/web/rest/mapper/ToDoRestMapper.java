package com.nahberger.todolist.web.rest.mapper;


import com.nahberger.todolist.domain.model.ToDoEntry;
import com.nahberger.todolist.web.rest.dto.CreateToDoEntryDto;
import com.nahberger.todolist.web.rest.dto.ToDoEntryDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class ToDoRestMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ToDoEntry toDomain(final CreateToDoEntryDto createToDoEntryDto) {
        final LocalDate dueDate = convertToDueDate(createToDoEntryDto.getDueDate());
        return new ToDoEntry(createToDoEntryDto.getDescription(), dueDate);
    }

    public ToDoEntryDto toDto(final ToDoEntry domain) {
        final String dueDate = formatDueDate(domain.getDueDate());
        return new ToDoEntryDto(domain.getId(), domain.getDescription(), dueDate);
    }

    public List<ToDoEntryDto> toDto(final List<ToDoEntry> toDoEntries) {
        return toDoEntries.stream()
                .map(this::toDto)
                .toList();
    }

    private LocalDate convertToDueDate(final String dueDate) {
        return LocalDate.parse(dueDate, FORMATTER);
    }

    private String formatDueDate(final LocalDate dueDate) {
        return dueDate.format(FORMATTER);
    }
}
