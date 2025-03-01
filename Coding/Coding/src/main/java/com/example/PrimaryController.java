package com.example;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;



import java.io.File;
import java.util.List;

public class PrimaryController {

    @FXML
    private StackPane DragnDrop;

    @FXML
    private Label DragnDropLabel;

    @FXML
    public void initialize() {
        DragnDrop.setOnDragOver(event -> {
            if (event.getGestureSource() != DragnDrop && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        DragnDrop.setOnDragDropped(event -> {
            Dragboard dragBoard = event.getDragboard();
            boolean check = false;
            if (dragBoard.hasFiles()) {
                check = true;
                List<File> files = dragBoard.getFiles();
                String filePaths = files.stream()
                                        .map(File::getAbsolutePath)
                                        .reduce((a, b) -> a + "\n" + b)
                                        .orElse("No files dropped");
                DragnDropLabel.setText(filePaths);
            }
            event.setDropCompleted(check);
            event.consume();
        });
    
    }
    @FXML
    private Button submitButton;

    @FXML
    private void changePage() throws Exception {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    
    }

    @FXML
    Button testButton;

    @FXML
    void runTests() throws Exception{
        Main.main(null);
    }
    
}
