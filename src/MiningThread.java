import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class MiningThread implements Runnable {
    private final String prevHash;
    private final String psuedonym;
    private final OutputManager output;
    private final MiningCommunication mc;
    public final String threadName;
    private Thread t;
    private Long nonce;
    private Long endNonce;


    MiningThread(String prevHash, String psuedonym, OutputManager output, MiningCommunication mc, String threadName, long startNonce, long endNonce) {
        this.prevHash = prevHash;
        this.psuedonym = psuedonym;
        this.output = output;
        this.mc = mc;
        this.threadName = threadName;
        this.nonce = startNonce;
        this.endNonce = endNonce;
    }

    public void run() {
        while (mc.getLeadingZeros() < 50 || Objects.equals(nonce, endNonce)) {
            MessageDigest sha;
            try {
                sha = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            String text = mc.getHash()+psuedonym+nonce;
            sha.update(text.getBytes());
            byte[] bin = sha.digest();
            StringBuilder s = new StringBuilder();
            for (byte b : bin) {
                s.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')); // Converts Byte to Bits 0:
            }
            int zeros = 0;
            for (char ch : s.toString().toCharArray()) {
                if (ch != '0') break;
                zeros++;
            }
            if (zeros > mc.getLeadingZeros()) {
                output.block(text);
                mc.setLeadingZeros(zeros);
                StringBuilder hex = new StringBuilder();
                for (byte b : bin) {
                    hex.append(String.format("%02x", b));
                }
                mc.setHash(hex.toString(), threadName);
            }
            nonce++;
        }
        System.out.println(threadName + " Finshed on " + nonce);
    }

    public void start () {
        if (t == null) {
            t = new Thread (this);
            t.start();
        }
    }

}
