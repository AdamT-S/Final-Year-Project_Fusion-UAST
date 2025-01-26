package Code;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;;

public class CommandLineClass{
    void Decompiler(String APK){
       try{
        
        String Decompile = "apktool d" + APK;

        ProcessBuilder command = new ProcessBuilder(Decompile.split(" "));
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
    
}

