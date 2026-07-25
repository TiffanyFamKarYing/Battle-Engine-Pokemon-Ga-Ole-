import java.util.Scanner;

public class WildBattle {

    public static void start(Player player, Scanner sc) {
        Pokemon wild = DataLoader.getRandomPokemon();
        if (wild == null) {
            System.out.println("⚠️ No wild Pokémon available.");
            return;
        }

        System.out.println("\n🌿 A wild " + wild.getName() + " appeared!");
        System.out.println(wild.getStatus());

        while (true) {
            System.out.println("1. Fight\n2. Catch\n3. Run");
            System.out.print("What will you do? ");
            switch (sc.nextLine().trim()) {
                case "1" -> {
                    Pokemon ally = player.chooseOnePokemon();
                    if (ally != null) battle(ally, wild);
                    return;
                }
                case "2" -> {
                    if (attemptCatch(player, wild, sc)) return;
                }
                case "3" -> {
                	runSafeBanner();
                	System.out.println("\n🏃 You ran away safely.");
                    return;
                }
                default -> System.out.println("❎ Enter 1, 2 or 3.");
            }
        }
    }

    private static void battle(Pokemon ally, Pokemon enemy) {
        ally.resetHP();
        enemy.resetHP();

        while (!ally.isFainted() && !enemy.isFainted()) {
            int dmg = ally.attack(enemy);
            System.out.printf("%s attacks %s for %d damage.%n", ally.getName(), enemy.getName(), dmg);

            if (enemy.isFainted()) break;

            dmg = enemy.attack(ally);
            System.out.printf("%s counters %s for %d damage.%n", enemy.getName(), ally.getName(), dmg);
        }

        if (ally.isFainted()) {
        	printFaintedBanner(); // Call the banner here
            System.out.println("💀 " + ally.getName() + " fainted.");
        }
   

        if (enemy.isFainted()) {
            System.out.println(); 
            printDefeatedBanner();
            System.out.println("\n🎉 You defeated " + enemy.getName() + "!");
            ally.gainExp(30);

            if (ally.isReadyToEvolve()) {
                System.out.println("🌟 " + ally.getName() + " is ready to evolve!");
                System.out.println("✨ Evolve Pokémon to power up!");
            }

            UserManager.savePlayers();
        }
    }

    private static void printDefeatedBanner() {
        String banner =
            "\033[93m" +  // Bright Yellow (Gold-like)
            "\n _____                                                                      _____ \n" +
            "( ___ )                                                                    ( ___ )\n" +
            " |   |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|   | \n" +
            " |   | ██████╗ ███████╗███████╗███████╗ █████╗ ████████╗███████╗██████╗ ██╗ |   | \n" +
            " |   | ██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██║ |   | \n" +
            " |   | ██║  ██║█████╗  █████╗  █████╗  ███████║   ██║   █████╗  ██║  ██║██║ |   | \n" +
            " |   | ██║  ██║██╔══╝  ██╔══╝  ██╔══╝  ██╔══██║   ██║   ██╔══╝  ██║  ██║╚═╝ |   | \n" +
            " |   | ██████╔╝███████╗██║     ███████╗██║  ██║   ██║   ███████╗██████╔╝██╗ |   | \n" +
            " |   | ╚═════╝ ╚══════╝╚═╝     ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═════╝ ╚═╝ |   | \n" +
            " |___|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|___| \n" +
            "(_____)                                                                    (_____) \n" +
            "\033[0m"; // Reset color
        System.out.println(banner);
    }
    
