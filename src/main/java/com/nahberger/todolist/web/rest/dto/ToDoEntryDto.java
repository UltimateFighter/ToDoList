package com.nahberger.todolist.web.rest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class ToDoEntryDto extends AbstractToDoEntryDto {

    final private String id;

    public ToDoEntryDto(final String id, final String description, final String dueDate) {
        super(description, dueDate);
        this.id = id;
    }
}
