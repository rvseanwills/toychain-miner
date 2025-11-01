import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MiningThread implements Runnable {
    private final String prevHash;
    private final String psuedonym;
    private final OutputManager output;
    private final MiningCommunication mc;
    public final String threadName;
    private Thread t;

    MiningThread(String prevHash, String psuedonym, OutputManager output, MiningCommunication mc, String threadName, long startNonce, long endNonce) {
        this.prevHash = prevHash;
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
        ByteBuffer nonceBuffer = ByteBuffer.allocate(Long.BYTES); // reuseable for all nonces
        while (mc.getLeadingZeros() < 50) {
            sha.reset(); // clear sha object
            sha.update(mc.getHash().getBytes(StandardCharsets.UTF_8)); // add the bytes of previous hash
            sha.update(psuedonym.getBytes(StandardCharsets.UTF_8)); // add the bytes of the psuedonym
            long nonce = ThreadLocalRandom.current().nextLong();
            nonceBuffer.clear(); // clear nonce buffer
            sha.update(nonceBuffer.putLong(nonce).array()); // add random nonce and converts to bytes without using javas heavy string conversions. also need to find a way of caching the randoms
            byte[] bin = sha.digest(); // gets the new hash as bytes
            // Counting the zeros:
            int zeroCount = 0;
            for (byte b: bin) {
                int unsigned = b & 0xFF; // applies a bitmask?
                if (unsigned == 0) {
                    zeroCount += 8;
                } else {
                    zeroCount += Integer.numberOfLeadingZeros(unsigned) - 24; // Only top 8 bits?
                    break;
                }
            }
            //if (zeroCount > 20) System.out.println(zeroCount); // for testing
            // Checks for a leading block, outputs and changes shared previous hash for other threads
            if (zeroCount > mc.getLeadingZeros()) {
                mc.setLeadingZeros(zeroCount);
                StringBuilder hex = new StringBuilder();
                for (byte b : bin) {
                    hex.append(String.format("%02x", b)); // convert byte to hex
                }
                output.block(mc.getHash()+psuedonym+nonce);
                mc.setHash(hex.toString(), threadName);
            }
        }
        System.out.println(threadName + " Finshed ");
    }

    public void start () {
        if (t == null) {
            t = new Thread (this);
            t.start();
        }
    }

}
