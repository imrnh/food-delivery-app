import java.io.*;

public class FileOps {
    public static boolean lineRemover(String filePath, int lineNumberToRemove){
        try {
            File inputFile = new File(filePath);
            File tempFile = new File("tempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            int lineNumber = 1;

            while ((currentLine = reader.readLine()) != null) {
                // If this line is not the line to be removed, write it to the temporary file
                if (lineNumber != lineNumberToRemove) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                lineNumber++;
            }
            writer.close();
            reader.close();

            // Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete the original file.");
                return false;
            }

            // Rename the temporary file to the original file name
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temporary file.");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
