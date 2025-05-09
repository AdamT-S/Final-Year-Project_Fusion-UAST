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

import com.example.CommandLine.APK_Manager;
import com.example.CommandLine.CommandLineClass;
import com.example.CommandLine.DAST_Class;
import com.example.CommandLine.FileRead;
import com.example.CommandLine.ReportGenerator;
import com.example.CommandLine.SAST_Class;

public class PrimaryController {

    //All file commands that get called
    APK_Manager apkManager = new APK_Manager();
    CommandLineClass Test = new CommandLineClass();
    DAST_Class dynamicTest = new DAST_Class();
    FileRead file = new FileRead();
    ReportGenerator reportMaker = new ReportGenerator();
    SAST_Class staticTest = new SAST_Class();


    //This section applies the drag and drop feature on the first page
    @FXML
    private StackPane DragandDrop;

    @FXML
    private Label DragandDropLabel;
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


    //This section runs the different test types
    @FXML
    CheckMenuItem RunAllTests;

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
private void changePage() throws Exception 
{
   
    
    if (DragandDropLabel.getText().contains("/") && CheckSAST.isSelected() || CheckDAST.isSelected() || RunAllTests.isSelected())
    {
        //Checkpoint for debugging
        System.out.println(testFile);

        //List of commands that will run during the tests
        List<Runnable> commands = new ArrayList<>();

        //preping the next stage
        Stage stage = (Stage) submitButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        Parent root = loader.load();
        SecondaryController SecCon = loader.getController();
        boolean thisPathWillChange = false;
        String replacePath = DragandDropLabel.getText();

        if(replacePath.contains(".apk"))
        {
            apkManager.decompiler(replacePath);
             
            thisPathWillChange = true; 
        }

        if(thisPathWillChange == true)
        {
            testChecker("/home/kali/Fusion-UAST/apk", commands);
        }
        else if(thisPathWillChange == false)
        {
            testChecker(replacePath, commands);
        }
        else
        {
            System.out.println("Something went really wrong to fail true and false");
        }

        
        commands.add(() -> reportMaker.mdMaker("/home/kali/Fusion-UAST/"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        SecCon.increase(commands);
    } 

    else 
    {
        DragandDropLabel.setText("Invalid input! Please tick the tests you want to run");
    }
}
    public void testChecker(String filepath, List<Runnable> commands)
    {
        //adding tests for running
        if (RunAllTests.isSelected())
        {   
        
            commands.add(() -> {
                try
                {
                    staticTest.Semgrep_run(filepath);
                    
                }
                
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            });
            commands.add(() -> dynamicTest.DASTCommand(filepath));
        }
        //If all tests not selected, then individual ones will be
        else
        {
            if (CheckSAST.isSelected())
            {
                commands.add(() -> {
                    try
                    {
                        staticTest.Semgrep_run(filepath);
                    } 
                    
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                });
            }
            if(CheckDAST.isSelected())
            {
                commands.add(() -> dynamicTest.DASTCommand(filepath));
            }


        }
    }
    

    // This runs all the backend in one go to ensure that it is all functional
    @FXML
    Button testButton;


    @FXML
    void runTests() throws Exception
    {
        Main.main(null);
    }

    @FXML
    private Button jumpPageButton;
    @FXML
    void Page3() throws Exception 
    {
        Stage stage = (Stage) jumpPageButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
        stage.setTitle("Loading...");
        Parent root = loader.load(); 
        Scene scene = new Scene(root);
        ResultsController res = loader.getController();
        res.onStart();
        stage.setScene(scene);
    }
    
}
