package com.nahberger.todolist.persistance.inmemory;

import com.nahberger.todolist.domain.exception.ResourceAlreadyExistsException;
import com.nahberger.todolist.domain.exception.ResourceNotFoundException;
import com.nahberger.todolist.domain.model.SortOrder;
import com.nahberger.todolist.domain.model.ToDoEntry;
import com.nahberger.todolist.persistance.inmemory.entity.ToDoEntryEntity;
import com.nahberger.todolist.persistance.inmemory.mapper.ToDoEntryEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InMemoryToDoListRepositoryTest {

    @Mock
    ToDoEntryEntityMapper toDoEntryEntityMapper;

    @InjectMocks
    InMemoryToDoListRepository inMemoryToDoListRepository;

    @Test
    void getAllAsc() {
        // given
        final ToDoEntryEntity toDoEntryEntity1 = mockAndCreateEntity("ID1", LocalDate.now());
        final ToDoEntryEntity toDoEntryEntity2 = mockAndCreateEntity("ID2", LocalDate.now().plusDays(1));

        // when
        inMemoryToDoListRepository.getAll(SortOrder.ASC);

        // then
        final ArgumentCaptor<List<ToDoEntryEntity>> toDoEntryEntitiesCaptor = ArgumentCaptor.forClass(List.class);
        verify(toDoEntryEntityMapper).toDomain(toDoEntryEntitiesCaptor.capture());
        final List<ToDoEntryEntity> toDoEntryEntities = toDoEntryEntitiesCaptor.getValue();
        assertThat(toDoEntryEntities).hasSize(2)
                .containsExactly(toDoEntryEntity1, toDoEntryEntity2);
    }

    @Test
    void getAllDesc() {
        // given
        final ToDoEntryEntity toDoEntryEntity1 = mockAndCreateEntity("ID1", LocalDate.now());
        final ToDoEntryEntity toDoEntryEntity2 = mockAndCreateEntity("ID2", LocalDate.now().plusDays(1));

        // when
        inMemoryToDoListRepository.getAll(SortOrder.DESC);

        // then
        final ArgumentCaptor<List<ToDoEntryEntity>> toDoEntryEntitiesCaptor = ArgumentCaptor.forClass(List.class);
        verify(toDoEntryEntityMapper).toDomain(toDoEntryEntitiesCaptor.capture());
        final List<ToDoEntryEntity> toDoEntryEntities = toDoEntryEntitiesCaptor.getValue();
        assertThat(toDoEntryEntities).hasSize(2)
                .containsExactly(toDoEntryEntity2, toDoEntryEntity1);
    }

    @Test
    void create() {
        // given
        final ToDoEntry toDoEntry = mock(ToDoEntry.class);
        when(toDoEntry.getId()).thenReturn("ID");
        final ToDoEntryEntity toDoEntryEntity = mock(ToDoEntryEntity.class);
        when(toDoEntryEntityMapper.toEntity(toDoEntry)).thenReturn(toDoEntryEntity);

        // when
        inMemoryToDoListRepository.create(toDoEntry);

        // then
        verify(toDoEntryEntityMapper, times(1)).toEntity(toDoEntry);
    }

    @Test
    void createDuplicatedThrowsException() {
        // given
        final ToDoEntry toDoEntry = mock(ToDoEntry.class);
        when(toDoEntry.getId()).thenReturn("ID");

        final ToDoEntryEntity toDoEntryEntity = mock(ToDoEntryEntity.class);
        when(toDoEntryEntityMapper.toEntity(toDoEntry)).thenReturn(toDoEntryEntity);
        inMemoryToDoListRepository.create(toDoEntry);

        // when / then
        assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() ->
                        inMemoryToDoListRepository.create(toDoEntry)
                );
    }

    @Test
    void delete() {
        // given
        final ToDoEntry toDoEntry = mock(ToDoEntry.class);
        when(toDoEntry.getId()).thenReturn("ID");

        final ToDoEntryEntity toDoEntryEntity = mock(ToDoEntryEntity.class);
        when(toDoEntryEntityMapper.toEntity(toDoEntry)).thenReturn(toDoEntryEntity);
        inMemoryToDoListRepository.create(toDoEntry);

        // when / then
        assertThatCode(() -> inMemoryToDoListRepository.delete("ID")).doesNotThrowAnyException();
    }

    @Test
    void deleteNoneExisting() {
        // when / then
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() ->
                        inMemoryToDoListRepository.delete("ID")
                );
    }

    private ToDoEntryEntity mockAndCreateEntity(final String id, final LocalDate now) {
        final ToDoEntry toDoEntry1 = mock(ToDoEntry.class);
        when(toDoEntry1.getId()).thenReturn(id);

        final ToDoEntryEntity toDoEntryEntity1 = mock(ToDoEntryEntity.class);
        when(toDoEntryEntity1.getDueDate()).thenReturn(now);
        when(toDoEntryEntityMapper.toEntity(toDoEntry1)).thenReturn(toDoEntryEntity1);

        inMemoryToDoListRepository.create(toDoEntry1);

        return toDoEntryEntity1;
    }
}