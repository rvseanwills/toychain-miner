import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MiningManager {
    private OutputManager output;
    private String previousHash;
    private int leadingZeros;
    private String pseudonym;
    public MiningManager (String previousHash, String pseudonym, OutputManager output) {
        this.output = output;
        this.previousHash = previousHash;
        this.leadingZeros = calculateLeadingZeros(previousHash);
        this.pseudonym = pseudonym;
        output.log("Mining Manager Started with previous hash: " + this.previousHash);
        output.log("Mining Manager Started with pseudonym: " + this.pseudonym);
        output.log("Mining Manager Started with leading zeros: " + leadingZeros);
        try {
            while (leadingZeros < 100) { mine(this.previousHash+pseudonym+(int)(Math.random() * 100000)); }
        } catch (Exception e) {
            output.log("Error: Mining Failed - " + e.getMessage());
        }
    }
    private void mine (String text) throws NoSuchAlgorithmException {
        output.log("//////////////////////////////////");
        output.log("############ NEW MINE ############");
        output.log("############ CURRENT LEADING ZEROS: " + leadingZeros);
        output.log("############ PREVIOUS HASH: " + previousHash);
        output.log("############ INPUT TEXT: " + text);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(text.getBytes());
        byte[] bin = sha.digest();
        output.log("############ OUTPUT HASH (Binary): " + byteArrayString(bin));
        String hex = byteArrayHex(bin);
        output.log("############ OUTPUT HASH (Hex): " + byteArrayHex(bin));
        int zeros = calculateLeadingZeros(hex);
        output.log("############ OUTPUT LEADING ZEROS: " + zeros);
        if (zeros > leadingZeros) {
            output.block(text);
            output.log("############ OUTPUT IS A NEW BLOCK! ADDED TO BLOCK LOG");
            output.log("############ ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            leadingZeros = zeros;
            previousHash = hex;
        } else {
            output.log("############ OUTPUT IS NOT A NEW BLOCK CONTINUING WITH: " + previousHash);
        }
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
