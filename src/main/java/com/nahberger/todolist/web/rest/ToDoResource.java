package com.nahberger.todolist.web.rest;


import com.nahberger.todolist.domain.ToDoService;
import com.nahberger.todolist.domain.model.SortOrder;
import com.nahberger.todolist.domain.model.ToDoEntry;
import com.nahberger.todolist.web.rest.dto.CreateToDoEntryDto;
import com.nahberger.todolist.web.rest.dto.ToDoEntryDto;
import com.nahberger.todolist.web.rest.mapper.ToDoRestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ToDoResource {

    final ToDoService toDoService;

    final ToDoRestMapper toDoRestMapper;

    @Inject
    public ToDoResource(final ToDoService toDoService, final ToDoRestMapper toDoRestMapper) {
        this.toDoService = toDoService;
        this.toDoRestMapper = toDoRestMapper;
    }

    @GET
    public Response getAll(@QueryParam("sortOrder") final String sortOrderValue) {
        final SortOrder sortOrder = parseAndValidateSortOrder(sortOrderValue);
        final List<ToDoEntry> entries = toDoService.getAll(sortOrder);
        final List<ToDoEntryDto> toDoEntryDtos = toDoRestMapper.toDto(entries);
        return Response.ok(toDoEntryDtos).build();
    }

    @POST
    public Response create(@Valid final CreateToDoEntryDto createToDoEntryDto) {
        final ToDoEntry toDoEntry = toDoRestMapper.toDomain(createToDoEntryDto);
        final ToDoEntry createdToDoEntry = toDoService.create(toDoEntry);
        final ToDoEntryDto toDoEntryDto = toDoRestMapper.toDto(createdToDoEntry);
        return Response.status(Response.Status.CREATED)
                .entity(toDoEntryDto)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        toDoService.delete(id);
        return Response.noContent().build();
    }

    private SortOrder parseAndValidateSortOrder(final String sortOrderValue) {
        try {
            return SortOrder.fromString(sortOrderValue);
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException(String.format("Sort oder has to be value of %s",
                    Arrays.toString(SortOrder.values())), exception);
        }
    }
}
