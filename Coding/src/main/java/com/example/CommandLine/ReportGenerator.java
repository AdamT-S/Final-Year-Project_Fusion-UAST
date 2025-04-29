package com.example.CommandLine;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {

    

    public void mdMaker(String filePath)
    {
        File FinalReport = new File(filePath+"/Final_Report.md");
        System.out.println(FinalReport + " created");
    
        try 
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FinalReport, true));
            writer.write("#" + FinalReport.toString() + " Report\n\n");
            fileBody(filePath, writer);
            writer.close();
        } 
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
    }
    
    public void fileBody(String FilePath, BufferedWriter writer)
    {
        try
        {
            System.out.println("Starting file reads");
            boolean grypeDesc = false;
            Path grypePath = Paths.get(FilePath);
           
            Files.walkFileTree(grypePath, new SimpleFileVisitor<Path>()
            {
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                    System.out.println(grypePath.toString()); 
                    System.out.println(file);
                    BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
                    if (file.toString().contains("grypeOutput") || file.toString().contains("Semgrep"))
                    {

                        if(file.toString().contains("grypeOutput") )
                        {  
                            System.out.println("Running in Grype reports");
                            if(grypeDesc == false)
                            {
                                writer.write("##Dynamic Application Security Analysis Results\n\n");
                                writer.write("###**Dynamic Application Security Analysis(DAST) is a method of vulnerability detection where an application analyses a running program. DAST tests do not check the application's source code, but rather checks the running environment[^2] to see how it interacts with the programs operation.**\n");
                                writer.write("**A program should not have any critical errors within it. This must be fixed immediately as a hacker could exploit this very large vulnerability to perform an attack on the system.**\n");
                                writer.write("**It should also not have any high level vulnerabilities. There should be a zero tolerance of any vulnerabilities within the high and critical ranges.**\n");
                                writer.write("**If a program has any medium or low vulnerabilities, a few can be expected. Depending on the size of the program. If the program is large you can expect a few 10's or 100's of medium and low vulnerabilities. If the program is small, you should typically expect a few. These will need to be fixed but are not as urgent as the higher levels**\n\n");
                                final boolean grypeDesc = true;
                            }
                            int critCounter = 0;
                            int highCounter = 0;
                            int mediumCounter = 0;
                            int lowCounter = 0;
                            int unknownCounter = 0;
                        
                            String currentLine;
                            while ((currentLine = reader.readLine()) != null) 
                            {
                                if(currentLine.contains("Critical"))
                                {
                                    critCounter += 1;
                                }
                                else if(currentLine.contains("High"))
                                {
                                    highCounter += 1;
                                }
                                else if(currentLine.contains("Medium"))
                                {
                                    mediumCounter += 1;
                                }
                                else if(currentLine.contains("Low"))
                                {
                                    lowCounter += 1;
                                }
                                else if(currentLine.contains("Unknown"))
                                {
                                    unknownCounter += 1;
                                }
                                
                            }
    
                            int counterTotal = critCounter + highCounter + mediumCounter + lowCounter + unknownCounter;
                           
                            if(counterTotal == 0)
                            {
                                System.out.println("This is the error thats been plaguing you");
                            }
                            else
                            {
                               int critPercent = (critCounter*100) / counterTotal;
                                int highPercent = (highCounter*100) / counterTotal;
                                int mediumPercent = (mediumCounter*100) / counterTotal;
                                int lowPercent = (lowCounter*100) / counterTotal;
                                int unknownPercent = (unknownCounter*100) / counterTotal;
                                writer.write("\n#Number of each type of vulnerabilities within " + file + ": \n");
                                writer.write("Critical errors: " + critPercent+"% (" + critCounter + " vulnerabilities)\n");
                                writer.write("High level errors: " + highPercent+"% (" + highCounter + "vulnerabilities)\n");
                                writer.write("Medium level errors: " + mediumPercent+"% (" + mediumCounter + "vulnerabilities)\n");
                                writer.write("Low level errors: " + lowPercent+"% (" + lowCounter + "vulnerabilities)\n");
                                writer.write("Unknown errors: " + unknownPercent+"% (" + unknownCounter + "vulnerabilities)\n\n");
                                
                                if(unknownCounter > 0)
                                {
                                    writer.write("**************\nAn unknown vulnerability is a vulnerability that is not currently in the testing database and will require further examination. More details of all of the vulnerabilities found will be available in the 'Detailed' report.\n**************\n");
                                }
                                if((critCounter | highCounter) >= 1 )
                                {
                                    writer.write("***This application has very dangerous vulnerabilities that need to be addressed immediately. It is recommended you provide these reports to a cyber security consultant immediately.***\n");
                                }
                                if(mediumCounter >= 2)
                                {
                                    writer.write("\nThere are " + mediumCounter + " vulnerabilities within this program, these vulnerabilities will need fixing as soon as possible but it is not an urgent fix.\n");
                                }
                                if(lowCounter >= 5)
                                {
                                    writer.write("\nThere are a few low level vulnerabilities present. These vulnerabilities are negligible to the running of the program, however it is always best to employ a zero tolerance policy against all vulnerabilities and should have these fixed.\n");
                                }
                                if(lowCounter <= 4)
                                {
                                    writer.write("\nThere is a small amount of low level vulnerabilities present. These should be fixed, but posses a minor level of threat to the program.\n");
                                }
                                if((critCounter & highCounter & mediumCounter & lowCounter & unknownCounter) == 0)
                                {
                                    writer.write("This program has no vulnerabilities.\n");
                                }
                            } 
                        }
                            
                        if(file.toString().contains("Semgrep"))
                        {
                            System.out.println("Running in sast report");
                            writer.write("\n##Static Application Security Analysis Results\n");
                            writer.write("###**Static Application Security Analysis(SAST) is a method of vulnerability detection where an application analyses an programs source code[^1]. This program then checks for vulnerabilities within the software by checking commands for any potential issues that are associated with those commands.**\n\n");
                            
                            String currentLine;
                            while ((currentLine = reader.readLine()) != null) 
                            {
                                currentLine = reader.readLine();
                                writer.write(currentLine);
                            }
                            writer.write("\n\n");
                        }
                    }
                    reader.close();
                    return FileVisitResult.CONTINUE;
                } 
                
            });
            writer.write("\n\n");
            
            
            writer.close();
            
        }
        catch (IOException e)
        {
    
        }
    }
    
}
