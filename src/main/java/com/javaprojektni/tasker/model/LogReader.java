package com.javaprojektni.tasker.model;

import com.javaprojektni.tasker.model.Activity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogReader {

    private static final String FILE_PATH = "src/main/java/com/javaprojektni/tasker/Files/changes.dat";

    public synchronized static List<Activity> readLogsFromFile() {
        List<Activity> activities = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj;
            while ((obj = inputStream.readObject()) != null) {
                if (obj instanceof Activity) {
                    activities.add((Activity) obj);
                } else if (obj instanceof List) {
                    activities.addAll((List<Activity>) obj);
                }
            }
        } catch (EOFException e) {
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return activities;
    }
}
