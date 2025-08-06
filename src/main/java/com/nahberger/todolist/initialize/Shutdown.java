package com.nahberger.todolist.initialize;

import org.jboss.logging.Logger;

public class Shutdown {

    private static final Logger LOG = Logger.getLogger(Shutdown.class);

    public static void initHoock() {
        Runtime.getRuntime().addShutdownHook(new Thread(Shutdown::log));
    }

    public static void log() {
        LOG.info("ToDo List stopped.");
    }
}
