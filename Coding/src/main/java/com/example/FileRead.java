package com.example;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileRead{
    //Lists to ensure that any files identified can be reused
    List<String> permList = new ArrayList<>();
    List<String> dangerousFileList = new ArrayList<>();

    //Read the manifest files and its permissions
    public void ReadManifest(String FileName){
        //This ensures that if the file returns with an error that the system doesnt crash
        try{
            BufferedReader reader = new BufferedReader(new FileReader(FileName));
            String line = reader.readLine();
            //Gets the data after the android.permission section in the manifest file
            Pattern pattern = Pattern.compile("android\\.permission\\.([^\"]+)\"");
            //reads the whole document to make sure that all of the permissions are added
            while (line!= null){
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()){
                    /*
                    Groups the part of the line that is desired within the manifest file and adds it to a list
                    ([^\"]+)\" is the group. () acts as the grouping [^\"] acts as the characters that can be used where ^" means any character except "
                    and the + means all characters after that too.
                    */
                    String permission = matcher.group(1);
                    permList.add(permission);
                }
                
                line = reader.readLine();
            }
            reader.close();
            //prints off Manifest permissions
            System.out.println("Permissions: ");
            for (String s : permList){
                System.out.println(s);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }

    public void ReadFlaggedFiles(String FileName, String FilePath) throws IOException{
        // source: https://stackoverflow.com/questions/11169266/in-java-how-to-print-entire-line-in-the-file-when-string-match-found
        String[] firstPartOfFile = FilePath.split("/");
        String path = firstPartOfFile[1]; 
        System.out.println(path);
        
        
        BufferedReader r = new BufferedReader(new FileReader(FileName));
        Pattern patt = Pattern.compile(path); 
        String line;
        List<String> dangerousFileList = new ArrayList<>();
        
        try {
            line = r.readLine();
            int issuebreak = 0;
            
            while (line != null && issuebreak < 11) {
                Matcher matcher = patt.matcher(line);
                if (matcher.find()) {
                    dangerousFileList.add(line);
                    int checkPath = line.lastIndexOf(".");

                    while (checkPath == -1 && line != null) { 
                        line = r.readLine();
                        checkPath = line.lastIndexOf(".");
                        dangerousFileList.add(line);
                    }
                    System.out.println("\nThe loop has ended and will restart here\n");
                }

                if (issuebreak == 10) {
                    System.out.println("I'm having loop issues");
                }
                line = r.readLine();
            }

            r.close();
            System.out.println("No issues here");
            
            System.out.println("Dangerous Files: ");
            for (String s : dangerousFileList) {
                System.out.println(s);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    } 
}
