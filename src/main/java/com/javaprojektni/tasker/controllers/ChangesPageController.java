package com.javaprojektni.tasker.controllers;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import com.javaprojektni.tasker.model.Activity;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ChangesPageController {


    @FXML
    private TableView<Activity> ChangesTableView;
    @FXML
    private TableColumn<Activity, String> dateTableColumn;
    @FXML
    private TableColumn<Activity, String> madeByTableColumn;
    @FXML
    private TableColumn<Activity, String> descriiptionTableColumn;

    @FXML
    private void initialize() {
        descriiptionTableColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMadeBys()));
        madeByTableColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDesc()));
        dateTableColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMadeOn().toString()));




        readLogFile();
    }

    private void readLogFile() {
        String filePath = "src/main/java/com/javaprojektni/tasker/Files/changes.dat";
        ObservableList<Activity> logEntries = FXCollections.observableArrayList();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj;
            while ((obj = inputStream.readObject()) != null) {
                if (obj instanceof Activity) {
                    logEntries.add((Activity) obj);
                } else if (obj instanceof ArrayList) {
                    logEntries.addAll((ArrayList<Activity>) obj);
                }
            }
        } catch (EOFException e) {
            // Reached end of file
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        ChangesTableView.setItems(logEntries);
    }

}

