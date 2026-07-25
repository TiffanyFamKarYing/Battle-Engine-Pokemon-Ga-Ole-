import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PlayerStorageManager {

    private static final String PLAYER_FILE = "players_data.txt";  // clearer filename

    // Save the player and their Pokémon collection
    public static void save(Map<String, Player> players) {
        List<String> names = new ArrayList<>(players.keySet());
        Collections.sort(names);

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(PLAYER_FILE), StandardCharsets.UTF_8))) {
            for (String name : names) {
                Player p = players.get(name);
                pw.println(name);  // Write the player name
                for (Pokemon mon : p.getCollection()) {
                    // Write Pokémon details: Name, Type, MoveType, Max HP, Current HP, Power, Level
                    pw.printf("%s,%s,%s,%d,%d,%d,%d%n",
                            mon.getName(), mon.getType(), mon.getMoveType(),
                            mon.getMaxHP(), mon.getCurrentHP(), mon.getPower(), mon.getLevel());
                }
                pw.println("---");  // End of this player's data
            }
        } catch (IOException e) {
            System.out.println("❎ Failed to save players: " + e.getMessage());
        }
    }

    // Load the players and their Pokémon collections from the file
    public static Map<String, Player> load() {
        Map<String, Player> players = new LinkedHashMap<>();
        File file = new File(PLAYER_FILE);
        if (!file.exists()) return players;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            Player current = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals("---")) { 
                    current = null; // Mark the end of the current player's data
                    continue; 
                }

                if (current == null) {
                    // Start a new player with the name in this line
                    current = new Player(line);
                    players.put(line, current);
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 7) continue; // Skip invalid lines

                try {
                    String n        = parts[0].trim();
                    String type     = parts[1].trim();
                    String moveType = parts[2].trim();
                    int maxHP       = Integer.parseInt(parts[3].trim());
                    int curHP       = Integer.parseInt(parts[4].trim());
                    int power       = Integer.parseInt(parts[5].trim());
                    int level       = Integer.parseInt(parts[6].trim());

                    // Create and add Pokémon to the current player's collection
                    Pokemon mon = new Pokemon(n, type, moveType, maxHP, power);
                    mon.setCurrentHP(curHP);
                    mon.setLevel(level);
                    current.addPokemon(mon);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Error reading Pokémon data: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("❎ Failed to load players: " + e.getMessage());
        }
        return players;
    }
}
