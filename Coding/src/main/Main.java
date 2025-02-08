package src.main;
package main;


public class Main {
    public static void main(String[] args){
        CommandLineClass Test = new CommandLineClass();
        FileRead file = new FileRead();
        String directory = "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main/AndroidManifest.xml";
        Test.ListDir();
        file.ReadFile(directory);
    }
}
    
