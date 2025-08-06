package com.nahberger.todolist.domain;

import com.nahberger.todolist.domain.model.ToDoEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToDoListFactoryTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @InjectMocks
    ToDoListFactory toDoListFactory;

    @Test
    void create() {
        // given
        final ToDoEntry toDoEntry = mock(ToDoEntry.class);
        when(toDoEntry.getDescription()).thenReturn("Description");
        final LocalDate dueDate = LocalDate.now();
        when(toDoEntry.getDueDate()).thenReturn(dueDate);
        final String expectedId = FORMATTER.format(dueDate);

        // when
        final ToDoEntry newToDoEntry = toDoListFactory.create(toDoEntry);

        // then
        assertThat(newToDoEntry.getId()).isEqualTo(expectedId);
        assertThat(newToDoEntry.getDescription()).isEqualTo("Description");
        assertThat(newToDoEntry.getDueDate()).isEqualTo(dueDate);
    }

}