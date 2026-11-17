import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean keepGoing = true;

        System.out.println("=== Data Saver Program ===");
        System.out.println("Enter data records (at least 3 recommended).\n");

        while (keepGoing) {
            // Get user input using SafeInput-style prompts or standard Scanner checks
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name");

            // ID Number: zero-replaced string of 6 digits (e.g., 000001)
            String idNumber = SafeInput.getRegExString(in, "Enter ID Number (6 digits e.g. 000001)", "\\d{6}");

            String email = SafeInput.getNonZeroLenString(in, "Enter Email");

            // Year of Birth: 4-digit integer
            int yob = SafeInput.getRangedInt(in, "Enter Year of Birth", 1000, 9999);

            // Format as a CSV record string
            String csvRecord = String.format("%s, %s, %s, %s, %d", firstName, lastName, idNumber, email, yob);
            records.add(csvRecord);

            System.out.print("\nWould you like to enter another record? (Y/N): ");
            String response = in.next().trim();
            in.nextLine(); // clear buffer

            if (response.equalsIgnoreCase("N")) {
                keepGoing = false;
            }
            System.out.println();
        }

        // Prompt for file name
        String fileName = SafeInput.getNonZeroLenString(in, "Enter output file name (do not include extension)");
        String fullFileName = fileName + ".csv";

        // Define path to save in the src directory of the IntelliJ project
        Path filePath = Paths.get(System.getProperty("user.dir"), "src", fullFileName);

        // Write records to the CSV file
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String record : records) {
                writer.write(record);
                writer.newLine();
            }
            System.out.println("\nData successfully saved to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}