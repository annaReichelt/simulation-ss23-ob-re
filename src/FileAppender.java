package src;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppender {

    public static void appendLineToFile(String filePath, String line) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(line);
        writer.newLine();
        writer.close();
    }
}