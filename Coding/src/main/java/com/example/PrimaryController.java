package com.example;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    @FXML
    CheckMenuItem CheckPerms;

    @FXML
    CheckMenuItem CheckSAST;

    CommandLineClass Test = new CommandLineClass();
    FileRead file = new FileRead();

    //Submit button swaps to the next page... needs to have checks before it can work
    @FXML
    private Button submitButton;

    @FXML
    private Label invalidInput;

    @FXML
private void changePage() throws Exception {
    if (DragandDropLabel.getText().contains("/") && (CheckPerms.isSelected() || CheckSAST.isSelected())) {
        List<Runnable> commands = new ArrayList<>();
        Stage stage = (Stage) submitButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        Parent root = loader.load();
        SecondaryController SecCon = loader.getController();

        // Wrap method calls in lambda expressions
        if (CheckPerms.isSelected()) {
            commands.add(() -> file.ReadManifest(DragandDropLabel.getText()));
        }
        if (CheckSAST.isSelected()) {
            commands.add(() -> {
                try {
                    Test.FlagDangerousFiles(DragandDropLabel.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        SecCon.increase(commands);
    } else {
        DragandDropLabel.setText("Invalid input! Please tick the tests you want to run");
    }
}

    // This runs all the backend in one go to ensure that it is all functional
    @FXML
    Button testButton;


    @FXML
    void runTests() throws Exception{
        Main.main(null);
    }
    
}
