package com.nahberger.todolist.web.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractToDoEntryDto {

    @NotBlank(message = "Description must be given and not be blank")
    private String description;

    @NotNull(message = "Due date must not be null")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Due date must be in the format DD.MM.YYYY")
    private String dueDate;

}
