package com.example;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    

    public void mdMaker(String filePath){
        File FinalReport = new File(filePath.substring(filePath.lastIndexOf("/")-1));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FinalReport));
            writer.write("#" + filePath.substring(filePath.lastIndexOf("/")-1) + " Report\n\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SAST_Portion(String file_output)
    {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_output.substring(file_output.lastIndexOf("/")-1)));
            BufferedReader reader = new BufferedReader(new FileReader(file_output));
            String line = reader.readLine();
            writer.write("##Static Application Security Analysis Results\n");
            writer.write("###**Static Application Security Analysis(SAST) is a method of vulnerability detection where an application analyses an programs source code*. This program then checks for vulnerabilities within the software by checking commands for any potential issues that are associated with those commands.**\n");
            while(line != null)
            {
                writer.write(line); 
                line = reader.readLine();  
            }

            writer.write("\n\n");
            
            
            writer.close();
            reader.close();
        }
        catch (IOException e){

        }
    }

    

    
}
