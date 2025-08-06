package com.nahberger.todolist.domain;

import com.nahberger.todolist.domain.exception.ResourceNotFoundException;
import com.nahberger.todolist.domain.model.SortOrder;
import com.nahberger.todolist.domain.model.ToDoEntry;
import com.nahberger.todolist.persistance.ToDoListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {


    @Mock
    ToDoListFactory toDoListFactory;

    @Mock
    ToDoListRepository toDoListRepository;

    @InjectMocks
    ToDoService toDoService;


    @Test
    void getAll() {
        // given
        final ToDoEntry toDoEntry1 = mock(ToDoEntry.class);
        final ToDoEntry toDoEntry2 = mock(ToDoEntry.class);
        when(toDoListRepository.getAll(SortOrder.ASC)).thenReturn(List.of(toDoEntry1, toDoEntry2));

        // when
        final List<ToDoEntry> toDoEntries = toDoService.getAll(SortOrder.ASC);

        // then
        assertThat(toDoEntries).hasSize(2)
                .containsExactly(toDoEntry1, toDoEntry2);
        verify(toDoListRepository, times(1)).getAll(SortOrder.ASC);
    }

    @Test
    void create() {
        // given
        final ToDoEntry toDoEntry = mock(ToDoEntry.class);
        final ToDoEntry newToDoEntry = mock(ToDoEntry.class);
        when(toDoListFactory.create(toDoEntry)).thenReturn(newToDoEntry);

        // when
        toDoService.create(toDoEntry);

        // then
        verify(toDoListRepository, times(1)).create(newToDoEntry);
    }

    @Test
    void delete() {
        // when
        toDoService.delete("ID");

        // then
        verify(toDoListRepository, times(1)).delete("ID");
    }

    @Test
    void deleteNoneExisting() {
        // given
        final String id = "ID";
        doThrow(ResourceNotFoundException.class).when(toDoListRepository).delete(id);

        // when / then
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> toDoListRepository.delete(id));
    }
}