package src.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;;

public class CommandLineClass{
    void CommandLineRun(String CommandString, String FilePath){
        try{
        
    
            ProcessBuilder command = new ProcessBuilder(CommandString.split(" "));
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
    
}

