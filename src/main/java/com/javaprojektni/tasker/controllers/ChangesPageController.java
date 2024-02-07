package com.javaprojektni.tasker.controllers;

import com.javaprojektni.tasker.model.Activity;
import com.javaprojektni.tasker.model.LogReader;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

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

    private void updateView(ObservableList<Activity> logEntries) {
        ChangesTableView.setItems(logEntries);
    }

    private void readLogFile() {
        ObservableList<Activity> logEntries = FXCollections.observableArrayList();

        ArrayList<Activity> activities = (ArrayList<Activity>) LogReader.readLogsFromFile();
        logEntries.addAll(activities);

        updateView(logEntries);
    }
}
