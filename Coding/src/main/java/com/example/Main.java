package com.example;

import java.io.IOException;

import com.example.CommandLine.APK_Manager;
import com.example.CommandLine.DAST_Class;
import com.example.CommandLine.ReportGenerator;
import com.example.CommandLine.startup;


public class Main {
    public static void StartProgram()
    {
        startup launchStartup = new startup();
        launchStartup.fileCreation();
    }

    //The main class right now just acts more as a test button, App.java should really be the Main class.
    public static void main(String[] args) throws IOException
    {
        
        //These commands are all for testing purposes

        /*CommandLineClass Test = new CommandLineClass();
        FileRead file = new FileRead();*/
        ReportGenerator report = new ReportGenerator();
        DAST_Class dast = new DAST_Class();
        /*

        
        String directory = "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main/AndroidManifest.xml";
        Test.ListDir();
        file.ReadManifest(directory);
        Test.PrintAllFiles("/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2");
        //Test.FlagDangerousFiles("/home/kali/test");*/
        //dast.DASTCommand("/home/kali/Fusion-UAST/apk/");
        report.mdMaker("/home/kali/Fusion-UAST/");
        //APK_Manager apk_Manager = new APK_Manager();
        //apk_Manager.decompiler("/home/kali/InsecureBankv2.apk");
        
    }
}
    
