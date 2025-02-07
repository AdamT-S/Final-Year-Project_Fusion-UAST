import Code.CommandLineClass;

public class Testing {
    void TestFunc(){
        CommandLineClass Test = new CommandLineClass();
        String directory = "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2/app/src/main";
        Test.ChangeDirectory(directory);
    }
    void HolderCommandLineClass(){
        Test = new CommandLineClass();
        String APK = "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master/InsecureBankv2.apk";
        Test.Decompiler(APK); 
       } 
}
