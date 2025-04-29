package com.example.Frontend_Logic;

import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ResultsController 
{
    
    @FXML
    private TextArea ReportText;

    private static final String FILE_PATH = "/home/kali/Fusion-UAST/Final_Report.md";

    @FXML
    public void onStart() 
    {
        try 
        {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            ReportText.setText(content);
        } 
        
        catch (IOException e) 
        {
            ReportText.setText("Error reading file: " + e.getMessage());
        }
    }
}

