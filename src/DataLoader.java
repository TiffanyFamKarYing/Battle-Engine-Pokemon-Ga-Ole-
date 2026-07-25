import java.util.Random;

public class DataLoader {

    private static final Random R = new Random();

    private static final String[] COMMON = {
        "Charmander", "Squirtle", "Bulbasaur", "Pikachu", "Eevee", "Chikorita",
        "Pidgey", "Zubat", "Jigglypuff", "Bidoof"
    };

    private static final String[] UNCOMMON = {
        "Wartortle", "Jolteon", "Vaporeon", "Ninetales", "Pidgeotto", "Golbat"
    };

    private static final String[] RARE = {
        "Charizard", "Blastoise", "Venusaur", "Typhlosion", "Snorlax", "Lucario",
        "Garchomp", "Gengar", "Lugia", "Gardevoir", "Dragonite", "Raichu",
        "Meganium", "Pidgeot", "Crobat", "Wigglytuff", "Bibarel"
    };

    private static final String[] LEGENDARY = {
        "Mew", "Mewtwo", "Rayquaza"
    };

    private static final String[] TYPES = {
        "Fire", "Water", "Grass", "Electric", "Normal", "Psychic", "Fighting", "Dragon", "Poison", "Fairy"
    };

    // Returns a random Pokémon based on rarity
    public static Pokemon getRandomPokemon() {
        int roll = R.nextInt(100);
        String name = switch (roll) {
            case 0 -> get(LEGENDARY); // 1% Legendary
            default -> (roll < 60) ? get(COMMON)
                    : (roll < 85) ? get(UNCOMMON)
                    : (roll < 97) ? get(RARE)
                    : get(LEGENDARY); // small chance again
        };

        String type = get(TYPES);
        String moveType = get(TYPES);
        int hp = 100 + R.nextInt(7) * 10;
        int power = 25 + R.nextInt(30);

        return new Pokemon(name, type, moveType, hp, power);
    }

    // Now uses EvolutionData's methods
    public static EvolutionData.EvolutionInfo getEvolution(String name) {
        return EvolutionData.getEvolutionInfo(name);
    }

    // Utility to get a random item from a list
    private static String get(String[] arr) {
        return arr[R.nextInt(arr.length)];
    }
}
