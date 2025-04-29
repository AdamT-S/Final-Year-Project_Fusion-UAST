package com.example.CommandLine;

import java.io.File;

public class startup {

    CommandLineClass Test = new CommandLineClass();

    //Ensures all the files are created for the tests
    public void fileCreation(){

        new File("/home/kali/Fusion-UAST");

        File old_report = new File("/home/kali/Fusion-UAST/Old_Reports");
        String move_oldFiles [] = {"mv", "/home/kali/Fusion-UAST/Final_Report.md", old_report.toString()};
        String move_oldFolder [] = {"mv", "/home/kali/Fusion-UAST/fullReports", old_report.toString()};
        Test.complexCommandRun(move_oldFiles);
        Test.complexCommandRun(move_oldFolder);

        // This creates a temporary directory to save the .class, .jar, and sbom files
        File tempDirectory = new File("/home/kali/Fusion-UAST/tempFiles");
        if (!tempDirectory.exists()) 
        {
            if (tempDirectory.mkdirs()) 
            {
                System.out.println("Output directory created: " + tempDirectory.getAbsolutePath());
            } 
            else 
            {
                System.err.println("Failed to create output directory: " + tempDirectory.getAbsolutePath());
                return;
            }
        }
        //This creates a directory to save the output files from all of the differenty grype tests
        File outputDirectory = new File("/home/kali/Fusion-UAST/tempFiles");

        if (!outputDirectory.exists()) 
        {
            if (outputDirectory.mkdirs()) 
            {
                System.out.println("Output directory created: " + outputDirectory.getAbsolutePath());
            }
            
            else 
            {
                System.err.println("Failed to create output directory: " + outputDirectory.getAbsolutePath());
                return;
            }
        }   
    }
}
