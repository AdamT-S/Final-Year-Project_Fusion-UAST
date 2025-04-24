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
    private FileRead ReadFlagFiles = new FileRead();

    void CommandLineRun(String CommandString, String FilePath){
        try{
            // Print the directory for debugging purposes
            System.out.println("Directory: " + FilePath);
            // Split the command using a more robust method
            String[] commandArray = {CommandString}; // Using bash to handle the command
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
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
        } 
        
    }
    
    //This is used to decompile the apks provided
    void Decompiler(String APK){    
        String Decompile = "apktool d" + APK;
        CommandLineRun(Decompile, null);

    }
    //This lists all the files in a directory. (Unused. Only for early testing)
    void ListDir()
    {
        String ldir = "ls";
        CommandLineRun(ldir, "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main");
    }

    //This allows any function called to be directed to the correct directory
    void ChangeDirectory(String DirectoryName, ProcessBuilder DirChange){
        try{
            DirChange.directory(new File(DirectoryName));
            System.out.println(".....\n Directory Change Successful \n....");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //Change this to search every file within a directory.
    void SearchFiles(String FilePath){
        Path findFiles = Paths.get(FilePath);
        try {
            Files.walkFileTree(findFiles, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                    System.out.println("Visited " + file);
                    if(file.toString().contains("Manifest"))
                    {
                        ReadFlagFiles.ReadManifest(file.toString());
                        return FileVisitResult.TERMINATE;
                        
                    }
                    return FileVisitResult.CONTINUE;
                }

                
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //This flags all dangerous files found by analysis tools
    Runnable FlagDangerousFiles(String Directory) throws IOException{
        String SAST_Sgrep = "semgrep scan --config auto " + "\"" + Directory + "\""+ " --output /home/kali/Fusion-UAST/SemgrepScan.txt --text";
        System.out.println(SAST_Sgrep);
        CommandLineRun(SAST_Sgrep, Directory);
        ReadFlagFiles.ReadFlaggedFiles("/home/kali/Fusion-UAST/SemgrepScan.txt", Directory);
                return null;
    }
    
    public void DASTCommand(String filepath) {
        
    
        String classFilePath = "/home/kali/Fusion-UAST/" + filepath.substring(filepath.lastIndexOf('/') + 1).replace(".java", ".class");
        String jarFilePath = classFilePath.replace(".class", ".jar");
        String sbomFilePath = classFilePath.replace(".class", "_sbom.json");
        String outputFilePath = "/home/kali/Fusion-UAST/DASTOutputs/";
    
        try {
    
            System.out.println("Attempting to make class file");
            String[] classFileMaker = {"javac", "-d", "/home/kali/Fusion-UAST", filepath};
            ComplexCommandRun(classFileMaker);
            System.out.println("class file created successfully");
    
            // Verify .class file exists
            File classFile = new File(classFilePath);
            if (!classFile.exists()) {
                System.err.println("Error: .class file not found: " + classFilePath);
                return;
            }
    
            System.out.println("Creating JAR file...");
            String[] jarFileMaker = {"jar", "cvf", jarFilePath, "-C", "/home/kali/Fusion-UAST", "VulnerableApp.class"};
            ComplexCommandRun(jarFileMaker);
            System.out.println("JAR file created: " + jarFilePath);
    
            // Verify .jar file exists
            File jarFile = new File(jarFilePath);
            if (!jarFile.exists()) {
                System.err.println("Error: JAR file not found: " + jarFilePath);
                return;
            }
    
            System.out.println("Generating SBOM...");
            String[] sbomFileMaker = {"syft", "scan", jarFilePath, "-o", "cyclonedx-json"};
            ComplexCommandRun(sbomFileMaker);
            System.out.println("SBOM generated: " + sbomFilePath);
    
            // Verify SBOM file exists
            File sbomFile = new File(sbomFilePath);
            if (!sbomFile.exists()) {
                System.err.println("Error: SBOM file not found: " + sbomFilePath);
                return;
            }
    
            // Step 4: Run Grype scan on the SBOM
            System.out.println("Scanning with Grype...");
            String[] grypeScan = {"grype", "sbom:" + sbomFilePath};
            ComplexCommandRun(grypeScan);
            System.out.println("Grype test completed. Results saved in: " + outputFilePath);
    
        } 
        catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    
    }
}
