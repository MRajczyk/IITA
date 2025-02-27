package pl.dmcs.FileConverter;

import java.io.*;

public class FileConverter {

    public void convertFile(String filePath) throws IOException {
        BufferedReader reader;
        BufferedWriter writer;

        reader = new BufferedReader(new FileReader(filePath));
        StringBuilder content = new StringBuilder();
        int c;

        while ((c = reader.read()) != -1) {
            content.append(Character.toUpperCase((char) c));
        }

        writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content.toString());

        reader.close();
        writer.close();
    }
}
