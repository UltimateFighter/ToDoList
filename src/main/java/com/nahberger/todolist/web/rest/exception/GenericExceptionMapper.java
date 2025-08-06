package com.nahberger.todolist.web.rest.exception;


import com.nahberger.todolist.domain.exception.ResourceAlreadyExistsException;
import com.nahberger.todolist.domain.exception.ResourceNotFoundException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;


@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(final Throwable exception) {
        if (exception instanceof ResourceAlreadyExistsException) {
            LOG.warn(String.format("Resource conflict: %s", exception.getMessage()));
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorMessage(Response.Status.CONFLICT.getStatusCode(), exception.getMessage()))
                    .build();
        } else if (exception instanceof ResourceNotFoundException || exception instanceof jakarta.ws.rs.NotFoundException) {
            LOG.warn(String.format("Resource not found: %s", exception.getMessage()));
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()))
                    .build();
        } else if ((exception instanceof jakarta.validation.ConstraintViolationException) || exception instanceof BadRequestException) {
            LOG.warn(String.format("Validation failed: %s", exception.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Validation failed: " + exception.getMessage()))
                    .build();
        } else {
            LOG.error("Internal server error", exception);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error"))
                    .build();
        }
    }
}
