import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();

        // Set the working directory to the 'src' folder of the IntelliJ project
        File workingDirectory = new File(System.getProperty("user.dir") + "/src");
        if (workingDirectory.exists()) {
            chooser.setCurrentDirectory(workingDirectory);
        }

        System.out.println("Please choose a text file to inspect...");

        int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            Path file = selectedFile.toPath();

            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;

            System.out.println("\n--- Echoing File Contents ---");

            try (BufferedReader reader = Files.newBufferedReader(file)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // Echo the line to the screen
                    lineCount++;
                    charCount += line.length(); // Count characters in the line

                    // Count words using split on whitespace (handle empty lines gracefully)
                    if (!line.trim().isEmpty()) {
                        String[] words = line.trim().split("\\s+");
                        wordCount += words.length;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
                e.printStackTrace();
            }

            // Print the summary report
            System.out.println("\n==============================");
            System.out.println("       FILE SUMMARY REPORT    ");
            System.out.println("==============================");
            System.out.println("File Name          : " + selectedFile.getName());
            System.out.println("Number of Lines    : " + lineCount);
            System.out.println("Number of Words    : " + wordCount);
            System.out.println("Number of Characters: " + charCount);
            System.out.println("==============================");

        } else {
            System.out.println("No file selected. Program terminating.");
        }
    }
}