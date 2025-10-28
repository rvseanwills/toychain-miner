import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Starting Toy Chain Miner");
        Thread.sleep(1000); // User Experience 0:

        System.out.println("⣿⣿⣿⣿⣿⣟⠛⠛⠻⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                "⣿⣿⣿⣿⣿⣿⣷⣦⣀⠀⠀⠈⠙⢿⣿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⣿\n" +
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⡀⠀⠸⣧⣀⣈⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠀⠀⣿\n" +
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⣦⡀⠈⠛⠛⢿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⣿\n" +
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⣹⣿⣦⡀⠀⠀⢻⣿⣷⠀⢠⣿⣿⡇⠀⠀⠀⣿\n" +
                "⣿⣿⣿⣿⣿⣿⣿⠟⠋⠀⣠⣾⣿⣿⣿⣷⣄⠀⠈⣿⣿⠀⣾⣿⣿⣿⠀⠀⠀⣿\n" +
                "⣿⣿⣿⣿⣿⠟⠁⠀⣠⣾⣿⣿⡟⠙⠻⣿⣿⣧⡀⢸⣿⣾⣿⣿⣿⣿⡆⠀⠀⣿\n" +
                "⣿⣿⣿⡟⠁⠀⣠⣾⣿⣿⣿⣿⣶⣦⣤⣤⣭⣿⣷⣼⣿⣿⣿⠋⠉⠁⠀⠀⠀⣿\n" +
                "⣿⡿⠋⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⣿\n" +
                "⣿⣿⣦⣾⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠁⠀⠀⢸⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⣿\n" +
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠼⣿⣿⣿⣿⣿⣿⣶⣄⠀⠀⠀⣿\n" +
                "⣿⣿⣿⣿⠛⠛⠻⠿⢿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠉⠛⢻⣿⣿⣿⠟⠀⠀⠀⣿\n" +
                "⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⡏⠀⠀⠀⠀⣿\n" +
                "⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠙⠛⠂⠀⠀⠀⣿\n" +
                "⣿⣿⣿⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣿");
        Thread.sleep(1000); // User Experience 0:
        String pseudonym = "corebyte";
        OutputManager output = new OutputManager(pseudonym);
        System.out.println("Successfully Opened/Created Log Files.");
        Thread.sleep(1000); // User Experience 0:
        Scanner input = new Scanner(System.in);
        System.out.println("Please Copy Your Previous Binary Hash (In Hex!): ");
        String hash = input.nextLine();
        System.out.println("Thank you, your nonce will be randomly generated per hash generation.");
        Thread.sleep(1000); // User Experience 0:
        System.out.println("Your Pseudonym has been predetermined to: corebyte.");
        Thread.sleep(1000); // User Experience 0:
        System.out.println("Running Miner, please note all further program output (ignoring runtime errors) will go to your new log files, thank you!");
        MiningManager mining = new MiningManager(hash, pseudonym, output);
    }
}