import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class OutputManager {
    private String logFile = "minerlog-";
    private String blockFile = "blocks-";
    private FileOutputStream logFileOutput;
    private FileOutputStream blockFileOutput;

    public OutputManager (String pseudonym) {
        double rand = Math.random(); // Using to ensure file names are unique :)
        System.out.println("!!! UNIQUE LOG NUMBER: " + rand + "!!! (So you can easily find the corresponding log files.)");
        logFile = logFile += LocalDate.now() + "-" + rand + "." + pseudonym;
        blockFile = blockFile += LocalDate.now() + "-" + rand + "." + pseudonym;

        try {
            logFileOutput = new FileOutputStream(logFile);
            blockFileOutput = new FileOutputStream(blockFile);
            logFileOutput.write(pseudonym.getBytes());
            blockFileOutput.write(pseudonym.getBytes());
        } catch (IOException e) {
            System.out.println("Error Creating Log Files: " + e.getMessage());
        }
    }
    public void log(String message) {
        try {
            logFileOutput.write('\n');
            logFileOutput.write(message.getBytes());
        } catch (IOException e) {
            System.out.println("Error Adding Log Entry: " + e.getMessage());
        }
    }

    public void block(String block) {
        try {
            blockFileOutput.write('\n');
            blockFileOutput.write(block.getBytes());
        } catch (IOException e) {
            System.out.println("Error Adding Block Entry: " + e.getMessage());
        }
    }
}
