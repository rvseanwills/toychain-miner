public class MiningThread implements Runnable {
    private final String prevHash;
    private final String psuedonym;
    private final OutputManager output;
    private final MiningCommunication mc;
    public final String threadName;
    private Thread t;


    MiningThread( String prevHash, String psuedonym, OutputManager output, MiningCommunication mc, String threadName) {
        this.prevHash = prevHash;
        this.psuedonym = psuedonym;
        this.output = output;
        this.mc = mc;
        this.threadName = threadName;
    }

    public void run() {
        new MiningManager(prevHash, psuedonym, output, mc, threadName);
    }

    public void start () {
        if (t == null) {
            t = new Thread (this);
            t.start();
        }
    }

}
