package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class SecondaryController {
    @FXML
    private ProgressBar CodeLoading;
    
    float progress = 0;

    public void increase(int num_Functions){
        System.out.println("The increase function has been succesfully called");
        float progress_addit = 1.0f/num_Functions;

        for(int i = 0; i < num_Functions; i++)
        {
            System.out.println("The for loop has been successfully called");
            progress += progress_addit;
            CodeLoading.setProgress(progress);
               
            
        }
        
        if(progress == 1)
        {
            Stage stage = (Stage) CodeLoading.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
            Parent root;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                ResultsController res = loader.getController();
                res.onStart();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        else
        {
            System.exit(1);
        }
        
    }
}