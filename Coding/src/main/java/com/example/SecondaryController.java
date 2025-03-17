package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class SecondaryController {
    @FXML
    private ProgressBar CodeLoading;
    
    double progress = 0;

    public void increase(int num_Functions){
        float progress_addit = 1/num_Functions;
        for(float i = 0; i < 1; i += progress_addit)
        {
            progress += progress_addit;
            CodeLoading.setProgress(progress); 
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                System.err.println("The sleep loop didnt work");
            }
            
        }
    }
}