package com.javaprojektni.tasker.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LogWriter {

    private static final String FILE_PATH = "src/main/java/com/javaprojektni/tasker/Files/changes.dat";
    static ArrayList<Activity> changes = new ArrayList<>();

    public static void writeLog(Activity activity) {
        changes.add(activity);
    }

    public synchronized static void writeLogsToFile() {
        ArrayList<Activity> previousLogs = (ArrayList<Activity>) LogReader.readLogsFromFile();
        changes.addAll(previousLogs);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(changes);
            changes.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
