import java.io.*;
import java.util.*;

public class ScoreManager {

    private static final String FILE = "top_scores.txt";
    private static final Map<String, Integer> MAP = new HashMap<>();

    // Save the top scores
    public static void saveScore(String name, int score) {
        load();  // Load existing scores from the file

        // Update the player's score if it's higher than the current one
        if (score > MAP.getOrDefault(name, 0)) {
            MAP.put(name, score);  // Save the new high score
            save();  // Save the updated scores to the file
            System.out.println("💾 New top score recorded for " + name + ": " + score);
        }
    }

    // Display the top 5 scores
    public static void displayTopScores() {
        load();  // Load existing scores from the file

        // Check if there are no scores yet
        if (MAP.isEmpty()) {
            System.out.println("📉 No top scores yet.");
            return;
        }

        // Sort the scores in descending order
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(MAP.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());  // Sort in descending order of score

        System.out.println("\n🏅 Top 5 Scores:");
        int rank = 1;
        for (Map.Entry<String, Integer> e : sorted.subList(0, Math.min(5, sorted.size()))) {
            System.out.println(rank++ + ". " + e.getKey() + ": " + e.getValue());
        }
    }

    /* ---------------- persistence helpers ---------------- */
    private static void load() {
        MAP.clear();  // Clear any previous data in the map
        File f = new File(FILE);

        // If the file doesn't exist, we simply return
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            // Read each line in the file
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length != 2) continue;  // Skip invalid lines
                // Add each player's name and score to the map
                MAP.put(p[0].trim(), Integer.parseInt(p[1].trim()));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("❌ Error reading scores file: " + e.getMessage());
        }
    }

    private static void save() {
        // Sort the scores and save only the top 5
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(MAP.entrySet());
            sorted.sort((a, b) -> b.getValue() - a.getValue());  // Sort in descending order of score
            for (Map.Entry<String, Integer> e : sorted.subList(0, Math.min(5, sorted.size()))) {  // Save only top 5 scores
                pw.println(e.getKey() + "," + e.getValue());  // Write the player name and score to the file
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving scores file: " + e.getMessage());
        }
    }
}
