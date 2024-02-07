package com.javaprojektni.tasker.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogWriter {

    private static final String FILE_PATH = "src/main/java/com/javaprojektni/tasker/Files/changes.dat";

    public static void writeLog(Activity activity) {
        try {
            List<Activity> activities = readLogsFromFile();
            activities.add(activity); // Add the new activity to the list

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                outputStream.writeObject(activities); // Write the updated list back to the file
                System.out.println("Log entry has been written to file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Activity> readLogsFromFile() throws IOException {
        List<Activity> activities = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                Object obj = inputStream.readObject();
                if (obj instanceof List) {
                    activities.addAll((List<Activity>) obj); // Add existing activities to the list
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return activities;
    }

    public static String getChanges(Task oldTask, Task newTask) {
        StringBuilder changes = new StringBuilder();

        if (!oldTask.getName().equals(newTask.getName())) {
            changes.append("Name changed from ").append(oldTask.getName()).append(" to ").append(newTask.getName()).append("; ");
        }
        if (!oldTask.getTaskBody().equals(newTask.getTaskBody())) {
            changes.append("Task body changed from ").append(oldTask.getTaskBody()).append(" to ").append(newTask.getTaskBody()).append("; ");
        }
        if (!oldTask.isFinalizedStatus().equals(newTask.isFinalizedStatus())) {
            changes.append("Finalized status changed from ").append(oldTask.isFinalizedStatus()).append(" to ").append(newTask.isFinalizedStatus()).append("; ");
        }
        if (!oldTask.getDueDate().equals(newTask.getDueDate())) {
            changes.append("Due date changed from ").append(oldTask.getDueDate()).append(" to ").append(newTask.getDueDate()).append("; ");
        }
        return changes.toString();
    }

    private static void createFileIfNotExists(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
            System.out.println("File created: " + filePath);
        }
    }
}
