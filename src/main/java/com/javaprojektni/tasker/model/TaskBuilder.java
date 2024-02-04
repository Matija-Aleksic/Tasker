package com.javaprojektni.tasker.model;

import java.sql.Date;

public class TaskBuilder {
    private int id;
    private String name;
    private int taskOwnerId;
    private String taskBody;
    private boolean finalizedStatus;
    private Date dueDate;
    private Date dateCreated;

    public TaskBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TaskBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TaskBuilder setTaskOwnerId(int taskOwnerId) {
        this.taskOwnerId = taskOwnerId;
        return this;
    }

    public TaskBuilder setTaskBody(String taskBody) {
        this.taskBody = taskBody;
        return this;
    }

    public TaskBuilder setFinalizedStatus(boolean finalizedStatus) {
        this.finalizedStatus = finalizedStatus;
        return this;
    }

    public TaskBuilder setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public TaskBuilder setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public Task createTask() {
        return new Task(id, name, taskOwnerId, taskBody, finalizedStatus, dueDate, dateCreated);
    }
}