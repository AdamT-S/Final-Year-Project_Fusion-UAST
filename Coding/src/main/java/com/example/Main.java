package com.example;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.control.Button;


public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        CommandLineClass Test = new CommandLineClass();
        FileRead file = new FileRead();
        String directory = "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main/AndroidManifest.xml";
        Test.ListDir();
        file.ReadManifest(directory);
        Test.PrintAllFiles("/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2");
        Test.FlagDangerousFiles("/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master");

    }
}
    
