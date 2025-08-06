package com.nahberger.todolist.persistance.inmemory;

import com.nahberger.todolist.domain.exception.ResourceAlreadyExistsException;
import com.nahberger.todolist.domain.exception.ResourceNotFoundException;
import com.nahberger.todolist.domain.model.SortOrder;
import com.nahberger.todolist.domain.model.ToDoEntry;
import com.nahberger.todolist.persistance.ToDoListRepository;
import com.nahberger.todolist.persistance.inmemory.entity.ToDoEntryEntity;
import com.nahberger.todolist.persistance.inmemory.mapper.ToDoEntryEntityMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class InMemoryToDoListRepository implements ToDoListRepository {

    private static final Logger LOG = org.jboss.logging.Logger.getLogger(InMemoryToDoListRepository.class);

    private final ConcurrentHashMap<String, ToDoEntryEntity> storage = new ConcurrentHashMap<>();

    final ToDoEntryEntityMapper toDoEntryEntityMapper;

    @Inject
    public InMemoryToDoListRepository(final ToDoEntryEntityMapper toDoEntryEntityMapper) {
        this.toDoEntryEntityMapper = toDoEntryEntityMapper;
    }


    @Override
    public List<ToDoEntry> getAll(final SortOrder sortOrder) {
        LOG.info(String.format("Fetching all ToDo entries with sort order: %s", sortOrder));

        final List<ToDoEntryEntity> toDoEntryEntities = findAllSorted(sortOrder);

        return toDoEntryEntityMapper.toDomain(toDoEntryEntities);
    }

    private List<ToDoEntryEntity> findAllSorted(final SortOrder sortOrder) {
        return storage.values().stream()
                .sorted(getSortOrderComparator(sortOrder))
                .toList();
    }

    @Override
    public void create(final ToDoEntry entry) throws ResourceAlreadyExistsException {
        final String id = entry.getId();
        if (storage.containsKey(entry.getId())) {
            LOG.warn(String.format("Attempt to create ToDoEntry failed: Resource with ID %s already exists", id));
            throw new ResourceAlreadyExistsException("ToDoEntryEntity with ID " + id + " already Exists found");
        }

        final ToDoEntryEntity toDoEntryEntity = toDoEntryEntityMapper.toEntity(entry);

        storage.put(id, toDoEntryEntity);
        LOG.info(String.format("Created new ToDoEntryEntity with ID %s", id));
    }

    @Override
    public void delete(final String id) throws ResourceNotFoundException {
        final ToDoEntryEntity removed = storage.remove(id);
        if (removed == null) {
            LOG.warn(String.format("Attempt to delete ToDoEntryEntity failed: Resource with ID %s not found", id));
            throw new ResourceNotFoundException("ToDoEntryEntity with ID " + id + " not found");
        }
        LOG.info(String.format("Deleted ToDoEntryEntity with ID %s", id));
    }

    private Comparator<ToDoEntryEntity> getSortOrderComparator(final SortOrder sortOrder) {
        return sortOrder == SortOrder.DESC
                ? Comparator.comparing(ToDoEntryEntity::getDueDate).reversed()
                : Comparator.comparing(ToDoEntryEntity::getDueDate);
    }
}
