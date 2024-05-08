import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Filereader {
    public static String readFile(String fileName) {
        StringBuilder fileContentString = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
               fileContentString.append(line);
               fileContentString.append("\n");
            }
        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());
        }
        return fileContentString.toString();
    }

    public static List<String> readFileLine(String fileName) {
        java.util.List<String> fileLine = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileLine.add(line);
            }
        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());
        }
        return fileLine;
    }


    public static void fileWrite(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)))) {
            writer.write(content + System.lineSeparator());
            System.out.println("Content added to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}