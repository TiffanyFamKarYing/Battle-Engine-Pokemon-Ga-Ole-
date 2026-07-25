// --------------------------------- UserManager.java -----------------------------------
import java.util.*;

public class UserManager {

    private static final Map<String, Player> PLAYERS = new LinkedHashMap<>();

    public static Player getOrCreatePlayer(String name) {
        loadPlayers();
        if (PLAYERS.containsKey(name)) {
            Player p = PLAYERS.get(name);
            System.out.println("\n✅ Welcome back, Trainer " + name + "!");
            if (p.getCollectionSize() > 0) {
                for (Pokemon mon : p.getCollection()) mon.gainExp(50);
            } else {
                System.out.println("🔍 Time to catch your first Pokémon!");
            }
            return p;
        }
        Player newbie = new Player(name);
        PLAYERS.put(name, newbie);
        savePlayers();
        System.out.println("\n👋 Hello, new Trainer " + name + "! Your journey begins now.\n");
        return newbie;
    }

    public static void savePlayers() { PlayerStorageManager.save(PLAYERS); }
    public static Map<String, Player> getAllPlayers() { loadPlayers(); return Collections.unmodifiableMap(PLAYERS); }

    private static void loadPlayers() { if (!PLAYERS.isEmpty()) return; PLAYERS.putAll(PlayerStorageManager.load()); }
}