package com.example;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;

public class PrimaryController {

    @FXML
    private StackPane DragandDrop;

    //This is the label for the drag and drop box
    @FXML
    private Label DragandDropLabel;

    @FXML
    public void initialize() {
        //When a file is dragged onto the drag and drop box it will run this command (getting the file path)
        DragandDrop.setOnDragOver(event -> {
            if (event.getGestureSource() != DragandDrop && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        DragandDrop.setOnDragDropped(event -> {
            Dragboard dragBoard = event.getDragboard();
            boolean check = false;
            if (dragBoard.hasFiles()) {
                check = true;
                List<File> files = dragBoard.getFiles();
                String filePaths = files.stream()
                                        .map(File::getAbsolutePath)
                                        .reduce((a, b) -> a + "\n" + b)
                                        .orElse("No files dropped");
                DragandDropLabel.setText(filePaths);
            }
            event.setDropCompleted(check);
            event.consume();
        });
    
    }
    //Submit button swaps to the next page... needs to have checks before it can work
    @FXML
    private Button submitButton;

    @FXML
    private void changePage() throws Exception {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        Parent root = loader.load();
        SecondaryController SecCon = loader.getController();
       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        SecCon.increase(2);
    }
    // This runs all the backend in one go to ensure that it is all functional
    @FXML
    Button testButton;

    @FXML
    void runTests() throws Exception{
        Main.main(null);
    }
    
}
