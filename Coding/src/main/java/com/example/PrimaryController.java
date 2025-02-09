package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
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
}
