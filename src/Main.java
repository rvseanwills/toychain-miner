import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.util.concurrent.ThreadLocalRandom;

// Genesis Hash: 00000274c5d7140b5663e4d2db2e2169ac9cc06e3270c3a7e5eafe0d82d50266 

class OutputManager {
    private FileOutputStream blockFileOutput;

    public OutputManager (String pseudonym) {
        String blockFile = pseudonym + "-ID-" +  Math.random() * 100;
        System.out.println("### NEW BLOCKS - FILE NAME: " + blockFile);
        try {
            blockFileOutput = new FileOutputStream(blockFile);
            blockFileOutput.write(pseudonym.getBytes());
        } catch (IOException e) {
            System.out.println("Error Creating Log Files: " + e.getMessage());
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

class MiningCommunication {
    private volatile String hash;
    private volatile int leadingZeros;
//    private int attempts; 

    public MiningCommunication(String hash) {
//        this.attempts = 0; 
        this.hash = hash;
    }
//    public void incrementAttempts() { 
//        this.attempts++; 
//    } 

    //    public void printAttempts() { 
//        System.out.println("Attempts: " + attempts); 
//    } 
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
        System.out.println("Set the hash to " + hash);
    }

    public void setLeadingZeros(int zeros) {
        this.leadingZeros = zeros;
        System.out.println("New Leading Zeros: " + leadingZeros);
    }

    public int getLeadingZeros() {
        return leadingZeros;
    }
}

class MiningThread implements Runnable {
    private final String psuedonym;
    private final OutputManager output;
    private final MiningCommunication mc;
    public final String threadName;
    private Thread t;

    MiningThread(String psuedonym, OutputManager output, MiningCommunication mc, String threadName) {
        this.psuedonym = psuedonym;
        this.output = output;
        this.mc = mc;
        this.threadName = threadName;
    }

    public void run() {
        // Setup Sha object 
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int threadZeros = -1;
        String inputBase = "";
        while (true) { // Usually mc.getLeadingZeros() < 50 but I'm squeezing performance :/
            int sharedZeros = mc.getLeadingZeros();
            if (threadZeros != sharedZeros) {
                inputBase = mc.getHash() + psuedonym;
                threadZeros = sharedZeros;
            }
            long nonce = random.nextLong();
            String inputText = inputBase + nonce;
            // New Hash:
            sha.reset(); // clear sha object
            sha.update(inputText.getBytes(StandardCharsets.UTF_8));
            byte[] bin = sha.digest(); // gets the new hash as bytes
            // Counting the zeros: 
            int zeroCount = 0;
            for (byte b : bin) {
                if (b == 0) {
                    zeroCount += 8;
                } else {
                    int unsigned = b & 0xFF; // Mask? 
                    zeroCount += Integer.numberOfLeadingZeros(unsigned << 24);
                    break;
                }
            }
            // Checks for a leading block, outputs and changes shared previous hash for other threads 
            if (zeroCount > mc.getLeadingZeros()) {
                mc.setLeadingZeros(zeroCount);
                StringBuilder hex = new StringBuilder();
                for (byte b : bin) {
                    hex.append(String.format("%02x", b)); // convert byte to hex 
                }
                output.block(inputText);
                mc.setHash(hex.toString());
            }
        }
    }

    public void start () {
        if (t == null) {
            t = new Thread (this);
            t.start();
        }
    }

}


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter Your Pseudonym: ");
        String pseudonym = input.next();
        OutputManager output = new OutputManager(pseudonym);
        System.out.println("Please Copy Your Previous Binary Hash (In Hex!): ");
        String hash = input.next();
        MiningCommunication mc = new MiningCommunication(hash);
        mc.setLeadingZeros(calculateLeadingZeros(hash));
        System.out.println("Starting Zeros: " + mc.getLeadingZeros());
        int numOfThreads = 20;
        for (int i = 0; i < numOfThreads; i++) {
            MiningThread thre = new MiningThread(pseudonym, output, mc, "Thread "+i);
            thre.start();
        }
    }
    private static int calculateLeadingZeros(String hex) {
        String binary = hexArrayBinary(hex);
        int count = 0;
        for (char ch : binary.toCharArray()) {
            if (ch != '0') return count;
            count++;
        }
        return count;
    }
    private static String hexArrayBinary(String hex) {
        hex = hex.toUpperCase();
        StringBuilder binary = new StringBuilder(hex.length() * 4);
        for (char c : hex.toCharArray()) {
            int value = Character.digit(c, 16);
            if (value == -1) {
                throw new IllegalArgumentException("Invalid hex character: " + c);
            }
            binary.append(String.format("%4s", Integer.toBinaryString(value)).replace(' ', '0'));
        }
        return binary.toString();
    }
} 