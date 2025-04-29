package com.example.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DAST_Class {

    CommandLineClass command = new CommandLineClass();

    public void DASTCommand(String filepath) {
        Path findFiles = Paths.get(filepath);
    
        try {
            Files.walkFileTree(findFiles, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                    System.out.println("Visited " + file);
                    if(file.toString().endsWith(".java") && !file.getFileName().toString().startsWith("."))
                    {
                        String classFilePath = "/home/kali/Fusion-UAST/tempFiles/" + file.toString().substring(file.toString().lastIndexOf('/') + 1).replace(".java", ".class");
                        String jarFilePath = classFilePath.replace(".class", ".jar");
                        String sbomFilePath = "/home/kali/Fusion-UAST/tempFiles/" + file.getFileName().toString().replace(".java", "_sbom.json");
                        String outputFilePath = "/home/kali/Fusion-UAST/tempFiles/";
                    
                    
                            System.out.println("Attempting to make class file");
                            String[] classFileMaker = {"javac", "-d", outputFilePath, file.toString()};
                            command.ComplexCommandRun(classFileMaker);
                            System.out.println(classFilePath);
                            System.out.println("class file created successfully");
                    
                            // Verify .class file exists
                            File classFile = new File(classFilePath);
                            if (!classFile.exists()) {
                                System.err.println("Error: .class file not found: " + classFilePath);
                                System.exit(0);
                            }
                    
                            System.out.println("Creating JAR file...");
                            String[] jarFileMaker = {"/bin/bash", "-c", "jar cvf " + jarFilePath + " -C /home/kali/Fusion-UAST/tempFiles " + classFile.toString().substring(classFile.toString().lastIndexOf("/") +1)};
                            command.ComplexCommandRun(jarFileMaker);
                            System.out.println("JAR file created: " + jarFilePath);
                    
                            // Verify .jar file exists
                            File jarFile = new File(jarFilePath);
                            if (!jarFile.exists()) {
                                System.err.println("Error: JAR file not found: " + jarFilePath);
                                System.exit(0);
                            }
                            System.out.println("Generating SBOM...");
                            String[] sbomFileMaker = {"/bin/bash", "-c", "syft scan " + jarFilePath + " -o cyclonedx-json > " + sbomFilePath};
                            command.ComplexCommandRun(sbomFileMaker);
                            System.out.println("SBOM generated: " + sbomFilePath);
                    
                            // Verify SBOM file exists
                            File sbomFile = new File(sbomFilePath);
                            if (!sbomFile.exists()) {
                                System.err.println("Error: SBOM file not found: " + sbomFilePath);
                                System.exit(0);
                            }
                    
                            // Step 4: Run Grype scan on the SBOM
                            System.out.println("Scanning with Grype...");
                            String grypeOutputPath = (classFilePath.replace(".class", "_grypeOutput.txt").replace("/tempFiles", ""));
                            String grypeCommand = "grype sbom:" + sbomFilePath + " > " + grypeOutputPath;
                            String[] grypeScan = {"/bin/bash", "-c", grypeCommand};
                            command.ComplexCommandRun(grypeScan);
                        }         
                            
                                    return FileVisitResult.CONTINUE;
                                }                
                            });
        } catch (IOException e){
            e.printStackTrace();
        }
        
    
    }
}
