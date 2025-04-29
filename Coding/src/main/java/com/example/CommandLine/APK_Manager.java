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
    private FileRead fileReader = new FileRead();
    CommandLineClass command = new CommandLineClass();

    public void decompiler(String APK)
    {    
        String decompile = "apktool d " + APK + " -o /home/kali/Fusion-UAST/apk -f";
        command.commandLineRun(decompile, null);
        System.out.println("APK decompiled successfully and moving onto the next stage");

    }

     //Change this to search every file within a directory specifically to find the manifest.
    public void getManifest(String FilePath)
    {
        Path findFiles = Paths.get(FilePath);
        
        try
        {
            Files.walkFileTree(findFiles, new SimpleFileVisitor<Path>()
            {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                {
                    //Debug method to check files are actively being visited
                    System.out.println("Visited " + file);
                    if(file.toString().contains("Manifest"))
                    {
                        fileReader.readManifest(file.toString());
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
