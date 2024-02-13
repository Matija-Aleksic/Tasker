package com.javaprojektni.tasker.model;

import com.javaprojektni.tasker.Database.Database;
import com.javaprojektni.tasker.Interfaces.Sealed;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;


public final class Task implements Sealed {
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

    public String getTaskOwner() throws SQLException, IOException {
        Database database = new Database();
        database.openConnection();

        return database.getAllUsers().stream().filter(user -> this.id == user.getUserId()).findFirst().map(user -> user.getName() + " " + user.getSurname()).orElse(null);
    }

    public String getTaskBody() {
        return taskBody;
    }

    public void setTaskBody(String taskBody) {
        this.taskBody = taskBody;
    }

    public String isFinalizedStatus() {
        return finalizedStatus ? "TRUE" : "FALSE";
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


    public String getInvitees() throws SQLException, IOException {
        Database database = new Database();
        database.openConnection();
        ArrayList<User> users = database.getTaskInvitees(this.id);
        StringBuilder ans = new StringBuilder();
        for (User user : users) {
            ans.append(user.getName()).append(" ").append(user.getSurname()).append(",");
        }
        if (!ans.isEmpty()) {
            ans.deleteCharAt(ans.length() - 1);
        }
        return ans.toString();
    }

    @Override
    public String ids() {
        return null;
    }
}
