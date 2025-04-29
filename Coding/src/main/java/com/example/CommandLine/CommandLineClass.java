package com.example.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class CommandLineClass{
    

    void CommandLineRun(String CommandString, String FilePath){
        try{
            // Print the directory for debugging purposes
            System.out.println("Directory: " + FilePath);
            // Split the command using a more robust method
            String[] commandArray = {"/bin/bash", "-c", CommandString}; // Using bash to handle the command
            //Build the command that will be run in the terminal
            ProcessBuilder command = new ProcessBuilder(commandArray);
            //Lets me see all the errors in one location
            command.redirectErrorStream(true);
            //Makes sure that the files can run where they are needed
            if (FilePath != null){
                ChangeDirectory(FilePath, command);
            }
            //Runs the command
            final Process process = command.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine())!= null){
                System.out.println(line);
            }
        }
        //catches errors to prevent the code from crashing
        catch (IOException e){
            e.printStackTrace();
        } 
    }
    

    public void ComplexCommandRun(String[] compileCommand){

        ProcessBuilder compileBuilder = new ProcessBuilder(compileCommand);
        compileBuilder.redirectErrorStream(true);
        Process compileProcess;
        try {
            compileProcess = compileBuilder.start();
            compileProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } 
        System.out.println("Command worked");
        
    }
    
    //This lists all the files in a directory. (Unused. Only for early testing)
    void ListDir()
    {
        String ldir = "ls";
        CommandLineRun(ldir, "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main");
    }

    //This allows any function called to be directed to the correct directory also for testing purposes
    void ChangeDirectory(String DirectoryName, ProcessBuilder DirChange){
        try{
            DirChange.directory(new File(DirectoryName));
            System.out.println(".....\n Directory Change Successful \n....");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }  
    
}
