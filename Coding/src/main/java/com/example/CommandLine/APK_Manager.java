package com.example.CommandLine;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class APK_Manager
{
    private FileRead FileReader = new FileRead();
    CommandLineClass command = new CommandLineClass();

    void Decompiler(String APK){    
        String Decompile = "apktool d" + APK;
        command.CommandLineRun(Decompile, null);

    }

     //Change this to search every file within a directory specifically to find the manifest.
    public void getManifest(String FilePath){
        Path findFiles = Paths.get(FilePath);
        try {
            Files.walkFileTree(findFiles, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                    System.out.println("Visited " + file);
                    if(file.toString().contains("Manifest"))
                    {
                        FileReader.ReadManifest(file.toString());
                        return FileVisitResult.TERMINATE;
                        
                    }
                    return FileVisitResult.CONTINUE;
                }

                
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
