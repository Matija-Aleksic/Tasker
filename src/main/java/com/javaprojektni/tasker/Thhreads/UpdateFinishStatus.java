package com.javaprojektni.tasker.Thhreads;

import com.javaprojektni.tasker.Database.Database;
import com.javaprojektni.tasker.model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateFinishStatus implements Runnable {
    private final int id;
    private List<Task> tasks;


    public UpdateFinishStatus(int id) {
        this.id = id;
    }

    public void run() {
        List<Task> tasks = Database.getAllTasks();

        List<Task> tasksToUpdate = tasks.stream().filter(task -> {
            java.sql.Date dueDate = task.getDueDate();
            return dueDate != null && dueDate.toLocalDate().isBefore(LocalDate.now());
        }).collect(Collectors.toList());

        for (Task task : tasksToUpdate) {
            task.setFinalizedStatus(true);
            try {
                Database.updateTask(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("UpdateFinishStatus");
    }

}
