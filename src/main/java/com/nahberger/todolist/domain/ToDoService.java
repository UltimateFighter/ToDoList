package com.nahberger.todolist.domain;


import com.nahberger.todolist.domain.exception.ResourceAlreadyExistsException;
import com.nahberger.todolist.domain.exception.ResourceNotFoundException;
import com.nahberger.todolist.domain.model.SortOrder;
import com.nahberger.todolist.domain.model.ToDoEntry;
import com.nahberger.todolist.persistance.ToDoListRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class ToDoService {

    private static final Logger LOG = Logger.getLogger(ToDoService.class);

    final ToDoListFactory toDoListFactory;

    final ToDoListRepository toDoListRepository;

    @Inject
    public ToDoService(final ToDoListFactory toDoListFactory, final ToDoListRepository toDoListRepository) {
        this.toDoListFactory = toDoListFactory;
        this.toDoListRepository = toDoListRepository;
    }

    public List<ToDoEntry> getAll(final SortOrder sortOrder) {
        LOG.info(String.format("Fetching all ToDo entries with sort order: %s", sortOrder));

        return toDoListRepository.getAll(sortOrder);
    }

    public ToDoEntry create(final ToDoEntry toDoEntry) {
        final ToDoEntry newToDoEntry = toDoListFactory.create(toDoEntry);
        final String id = newToDoEntry.getId();
        try {
            toDoListRepository.create(newToDoEntry);
            LOG.info(String.format("Created new ToDoEntry with ID %s", id));
        } catch (final ResourceAlreadyExistsException exception) {
            final String msg = String.format("Attempt to create ToDoEntry failed: To do entry with due date '%s'"
                    + " already exists", id);
            LOG.warn(msg, exception);
            throw new ResourceAlreadyExistsException(msg);
        }
        return newToDoEntry;
    }

    public void delete(final String id) {
        deleteToDoEntry(id);
        LOG.info(String.format("Deleted ToDoEntry with ID %s", id));
    }

    private void deleteToDoEntry(final String id) {
        try {
            toDoListRepository.delete(id);
        } catch (final ResourceNotFoundException exception) {
            final String msg = String.format("Attempt to delete ToDoEntry failed: To do entry with with ID %s"
                    + " not found", id);
            LOG.warn(msg, exception);
            throw new ResourceNotFoundException(msg);
        }
    }
}
