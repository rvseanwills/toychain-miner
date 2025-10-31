import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MiningManager {
    private final OutputManager output;
    private String previousHash;
    private int leadingZeros;
    private final MiningCommunication mc;
    private final String threadName;

    public MiningManager (String previousHash, String pseudonym, OutputManager output, MiningCommunication mc, String threadName) {
        this.output = output;
        this.previousHash = previousHash;
        this.leadingZeros = calculateLeadingZeros(previousHash);
        this.mc = mc;
        if (mc != null) { mc.setLeadingZeros(this.leadingZeros); }

        this.threadName = threadName;
        System.out.println("From MiningManager - Initial Hash: " + previousHash + " | Leading Zeros: " + leadingZeros +  " | Thread: " + threadName);
        try {
            while (leadingZeros < 50) { mineNoLoggingWithThreading(this.previousHash+pseudonym+(int)(Math.random() * 100000000)); }
        } catch (Exception e) {
            System.out.println("Error: Mining Failed - " + e.getMessage());
        }

    }
    private void mineNoLoggingWithThreading (String text) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(text.getBytes());
        byte[] bin = sha.digest();
        String hex = byteArrayHex(bin);
        int zeros = calculateLeadingZeros(hex);
        if (zeros > leadingZeros) {
            output.block(text);
            mc.setLeadingZeros(zeros);
            mc.setHash(hex, threadName);
        }
        previousHash = mc.getHash();
        leadingZeros = mc.getLeadingZeros();
    }

    private int calculateLeadingZeros(String hex) {
        String binary = hexArrayBinary(hex);
        int count = 0;
        for (char ch : binary.toCharArray()) {
            if (ch != '0') return count;
            count++;
        }
        return count;
    }

    ///  Conversion Helpers

    private String byteArrayString(byte[] arr) {
        StringBuilder s = new StringBuilder();
        for (byte b : arr) {
            s.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')); // Converts Byte to Bits 0:
        }
        return s.toString();
    }
    private String byteArrayHex(byte[] arr) {
        StringBuilder s = new StringBuilder();
        for (byte b : arr) {
            s.append(String.format("%02x", b));
        }
        return s.toString();
    }
    private String hexArrayBinary(String hex) {
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
