import java.io.IOException;
import java.util.Scanner;

// Genesis Hash: 00000274c5d7140b5663e4d2db2e2169ac9cc06e3270c3a7e5eafe0d82d50266

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        String pseudonym = "corebyte";
        OutputManager output = new OutputManager(pseudonym);
        Scanner input = new Scanner(System.in);
        System.out.println("Please Copy Your Previous Binary Hash (In Hex!): ");
        String hash = input.next();
        MiningCommunication mc = new MiningCommunication(hash);
        mc.setLeadingZeros(calculateLeadingZeros(hash));
        System.out.println("Starting Zeros: " + mc.getLeadingZeros());
        int numOfThreads = 20;
        for (int i = 0; i < numOfThreads; i++) {
            Long startNonce = (Long.MAX_VALUE/numOfThreads)*i;
            Long endNonce = (i == numOfThreads-1) ? Long.MAX_VALUE : (i+1) * ((Long.MAX_VALUE/numOfThreads) -1);
            MiningThread thre = new MiningThread(mc.getHash(), pseudonym, output, mc, "Thread "+i, startNonce, endNonce);
            thre.start();
        }

//        System.out.println("Starting Toy Chain Miner");
//        Thread.sleep(1000); // User Experience 0:
//        Scanner input = new Scanner(System.in);
//        System.out.println("Performance Mode? Only new blocks will be added to the file logs. ('Y') ");
//        char inp = input.next().charAt(0);
//        if (inp == 'Y') {
//            OutputManager output = new OutputManager(pseudonym);
//            System.out.println("Please Copy Your Previous Binary Hash (In Hex!): ");
//            String hash = input.next();
//            MiningCommunication mc = new MiningCommunication(hash);
//            for (int i = 0; i < 8; i++) {
//                MiningThread thre = new MiningThread(mc.getHash(), pseudonym, output, mc, "Thread "+i);
//                thre.start();
//            }
//        } else {
//            System.out.println("⣿⣿⣿⣿⣿⣟⠛⠛⠻⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
//                    "⣿⣿⣿⣿⣿⣿⣷⣦⣀⠀⠀⠈⠙⢿⣿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⣿\n" +
//                    "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⡀⠀⠸⣧⣀⣈⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠀⠀⣿\n" +
//                    "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⣦⡀⠈⠛⠛⢿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⣹⣿⣦⡀⠀⠀⢻⣿⣷⠀⢠⣿⣿⡇⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⣿⣿⣿⣿⠟⠋⠀⣠⣾⣿⣿⣿⣷⣄⠀⠈⣿⣿⠀⣾⣿⣿⣿⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⣿⣿⠟⠁⠀⣠⣾⣿⣿⡟⠙⠻⣿⣿⣧⡀⢸⣿⣾⣿⣿⣿⣿⡆⠀⠀⣿\n" +
//                    "⣿⣿⣿⡟⠁⠀⣠⣾⣿⣿⣿⣿⣶⣦⣤⣤⣭⣿⣷⣼⣿⣿⣿⠋⠉⠁⠀⠀⠀⣿\n" +
//                    "⣿⡿⠋⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⣿\n" +
//                    "⣿⣿⣦⣾⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠁⠀⠀⢸⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠼⣿⣿⣿⣿⣿⣿⣶⣄⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⣿⠛⠛⠻⠿⢿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠉⠛⢻⣿⣿⣿⠟⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⡏⠀⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠙⠛⠂⠀⠀⠀⣿\n" +
//                    "⣿⣿⣿⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣿");
//            OutputManager output = new OutputManager(pseudonym);
//            Thread.sleep(1000); // User Experience 0:
//            System.out.println("Successfully Opened/Created Log Files.");
//            Thread.sleep(1000); // User Experience 0:
//            Scanner input1 = new Scanner(System.in);
//            System.out.println("Please Copy Your Previous Binary Hash (In Hex!): ");
//            String hash = input1.nextLine();
//            System.out.println("Thank you, your nonce will be randomly generated per hash generation.");
//            Thread.sleep(1000); // User Experience 0:
//            System.out.println("Your Pseudonym has been predetermined to: corebyte.");
//            Thread.sleep(1000); // User Experience 0:
//            System.out.println("Running Miner, please note all further program output (ignoring runtime errors) will go to your new log files, thank you!");
//
//            new MiningManager(hash, pseudonym, output, true, null, "");
       // }
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