package com.example;

import java.io.File;

public class startup {

    CommandLineClass Test = new CommandLineClass();

    
    void installers(){
        
        
    }
    void fileCreation(){

        File Fusion = new File("/home/kali/Fusion-UAST");

        // This creates a temporary directory to save the .class, .jar, and sbom files
        File tempDirectory = new File("/home/kali/Fusion-UAST/tempFiles");
        if (!tempDirectory.exists()) {
            if (tempDirectory.mkdirs()) {
                System.out.println("Output directory created: " + tempDirectory.getAbsolutePath());
            } else {
                System.err.println("Failed to create output directory: " + tempDirectory.getAbsolutePath());
                return;
            }
        }
        //This creates a directory to save the output files from all of the differenty grype tests
        File outputDirectory = new File("/home/kali/Fusion-UAST/tempFiles");
        if (!outputDirectory.exists()) {
            if (outputDirectory.mkdirs()) {
                System.out.println("Output directory created: " + outputDirectory.getAbsolutePath());
            } else {
                System.err.println("Failed to create output directory: " + outputDirectory.getAbsolutePath());
                return;
            }
        }

        File finalReport = new File("home/kali/Fusion-UAST/Report.txt");
    }
}
