package com.nahberger.todolist.web.rest.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.MDC;

import java.util.UUID;

@Provider
public class RequestIdFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public static final String REQUEST_ID_HEADER = "requestId";

    @Override
    public void filter(final ContainerRequestContext requestContext) {
        String requestId = requestContext.getHeaderString(REQUEST_ID_HEADER);
        if (requestId == null || requestId.trim().isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }

        requestContext.getHeaders().putSingle(REQUEST_ID_HEADER, requestId);
        MDC.put(REQUEST_ID_HEADER, requestId);
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        final String requestId = requestContext.getHeaderString(REQUEST_ID_HEADER);
        if (requestId != null) {
            responseContext.getHeaders().putSingle(REQUEST_ID_HEADER, requestId);
        }
    }
}
