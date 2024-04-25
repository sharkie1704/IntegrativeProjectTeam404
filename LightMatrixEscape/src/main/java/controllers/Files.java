package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author Ishrak Mellah
 */
public class Files {

    public static String readFileGivenPath(String path) {
        File selectedFile = new File(path);
        if (selectedFile.exists()) {
            try {
                // Read the file content into a string
                return new String(java.nio.file.Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
            } catch (IOException e) {
                return "Error reading file";
            }
        } else {
            return "No file selected";
        }
    }

    public static void saveToFile(String content, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
        }
    }

    public static String readFile(File progressFile) throws IOException {
        if (progressFile != null) {
            // Read the file content into a string
            return new String(java.nio.file.Files.readAllBytes(Paths.get(progressFile.getAbsolutePath())));
        } else {
            return "No file selected";
        }
    }
}
