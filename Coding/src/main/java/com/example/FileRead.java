package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRead{
    List<String> permList = new ArrayList<>();
    List<String> dangerousFileList = new ArrayList<>();
    public void ReadManifest(String FileName){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(FileName));
            String line = reader.readLine();
            Pattern pattern = Pattern.compile("android\\.permission\\.([^\"]+)\"");
            while (line!= null){
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()){
                    String permission = matcher.group(1);
                    permList.add(permission);
                }
                
                line = reader.readLine();
            }
            reader.close();
            System.out.println("Permissions: ");
            for (String s : permList){
                System.out.println(s);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }

    public void ReadFlaggedFiles(String FileName, String FilePath) throws FileNotFoundException{
        String path = "/home/kali/Final Year Project/Final-Year-Project-Automated-PenTest/Android-InsecureBankv2-master";
 
        InputStream is = new FileInputStream(FileName);
 
        try (Scanner sc = new Scanner(
                 is, StandardCharsets.UTF_8.name())) {
 
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        }
            System.out.println("Flagged Files: ");
            for (String s : dangerousFileList){
                System.out.println(s);
            }
    }
}
