package com.javaprojektni.tasker.Database;

import com.javaprojektni.tasker.model.Task;
import com.javaprojektni.tasker.model.TaskBuilder;
import com.javaprojektni.tasker.model.User;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class Database {

    // Database URL, username, and password
    private static final String URL = "jdbc:h2:tcp://localhost/~/tasker";
    private static final String USER = "student";
    private static final String PASSWORD = "student";
    private static final String DATABASE_FILE = "dat/database.properties";

    private Connection connection;


    private static Properties loadDatabaseProperties() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String urlBazePodataka = svojstva.getProperty("db.url");
        String korisnickoIme = svojstva.getProperty("db.user");
        String lozinka = svojstva.getProperty("db.password");
        return svojstva;
    }

    public Connection openConnection() throws SQLException, IOException {
        Properties properties = loadDatabaseProperties();

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return connection = getConnection(url, user, password);
    }

    public void createTask(Task task) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tasks (name, task_owner_id, task_body, finalized_status, due_date, date_created) VALUES (?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, task.getName());
            preparedStatement.setInt(2, task.getTaskOwnerId());
            preparedStatement.setString(3, task.getTaskBody());
            preparedStatement.setBoolean(4, task.isFinalizedStatus());
            preparedStatement.setDate(5, java.sql.Date.valueOf(task.getDueDate().toString()));
            preparedStatement.setDate(6, java.sql.Date.valueOf(task.getDateCreated().toString()));

            preparedStatement.executeUpdate();
            System.out.println("Task created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Task task) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET name = ?, task_body = ?, finalized_status = ?, due_date = ? WHERE id = ?")) {

            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getTaskBody());
            preparedStatement.setBoolean(3, task.isFinalizedStatus());
            preparedStatement.setDate(4, java.sql.Date.valueOf(task.getDueDate().toString()));
            preparedStatement.setInt(5, task.getId());

            preparedStatement.executeUpdate();
            System.out.println("Task updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks"); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TaskBuilder taskBuilder = new TaskBuilder();
                taskBuilder.setId(resultSet.getInt("id"));
                taskBuilder.setName(resultSet.getString("name"));
                taskBuilder.setTaskOwnerId(resultSet.getInt("task_owner_id"));
                taskBuilder.setTaskBody(resultSet.getString("task_body"));
                taskBuilder.setFinalizedStatus(resultSet.getBoolean("finalized_status"));
                taskBuilder.setDueDate(resultSet.getDate("due_date"));
                taskBuilder.setDateCreated(resultSet.getDate("date_created"));
                tasks.add(taskBuilder.createTask());
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users"); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setMail(resultSet.getString("email_address"));
                users.add(user);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void deleteTask(int taskId) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();
            System.out.println("Task deleted successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addTaskInvitee(int taskId, int userId) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO task_invitees (task_id, user_id) VALUES (?, ?)")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
            System.out.println("Task invitee added successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskInvitee(int taskId, int userId) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM task_invitees WHERE task_id = ? AND user_id = ?")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
            System.out.println("Task invitee deleted successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDueDate(int taskId, Date newDueDate) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET due_date = ? WHERE id = ?")) {

            preparedStatement.setDate(1, newDueDate);
            preparedStatement.setInt(2, taskId);

            preparedStatement.executeUpdate();
            System.out.println("Due date changed successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void markTaskAsCompleted(int taskId) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET finalized_status = TRUE WHERE id = ?")) {

            preparedStatement.setInt(1, taskId);

            preparedStatement.executeUpdate();
            System.out.println("Task marked as completed successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTaskName(int taskId, String newName) {
        // Implement update logic as needed
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET name = ? WHERE id = ?")) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, taskId);

            preparedStatement.executeUpdate();
            System.out.println("Task name changed successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTaskOwner(int taskId, int newOwnerId) {
        // Implement update logic as needed
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET task_owner_id = ? WHERE id = ?")) {

            preparedStatement.setInt(1, newOwnerId);
            preparedStatement.setInt(2, taskId);

            preparedStatement.executeUpdate();
            System.out.println("Task owner changed successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Method to change the body of a task
    public void changeTaskBody(int taskId, String newBody) {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET task_body = ? WHERE id = ?")) {

            preparedStatement.setString(1, newBody);
            preparedStatement.setInt(2, taskId);

            preparedStatement.executeUpdate();
            System.out.println("Task body changed successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public Task getTaskById(int taskId) {
        Task task = null;
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE id = ?")) {

            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setTaskOwnerId(resultSet.getInt("task_owner_id"));
                task.setTaskBody(resultSet.getString("task_body"));
                task.setFinalizedStatus(resultSet.getBoolean("finalized_status"));
                task.setDueDate(resultSet.getDate("due_date"));
                task.setDateCreated(Date.valueOf(resultSet.getTimestamp("date_created").toLocalDateTime().toLocalDate()));
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return task;
    }

    public ArrayList<Task> getTasksByUser(int userId) {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE task_owner_id = ?")) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setTaskOwnerId(resultSet.getInt("task_owner_id"));
                task.setTaskBody(resultSet.getString("task_body"));
                task.setFinalizedStatus(resultSet.getBoolean("finalized_status"));
                task.setDueDate(resultSet.getDate("due_date"));
                task.setDateCreated(Date.valueOf(resultSet.getTimestamp("date_created").toLocalDateTime().toLocalDate()));

                tasks.add(task);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public ArrayList<Task> getCompletedTasks() {
        ArrayList<Task> completedTasks = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE finalized_status = TRUE")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setTaskOwnerId(resultSet.getInt("task_owner_id"));
                task.setTaskBody(resultSet.getString("task_body"));
                task.setFinalizedStatus(resultSet.getBoolean("finalized_status"));
                task.setDueDate(resultSet.getDate("due_date"));
                task.setDateCreated(Date.valueOf(resultSet.getTimestamp("date_created").toLocalDateTime().toLocalDate()));
                completedTasks.add(task);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return completedTasks;
    }

    public ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE finalized_status = FALSE")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setTaskOwnerId(resultSet.getInt("task_owner_id"));
                task.setTaskBody(resultSet.getString("task_body"));
                task.setFinalizedStatus(resultSet.getBoolean("finalized_status"));
                task.setDueDate(resultSet.getDate("due_date"));
                task.setDateCreated(Date.valueOf(resultSet.getTimestamp("date_created").toLocalDateTime().toLocalDate()));

                incompleteTasks.add(task);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return incompleteTasks;
    }

    public List<Task> getOverdueTasks() {
        List<Task> overdueTasks = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE due_date < CURRENT_DATE AND finalized_status = FALSE")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setTaskOwnerId(resultSet.getInt("task_owner_id"));
                task.setTaskBody(resultSet.getString("task_body"));
                task.setFinalizedStatus(resultSet.getBoolean("finalized_status"));
                task.setDueDate(resultSet.getDate("due_date"));
                task.setDateCreated(Date.valueOf(resultSet.getTimestamp("date_created").toLocalDateTime().toLocalDate()));

                overdueTasks.add(task);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return overdueTasks;
    }

    public List<Task> getTasksDueSoon(int daysUntilDue) {
        List<Task> tasksDueSoon = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE due_date <= CURRENT_DATE + ? AND finalized_status = FALSE")) {

            preparedStatement.setInt(1, daysUntilDue);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setTaskOwnerId(resultSet.getInt("task_owner_id"));
                task.setTaskBody(resultSet.getString("task_body"));
                task.setFinalizedStatus(resultSet.getBoolean("finalized_status"));
                task.setDueDate(resultSet.getDate("due_date"));
                task.setDateCreated(Date.valueOf(resultSet.getTimestamp("date_created").toLocalDateTime().toLocalDate()));

                tasksDueSoon.add(task);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return tasksDueSoon;
    }

    public void deleteCompletedTasks() {
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tasks WHERE finalized_status = TRUE")) {

            preparedStatement.executeUpdate();
            System.out.println("Completed tasks deleted successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getTaskInvitees(int taskId) {
        ArrayList<User> taskInvitees = new ArrayList<>();
        try (Connection connection = openConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT users.* FROM users INNER JOIN task_invitees ON users.user_id = task_invitees.user_id WHERE task_invitees.task_id = ?")) {

            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setMail(resultSet.getString("mail_address"));
                taskInvitees.add(user);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return taskInvitees;
    }


}