    public static void printFaintedBanner(){
        String banner =
            "\033[91m" +  // Red color
            " _____                                                                 _____ \n" +
            "( ___ )                                                               ( ___ )\n" +
            " |   |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|   | \n" +
            " |   | ███████╗ █████╗ ██╗███╗   ██╗████████╗███████╗██████╗           |   | \n" +
            " |   | ██╔════╝██╔══██╗██║████╗  ██║╚══██╔══╝██╔════╝██╔══██╗          |   | \n" +
            " |   | █████╗  ███████║██║██╔██╗ ██║   ██║   █████╗  ██║  ██║          |   | \n" +
            " |   | ██╔══╝  ██╔══██║██║██║╚██╗██║   ██║   ██╔══╝  ██║  ██║          |   | \n" +
            " |   | ██║     ██║  ██║██║██║ ╚████║   ██║   ███████╗██████╔╝██╗██╗██╗ |   | \n" +
            " |   | ╚═╝     ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝   ╚═╝   ╚══════╝╚═════╝ ╚═╝╚═╝╚═╝ |   | \n" +
            " |___|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|___| \n" +
            "(_____)                                                               (_____) \n" +
            "\033[0m"; // Reset color

        System.out.println(banner);
    }

    private static void runSafeBanner() {
        String banner =
            "\033[94m" + // Bright Blue
            " _____                                                  _____ \n" +
            "( ___ )                                                ( ___ )\n" +
            " |   |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|   | \n" +
            " |   | ███████╗███████╗ ██████╗ █████╗ ██████╗ ███████╗ |   | \n" +
            " |   | ██╔════╝██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝ |   | \n" +
            " |   | █████╗  ███████╗██║     ███████║██████╔╝█████╗   |   | \n" +
            " |   | ██╔══╝  ╚════██║██║     ██╔══██║██╔═══╝ ██╔══╝   |   | \n" +
            " |   | ███████╗███████║╚██████╗██║  ██║██║     ███████╗ |   | \n" +
            " |   | ╚══════╝╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝     ╚══════╝ |   | \n" +
            " |___|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|___| \n" +
            "(_____)                                                (_____) \n" +
            "\033[0m"; // Reset color
        System.out.println(banner);
    }
    
    public static void printThrowBall() {
        String RED = "\u001B[31m";
        String WHITE = "\u001B[37m";
        String RESET = "\u001B[0m";
        System.out.println(" ");
        System.out.println(RED + "	⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣤⣶⣶⣿⣿⣿⣿⣿⣶⣶⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀" + RESET);
        System.out.println(RED + "	⠀⠀⠀⠀⠀⠀⣠⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣄⠀⠀⠀⠀⠀" + RESET);
        System.out.println(RED + "	⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡄⠀⠀⠀" + RESET);
        System.out.println(RED + "	⠀⠀⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠀⠀⠙⣿⣿⣿⣿⣿⣆⠀⠀" + RESET);
        System.out.println(RED + "	⠀⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠿⢿⣧⡀⠀⢠⣿⠟⠛⠛⠿⣿⡆⠀" + RESET);
        System.out.println(RED + "	⠀⢰⣿⣿⣿⣿⣿⣿⠿⠟⠋⠉⠁⠀⠀⠀⠀⠀⠙⠿⠿⠟⠋⠀⠀⠀⣠⣿⠇⠀" + RESET);
        System.out.println(RED + "	⠀⢸⣿⣿⡿⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣾⠟⠋⠀⠀" + RESET);
        System.out.println(RED + "	⠀⢸⣿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣤⣴⣾⠿⠛⠉⠀⠀⠀⠀⠀" + RESET);
        System.out.println(RED + "	⠀⠈⢿⣷⣤⣤⣄⣠⣤⣤⣤⣤⣶⣶⣾⠿⠿⠛⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀" + RESET);
        System.out.println(WHITE + "	⠀⢠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣦⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀" + RESET);
        System.out.println(WHITE + "	⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣄⠀⠀⠀⠀" + RESET);
        System.out.println(WHITE + "	⠀⢸⣿⡛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡀⠀" + RESET);
        System.out.println(WHITE + "	⠀⠀⢻⣧⠀⠈⠙⠛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀" + RESET);
        System.out.println(WHITE + "	⠀⠀⠈⢿⣧⠀⠀⠀⠀⠀⠀⠉⠙⠛⠻⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠁⠀" + RESET);
        System.out.println(WHITE + "	⠀⠀⠀⠀⠻⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣿⣿⣿⣿⠟⠀⣠⣾⠟⠀⠀⠀" + RESET);
        System.out.println(WHITE + "	⠀⠀⠀⠀⠀⠈⠻⣷⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⢀⣤⣾⠟⠁⠀⠀⠀⠀" + RESET);
        System.out.println(WHITE + "	⠀⠀⠀⠀⠀⠀⠀⠀⠙⠻⠿⣶⣦⣤⣤⣤⣤⣤⣤⣶⡿⠟⠋⠁⠀⠀⠀⠀⠀⠀" + RESET);
        System.out.println(WHITE + "	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" + RESET);
        System.out.println(" ");
    }


