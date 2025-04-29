package com.example.Frontend_Logic;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.Main;
import com.example.CommandLine.CommandLineClass;
import com.example.CommandLine.DAST_Class;
import com.example.CommandLine.FileRead;
import com.example.CommandLine.ReportGenerator;

public class PrimaryController {

    //All file commands that get called


    DAST_Class dynamicTest = new DAST_Class();

    @FXML
    private StackPane DragandDrop;

    //This is the label for the drag and drop box
    @FXML
    private Label DragandDropLabel;

    CommandLineClass Test = new CommandLineClass();
    FileRead file = new FileRead();
    ReportGenerator reportMaker = new ReportGenerator();
    private String testFile;

    @FXML
    public void initialize() {
        Main.StartProgram();
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
            testFile = DragandDropLabel.getText();
        });
    
    }

    @FXML
    CheckMenuItem RunAllTests;

    @FXML
    CheckMenuItem CheckPerms;

    @FXML
    CheckMenuItem CheckSAST;

    @FXML
    CheckMenuItem CheckDAST;

    

    

    //Submit button swaps to the next page... needs to have checks before it can work
    @FXML
    private Button submitButton;

    @FXML
    private Label invalidInput;

    @FXML
private void changePage() throws Exception {
    if (DragandDropLabel.getText().contains("/") && (CheckPerms.isSelected() || CheckSAST.isSelected() || CheckDAST.isSelected() || RunAllTests.isSelected())) {
        System.out.println(testFile);
        List<Runnable> commands = new ArrayList<>();
        Stage stage = (Stage) submitButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        Parent root = loader.load();
        SecondaryController SecCon = loader.getController();

        if (RunAllTests.isSelected() || !RunAllTests.isSelected() && !(CheckPerms.isSelected() | CheckSAST.isSelected() | CheckDAST.isSelected())){
            commands.add(() -> Test.getManifest(DragandDropLabel.getText()));
            commands.add(() -> {
                try {
                    Test.FlagDangerousFiles(DragandDropLabel.getText());
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            commands.add(() -> dynamicTest.DASTCommand(DragandDropLabel.getText()));
        }
        else{
            // Wrap method calls in lambda expressions
            if (CheckPerms.isSelected()) {
                commands.add(() -> Test.getManifest(DragandDropLabel.getText()));
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
            if(CheckDAST.isSelected()){
                commands.add(() -> dynamicTest.DASTCommand(DragandDropLabel.getText()));
            }


        }
        
        commands.add(() -> reportMaker.mdMaker("/home/kali/Fusion-UAST/"));
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

    @FXML
    private Button jumpPageButton;
    @FXML
    void Page3() throws Exception {
        Stage stage = (Stage) jumpPageButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
        Parent root = loader.load(); 
        Scene scene = new Scene(root);
        ResultsController res = loader.getController();
        res.onStart();
        stage.setScene(scene);
    }
    
}
