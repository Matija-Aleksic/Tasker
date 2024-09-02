package com.javaprojektni.tasker.Interfaces;

import com.javaprojektni.tasker.model.Task;
import com.javaprojektni.tasker.model.User;

import java.util.ArrayList;

public interface ReadInterface {
    void readLogFile();
    ArrayList<User> readUsers();
    ArrayList<User> readUsers(int id);
    ArrayList<Task> readTasks();
    ArrayList<Task> readTasks(int id);
}
