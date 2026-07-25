import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

public class TrainerStatusService {

    // Map to hold the top scores of players (for example)
    private static final Map<String, Integer> MAP = new HashMap<>();
    private static final String FILE = "trainer_status.txt"; // The file to store player data

    // Method to display the player status
    public static void displayPlayerStatus(Player player) {
        System.out.println("\n🎖️ ===== Trainer Status =====");
        System.out.println("🧑‍🎓 Name            : " + player.getName());
        System.out.println("🔢 Total Pokémon     : " + player.getCollectionSize());
        System.out.printf("📈 Average Level     : %.2f%n", player.getAverageLevel());
        System.out.println("💪 Strongest Pokémon : " + player.getStrongestPokemonName());

        if (player.getCollectionSize() > 0) {
            Pokemon last = player.getCollection().get(player.getCollectionSize() - 1);
            System.out.println("🎉 Last Caught       : " + last.getName() + " [Lv " + last.getLevel() + "]");

            int idx = 1;
            for (Pokemon p : player.getCollection()) {
                System.out.printf("   %d. 🐾 %s [Lv %d] ❤️ (%d/%d HP) ⚡ Power: %d%n", idx++, p.getName(), p.getLevel(), p.getCurrentHP(), p.getMaxHP(), p.getPower());
            }
        } else {
            System.out.println("📭 You have no Pokémon yet. Go catch some!");
        }
        System.out.println("🗃️ ============================\n");
    }

    // Export trainer statuses to a file, recording the scores
    public static void exportAllTrainerStatuses() {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(FILE), StandardCharsets.UTF_8))) {
            for (Player player : UserManager.getAllPlayers().values()) {
                pw.println("🎖️ Trainer: " + player.getName());
                pw.println("   Total Pokémon: " + player.getCollectionSize());
                pw.println("   Strongest Pokémon: " + player.getStrongestPokemonName());
                pw.printf("   Average Level: %.2f%n", player.getAverageLevel());
                pw.println("   Pokémon Caught:");

                int idx = 1;
                for (Pokemon p : player.getCollection()) {
                    pw.printf("       %d. %s [Lv %d] Power: %d%n", idx++, p.getName(), p.getLevel(), p.getPower());
                }
                pw.println("🗃️ ============================\n");
            }
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Optional method to simulate adding a score manually (for testing)
    public static void addTestScore(String playerName, int score) {
        MAP.put(playerName, score);
        exportAllTrainerStatuses(); // Write the updated scores to file
    }
}
