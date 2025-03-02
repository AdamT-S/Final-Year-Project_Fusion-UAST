package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;;

public class CommandLineClass{
    void CommandLineRun(String CommandString, String FilePath){
        try{
            // Print the directory for debugging purposes
            System.out.println("Directory: " + FilePath);
            // Split the command using a more robust method
            String[] commandArray = {"/bin/bash", "-c", CommandString}; // Using bash to handle the command
            
            ProcessBuilder command = new ProcessBuilder(commandArray);
            command.redirectErrorStream(true);
            if (FilePath != null){
                ChangeDirectory(FilePath, command);
            }
            final Process process = command.start();
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine())!= null){
                System.out.println(line);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    void Decompiler(String APK){    
        String Decompile = "apktool d" + APK;
        CommandLineRun(Decompile, null);

    }
    void ListDir()
    {
        String ldir = "ls";
        CommandLineRun(ldir, "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main");
    }
    void ChangeDirectory(String DirectoryName, ProcessBuilder DirChange){
        try{
            DirChange.directory(new File(DirectoryName));
            System.out.println(".....\n Directory Change Successful \n ...");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    void PrintAllFiles(String FilePath){
        Path findFiles = Paths.get(FilePath);
        try {
            Files.walkFileTree(findFiles, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                    System.out.println("Visited " + file);
                    return FileVisitResult.CONTINUE;
                }
                
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    void FlagDangerousFiles(String Directory){
        String SAST_Sgrep = "semgrep scan --config auto " + "\"" + Directory + "\"";
        CommandLineRun(SAST_Sgrep, Directory);
    }
    
    
    
}