    private static boolean attemptCatch(Player player, Pokemon wild, Scanner sc) {
        System.out.println("You threw a Poké Ball!");
        printThrowBall();  // This prints the Poké Ball animation

        int choice = 0;
        // Loop until the player selects a valid Poké Ball number
        while (choice < 1 || choice > 4) {
            System.out.println("Choose a Poké Ball:");
            System.out.println("1. Poke Ball");
            System.out.println("2. Great Ball");
            System.out.println("3. Ultra Ball");
            System.out.println("4. Master Ball");

            // Get the player's choice
            choice = sc.nextInt();

            // Consume the newline character left by nextInt()
            sc.nextLine();

            // Check if the input is valid (1, 2, 3, or 4)
            if (choice < 1 || choice > 4) {
                System.out.println("❎ Invalid input. Please enter a number between 1 and 4.");
            }
        }

        // Determine which Poké Ball the player selects based on their valid choice
        Pokeball ball = switch (choice) {
            case 1 -> Pokeball.POKE_BALL;
            case 2 -> Pokeball.GREAT_BALL;
            case 3 -> Pokeball.ULTRA_BALL;
            case 4 -> Pokeball.MASTER_BALL;
            default -> Pokeball.POKE_BALL; // This should never be reached because of the validation
        };

        System.out.println("You threw a " + ball.name() + "!");
        
        // Adjust catch rate based on the Pokémon's strength (level or power)
        double adjustedCatchRate = ball.getCatchRate();

        // If Pokémon is stronger, reduce the effectiveness of weaker Poké Balls
        if (wild.getLevel() > 10) { // For example, any Pokémon with level > 10 is considered stronger
            if (ball == Pokeball.POKE_BALL) {
                adjustedCatchRate *= 0.4; // Reduce catch rate by 60% for Poke Ball
            } else if (ball == Pokeball.GREAT_BALL) {
                adjustedCatchRate *= 0.7; // Reduce catch rate by 30% for Great Ball
            }
        }

        // Ensure the Master Ball always succeeds
        if (ball == Pokeball.MASTER_BALL) {
            adjustedCatchRate = 1.0;
        }

        // Determine the catch success based on the adjusted catch rate
        boolean caught = Math.random() < adjustedCatchRate;

        if (caught) {
            player.addPokemon(wild);  // Add the caught Pokémon to the player's collection
            wild.gainExpSilent(50);  // Give the Pokémon 50 EXP silently (no notification)
            System.out.println("✅ Gotcha! " + wild.getName() + " was caught!");
            UserManager.savePlayers();  // Save player data
            return true;  // Return true, indicating the Pokémon was caught
        } else {
            // If the Pokémon broke free, ask the player to try again
            System.out.print("The Pokémon broke free! Try again? (yes/no): ");
            String response = sc.nextLine().trim();  // Clean any excess newline characters
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("🏃 You gave up and left.");  // Message only when the player gives up
                return true;  // Exit the method as the player gave up
            } else {
                System.out.println("You chose to try again!");  // Optionally show this if they want to retry
            }
        }

        return false;  // If no success, return false to indicate the Pokémon was not caught
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}

