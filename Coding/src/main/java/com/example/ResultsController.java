package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ResultsController {
    @FXML
    private ListView<String> PermsList;

    private ObservableList<String> attempt = FXCollections.observableArrayList();
   

    @FXML
    public void onStart()
    {
        for (int i = 0; i <= 10; i++)
        {
            String str = Integer.toString(i);
            attempt.add(str);
        }
    }

    @FXML
    private StackPane ManifestPerms;

    @FXML
    private Label ManifestPermsLabel;



    @FXML
    public void initialize() {
        onStart();
        PermsList.setItems(attempt);
    }

}

