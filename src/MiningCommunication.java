public class MiningCommunication {
    private String hash;
    private int leadingZeros;
    private int attempts;

    public MiningCommunication(String hash) {
        this.attempts = 0;
        this.hash = hash;
    }
    public void incrementAttempts() {
        this.attempts++;
    }

    public void printAttempts() {
        System.out.println("Attempts: " + attempts);
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash, String tName) {
        this.hash = hash;
        System.out.println(tName + " Has set the hash to " + hash);
    }

    public void setLeadingZeros(int zeros) {
        this.leadingZeros = zeros;
    }

    public int getLeadingZeros() {
        return leadingZeros;
    }
}
