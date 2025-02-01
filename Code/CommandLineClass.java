package Code;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;;

public class CommandLineClass{
    void CommandLineRun(string CommandString){
        try{
        
    
            ProcessBuilder command = new ProcessBuilder(CommandString.split(" "));
            command.redirectErrorStream(true);
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
        CommandLineRun(Decompile);

    }
    void ChangeDirectory(String DirectoryName){
        String ChangeDirCommand = "cd" + DirectoryName;
        CommandLineRun(ChangeDirCommand);
    }
    
}

