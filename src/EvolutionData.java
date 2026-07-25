import java.util.HashMap;
import java.util.Map;

public class EvolutionData {

    public static class EvolutionInfo {
        public final String evolvedName;
        public final int levelRequired;
        public final String newType;
        public final String newMoveType;

        public EvolutionInfo(String evolvedName, int levelRequired, String newType, String newMoveType) {
            this.evolvedName = evolvedName;
            this.levelRequired = levelRequired;
            this.newType = newType;
            this.newMoveType = newMoveType;
        }
    }

    private static final Map<String, EvolutionInfo> EVOLUTIONS = new HashMap<>();

    static {
        // Starter Pokémon
        EVOLUTIONS.put("Charmander", new EvolutionInfo("Charmeleon", 7, "Fire", "Fire"));
        EVOLUTIONS.put("Charmeleon", new EvolutionInfo("Charizard", 16, "Fire", "Flying"));
        
        EVOLUTIONS.put("Squirtle", new EvolutionInfo("Wartortle", 7, "Water", "Water"));
        EVOLUTIONS.put("Wartortle", new EvolutionInfo("Blastoise", 16, "Water", "Water"));

        EVOLUTIONS.put("Bulbasaur", new EvolutionInfo("Ivysaur", 7, "Grass", "Poison"));
        EVOLUTIONS.put("Ivysaur", new EvolutionInfo("Venusaur", 16, "Grass", "Poison"));

        // Electric types
        EVOLUTIONS.put("Pikachu", new EvolutionInfo("Raichu", 10, "Electric", "Thunder"));

        // Grass types
        EVOLUTIONS.put("Chikorita", new EvolutionInfo("Bayleef", 7, "Grass", "Grass"));
        EVOLUTIONS.put("Bayleef", new EvolutionInfo("Meganium", 16, "Grass", "Grass"));

        // Flying types
        EVOLUTIONS.put("Pidgey", new EvolutionInfo("Pidgeotto", 6, "Normal", "Flying"));
        EVOLUTIONS.put("Pidgeotto", new EvolutionInfo("Pidgeot", 18, "Normal", "Flying"));
        EVOLUTIONS.put("Zubat", new EvolutionInfo("Golbat", 7, "Poison", "Flying"));
        EVOLUTIONS.put("Golbat", new EvolutionInfo("Crobat", 22, "Poison", "Flying"));

        // Eeveelutions
        EVOLUTIONS.put("Eevee", new EvolutionInfo("Vaporeon", 8, "Water", "Water"));
        EVOLUTIONS.put("Eevee", new EvolutionInfo("Jolteon", 8, "Electric", "Thunder"));
        EVOLUTIONS.put("Eevee", new EvolutionInfo("Flareon", 8, "Fire", "Fire"));

        // Other
        EVOLUTIONS.put("Jigglypuff", new EvolutionInfo("Wigglytuff", 10, "Fairy", "Normal"));
        EVOLUTIONS.put("Bidoof", new EvolutionInfo("Bibarel", 6, "Normal", "Water"));
    }

    public static boolean canEvolve(String name, int currentLevel) {
        EvolutionInfo info = EVOLUTIONS.get(name);
        return info != null && currentLevel >= info.levelRequired;
    }

    public static EvolutionInfo getEvolutionInfo(String name) {
        return EVOLUTIONS.get(name);
    }
}