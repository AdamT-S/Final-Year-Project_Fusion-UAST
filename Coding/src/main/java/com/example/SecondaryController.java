package com.example;

import java.io.IOException;
import java.util.List;
import com.example.CommandLine.CommandLineClass;
import com.example.CommandLine.FileRead;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class SecondaryController 
{
    @FXML
    private ProgressBar CodeLoading;

    CommandLineClass Test = new CommandLineClass();
    FileRead file = new FileRead();
    
    float progress = 0;

    public void increase(List<Runnable> commandlist)
    {
        System.out.println("The increase function has been succesfully called");
    

        for (Runnable command : commandlist) 
        {
            command.run();
            System.out.println("The for loop has been successfully called");  
        }
        
        Stage stage = (Stage) CodeLoading.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
        Parent root;
        try 
        {
            root = loader.load(); 
            Scene scene = new Scene(root);
            ResultsController res = loader.getController();
            res.onStart();
            stage.setScene(scene);
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }  
    }
}
