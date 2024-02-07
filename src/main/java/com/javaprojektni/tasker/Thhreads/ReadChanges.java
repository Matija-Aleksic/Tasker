package com.javaprojektni.tasker.Thhreads;

import com.javaprojektni.tasker.model.LogReader;

public class ReadChanges implements Runnable {
    private int id;

    public ReadChanges(int id) {
        this.id = id;
    }

    public void run() {
        LogReader.readLogsFromFile();
        System.out.println("ReadChanges");
    }
}
