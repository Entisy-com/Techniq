package com.entisy.techniq.core.util.entisy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileHelper {
    public static String readContent(File file) {
        try {
            StringBuilder builder = new StringBuilder();
            InputStream stream = Files.newInputStream(file.toPath());
            while(true){
                int i = stream.read();
                if(i < 0) break;
                builder.append((char) i);
            }
            stream.close();
            return builder.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void writeContent(File f, String content) {
        try {
            FileWriter writer = new FileWriter(f);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
