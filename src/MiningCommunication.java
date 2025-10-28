public class MiningCommunication {
    private String hash;
    private int leadingZeros;

    public MiningCommunication(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
    public void setHash(String hash, String tName) {
        this.hash = hash;
        System.out.println(tName + " Has set the hash to " + hash);
    }

    public void setLeadingZeros(int zeros, String tName) {
        this.leadingZeros = zeros;
    }

    public int getLeadingZeros() {
        return leadingZeros;
    }
}
