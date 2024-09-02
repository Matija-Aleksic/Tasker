package com.javaprojektni.tasker.Thhreads;

import com.javaprojektni.tasker.controllers.HomeController;

public class TaskRefresh implements Runnable {
    private final int id;

    public TaskRefresh(int id) {
        this.id = id;
    }

    public void run() {
        HomeController.refreshTasks();
        System.out.println("taskRefresh");
        System.out.println();
    }
}
