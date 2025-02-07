package Code;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileRead{
    public void ReadFile(String FileName) throws IOException{
            BufferedReader reader = new BufferedReader(new FileReader(FileName));
            String line = reader.readLine();
            while (line!= null){
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
    }
}
