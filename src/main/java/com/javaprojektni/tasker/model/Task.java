package com.javaprojektni.tasker.model;

import java.sql.Date;


public class Task {
    private int id;
    private String name;
    private int taskOwnerId;
    private String taskBody;
    private boolean finalizedStatus;
    private Date dueDate;
    private Date dateCreated;

    public Task(int id, String name, int taskOwnerId, String taskBody, boolean finalizedStatus, Date dueDate, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.taskOwnerId = taskOwnerId;
        this.taskBody = taskBody;
        this.finalizedStatus = finalizedStatus;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskOwnerId() {
        return taskOwnerId;
    }

    public void setTaskOwnerId(int taskOwnerId) {
        this.taskOwnerId = taskOwnerId;
    }

    public String getTaskBody() {
        return taskBody;
    }

    public void setTaskBody(String taskBody) {
        this.taskBody = taskBody;
    }

    public boolean isFinalizedStatus() {
        return finalizedStatus;
    }

    public void setFinalizedStatus(boolean finalizedStatus) {
        this.finalizedStatus = finalizedStatus;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


}
