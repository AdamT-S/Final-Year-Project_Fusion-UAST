package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void ReadFlaggedFiles(String FileName, String FilePath){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(FileName));
            String line = reader.readLine();
            Pattern pattern = Pattern.compile(FilePath+"/");
            while (line!= null){
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()){
                    String permission = matcher.group(1);
                    dangerousFileList.add(permission);
                }
                
                line = reader.readLine();
            }
            reader.close();
            System.out.println("Flagged Files: ");
            for (String s : dangerousFileList){
                System.out.println(s);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }
}
