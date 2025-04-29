package com.example.CommandLine;

import java.io.IOException;

public class SAST_Class 
{

    CommandLineClass command = new CommandLineClass();
    private FileRead FileReader = new FileRead();

    public Runnable Semgrep_run(String Directory) throws IOException
    {

        String SAST_Sgrep = "semgrep scan --config auto " + "\"" + Directory + "\""+ " --output /home/kali/Fusion-UAST/SemgrepOutput.txt --text";
        System.out.println(SAST_Sgrep);
        command.CommandLineRun(SAST_Sgrep, Directory);
        //This really needs to be made dynamic so that it works on other platforms, but its fine for the demo rn
        FileReader.ReadFlaggedFiles("/home/kali/Fusion-UAST/SemgrepOutput.txt", Directory);
        return null;
    
    }
}
