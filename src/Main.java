import java.util.*;

public class Main {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\u26A1 Welcome to the adventure, Trainer! The world of Pokémon awaits you.");
        System.out.println("Ready to begin an epic journey full of challenges, new friends, and wild encounters?");
        System.out.print("Enter your name and let's get started: ");
        String playerName = SC.nextLine().trim();

        Player player = UserManager.getOrCreatePlayer(playerName);

        // ── banner printed once ────────────────────────────────────────────────────
        printBanner();
        // ──────────────────────────────────────────────────────────────────────────

        // ===================== main menu loop =====================
        while (true) {
            System.out.println("\n===== Pokémon Ga-Ole Game =====");
            System.out.println("1. Catch Pokémon");
            System.out.println("2. View Collection");
            System.out.println("3. Wild Battle");
            System.out.println("4. Rival Battle");
            System.out.println("5. Check Evolution"); 
            System.out.println("6. Heal All Pokémon");
            System.out.println("7. Release Pokémon");
            System.out.println("8. View Top Scores");
            System.out.println("9. Trainer Status");
            System.out.println("10. View Pokémon Details");
            System.out.println("11. Save & Exit");
            System.out.print("Choose: ");

            int choice = getValidIntInput(1, 11);

            switch (choice) {
                case 1 -> player.catchPokemon();
                case 2 -> player.displayCollection();  
                case 3 -> WildBattle.start(player, SC);
                case 4 -> RivalBattle.start(player, SC);
                case 5 -> {
                    boolean anyShown = false;
                    for (Pokemon p : player.getCollection()) {
                        EvolutionData.EvolutionInfo info = p.getEvolutionInfo();
                        if (info != null) {
                            int levelDiff = info.levelRequired - p.getLevel();
                            if (levelDiff <= 0) {
                                // Ready to evolve — prompt
                                p.checkForEvolution(SC);
                                anyShown = true;
                            } else {
                           
                                System.out.println("🔸 " + p.getName() + " needs " + levelDiff + " more level(s) to evolve into " + info.evolvedName + ".");
                                anyShown = true;
                            }
                        } else {
                          
                            System.out.println("❌ " + p.getName() + " does not evolve.");
                        }
                    }

                    if (!anyShown) {
                        System.out.println("🔍 None of your Pokémon have evolutions.");
                    }
                }
                case 6 -> { player.healAll(); System.out.println("\u2728 All Pokémon have been healed!"); }
                case 7 -> handleRelease(player);
                case 8 -> ScoreManager.displayTopScores();
                case 9 -> {
                    TrainerStatusService.displayPlayerStatus(player);
                    TrainerStatusService.exportAllTrainerStatuses();
                }
                case 10 -> ViewPokemonDetails.show(player, SC);
                case 11 -> {
                    UserManager.savePlayers();
                    TrainerStatusService.exportAllTrainerStatuses();
                    System.out.println("\u2705 Progress saved. Goodbye!");
                    
                    System.out.println(
                    	    "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣀⡀⢸⣿⣷⣶⣤⣄⣀⡀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⠛⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⠛⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⢀⣀⣀⣀⣀⣀⣿⣦⡀⠀⢸⣿⢿⣿⣿⣿⣿⣿⣿⠀⣿⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸⡇⠀⣿⣿⣿⣿⣿⣿⠀⣿⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠈⠉⠉⠉⠉⠉⣿⠟⠁⠀⢸⣿⣾⣿⣿⣿⣿⣿⣿⠀⣿⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⣤⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⠀⣤⠀⠀⠀⠀⠀⠀⠀\n" +
                    	    "⠀⠀⣀⣀⣀⣀⣀⣀⣀⣀⣀⡀⢸⣿⡿⠿⠛⠋⣉⣁⣀⣀⣀⣀⣀⣀⣀⣀⠀⠀\n" +
                    	    "⠀⠀⠉⠉⠉⠉⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀⠈⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠀⠀"
                    	);
                    return;
                }
            }
        }
    }

    // ---------------- banner art ----------------
    private static void printBanner() {
        System.out.println("\u001B[31m                            W E L C O M E !                      \u001B[0m");
        System.out.println("\u001B[33m    _.----.         ____                   ___    ___     ____   \u001B[0m");
        System.out.println("\u001B[32m _,-'       `.     |    |  /`.      ,-'   |   \\  /   |    |    \\  |`. \u001B[0m");
        System.out.println("\u001B[36m \\      __    \\    '-.  | /   `.  ___    |    \\/    |    '-.   \\ |  | \u001B[0m");
        System.out.println("\u001B[34m  \\\\._   \\ \\   |  __  |  |/    ,'','_  `. |          | __  |    \\|  | \u001B[0m");
        System.out.println("\u001B[35m    \\    \\/   /,' _`.|      ,' / / / /   |   (o_o)  ,' _`.|     |  | \u001B[0m");
        System.out.println("\u001B[31m     \\     ,-'/  /   \\    ,'   | / / ,`.|         /  /   \\  |     | \u001B[0m");
        System.out.println("\u001B[33m      \\    \\ |   \\_/  |   `-.  \\    `'  /|  |    ||   \\_/  | |\\    | \u001B[0m");
        System.out.println("\u001B[32m       \\    \\ \\      /       `-.`.___,-' |  |\\  /| \\      /  | |   | \u001B[0m");
        System.out.println("\u001B[36m        \\    \\ `.__,'|  |`-._    `|      |__| \\/ |  `.__,'|  | |   | \u001B[0m");
        System.out.println("\u001B[34m         \\_.-'       |__|    `-._ |              '-.|     '-.| |   | \u001B[0m");
        System.out.println("\u001B[35m                                 `'                            '-._| \u001B[0m");
    }

    // ---------------- helper input methods ----------------
    private static void handleRelease(Player player) {
        while (true) {
            player.displayCollection();  // Shows the collection here
            if (player.getCollectionSize() == 0) break;

            System.out.print("Enter a Pokémon number to release: ");
            int idx = getValidIntInput(1, player.getCollectionSize()) - 1;

            Pokemon selected = player.getPokemon(idx);
            if (selected == null) {
                System.out.println("❎ Invalid selection. Try again.");
                continue;
            }

            System.out.print("Are you sure you want to release " + selected.getName() + "? (yes/no): ");
            String confirm = SC.nextLine().trim().toLowerCase();

            if (confirm.equals("yes")) {
                Pokemon released = player.removePokemon(idx);
                System.out.println("\u2705 You have released " + released.getName() + " back into the wild.");
                UserManager.savePlayers();
                break;
            } else if (confirm.equals("n")) {
                System.out.println("⤴️ Release cancelled.");
                break;
            } else {
                System.out.println("❎ Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    public static int getValidIntInput(int min, int max) {
        while (true) {
            String input = SC.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) return val;
                System.out.print("❌ Enter a number (" + min + " to " + max + "): ");
            } catch (NumberFormatException ex) {
                System.out.print("❌ Invalid input. Enter a number (" + min + " to " + max + "): ");
            }
        }
    }
}
