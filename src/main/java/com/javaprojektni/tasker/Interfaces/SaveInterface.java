package com.javaprojektni.tasker.Interfaces;

import com.javaprojektni.tasker.model.Task;
import com.javaprojektni.tasker.model.User;

import java.util.ArrayList;

public interface SaveInterface {
    ArrayList<User> saveUsers();

    ArrayList<User> saveUsers(int id);

    ArrayList<Task> saveTasks();

    ArrayList<Task> saveTasks(int id);
}
