package com.nahberger.todolist.initialize;

import com.nahberger.todolist.initialize.banner.BannerPrinter;
import org.jboss.logging.Logger;

public class Initializer {

    private static final Logger LOG = Logger.getLogger(Initializer.class);

    public static void init() {
        onStartUp();
        onShutDown();
    }

    private static void onStartUp() {
        log();
    }

    private static void onShutDown() {
        Shutdown.initHoock();
    }

    private static void log() {
        BannerPrinter.print();
        LOG.info("ToDo List started.");
    }

}
