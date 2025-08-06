package com.nahberger.todolist;


import com.nahberger.todolist.initialize.Initializer;
import com.nahberger.todolist.initialize.Shutdown;
import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api/v1")
@Dependent
public class ToDoApplication extends Application {

    public ToDoApplication() {
        init();
    }

    private void init() {
        try {
            Initializer.init();
        } catch (final Exception exception) {
            Shutdown.log();
        }
    }
}