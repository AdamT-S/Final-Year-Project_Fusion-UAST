package com.example;
import java.io.IOException;


public class Main {
    //The main class right now just acts more as a test button, App.java should really be the Main class.
    public static void main(String[] args) throws IOException{
        
        startup launchStartup = new startup();
        CommandLineClass Test = new CommandLineClass();
        FileRead file = new FileRead();

        
        /*String directory = "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main/AndroidManifest.xml";
        Test.ListDir();
        file.ReadManifest(directory);
        Test.PrintAllFiles("/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2");
        Test.FlagDangerousFiles("/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master");*/
        Test.DASTCommand("/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Coding/src/main/java");
    }
}
    
