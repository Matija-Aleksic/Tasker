package com.javaprojektni.tasker.Thhreads;

import com.javaprojektni.tasker.model.LogWriter;

public class WriteChanges implements Runnable{
    private int id;

    public WriteChanges(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        LogWriter.writeLogsToFile();
        System.out.println("WriteChanges");
    }
}
