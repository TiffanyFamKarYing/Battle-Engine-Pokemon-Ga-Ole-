import java.util.Scanner;

public class ViewPokemonDetails {
    private static final String DETAILS_FORMAT = "%-10s: %s";
    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_RED = "\u001B[31m";

    public static void show(Player player, Scanner scanner) {
        if (player == null || scanner == null) {
            System.out.println("❌ Error: Invalid parameters");
            return;
        }

        if (!hasPokemon(player)) {
            return;
        }

        Pokemon pokemon = selectPokemon(player, scanner);
        if (pokemon == null) {
            return;
        }

        displayPokemonDetails(pokemon);
    }

    private static boolean hasPokemon(Player player) {
        if (player.getCollectionSize() == 0) {
            System.out.println("❎ You have no Pokémon.");
            return false;
        }
        player.displayCollection();
        return true;
    }

    private static Pokemon selectPokemon(Player player, Scanner scanner) {
        System.out.print("Enter the number of the Pokémon to view: ");
        int index = Main.getValidIntInput(1, player.getCollectionSize()) - 1;
        return player.getPokemon(index);
    }

    private static void displayPokemonDetails(Pokemon pokemon) {
        PokemonArt.displayArt(pokemon.getName());  // Changed from getArt() to displayArt()
        displayDetailsHeader();
        displayBasicDetails(pokemon);
        displaySpecialTypeIfCharizard(pokemon);
        displayEvolutionInfo(pokemon);
    }

    private static void displayDetailsHeader() {
        System.out.println("\n📋 Pokémon Details");
    }

    private static void displayBasicDetails(Pokemon pokemon) {
        printDetail("Name", pokemon.getName());
        printDetail("Level", String.valueOf(pokemon.getLevel()));
        printDetail("Type", pokemon.getType());
        printDetail("Move Type", pokemon.getMoveType());
        printDetail("HP", pokemon.getCurrentHP() + "/" + pokemon.getMaxHP());
        printDetail("Power", String.valueOf(pokemon.getPower()));
        printDetail("EXP", String.valueOf(pokemon.getExp()));
    }

    private static void printDetail(String label, String value) {
        System.out.println(String.format(DETAILS_FORMAT, label, value));
    }

    private static void displaySpecialTypeIfCharizard(Pokemon pokemon) {
        if ("Charizard".equals(pokemon.getName())) {
            System.out.println(COLOR_RED + String.format(DETAILS_FORMAT, "Type", "Fire/Flying") + COLOR_RESET);
        }
    }
    
    private static void displayEvolutionInfo(Pokemon pokemon) {
        EvolutionData.EvolutionInfo evo = EvolutionData.getEvolutionInfo(pokemon.getName());

        System.out.println("\n🔄 Evolution Info:");

        if (evo == null) {
            System.out.println("❌ This Pokémon does not evolve.");
            return;
        }

        boolean canEvolve = EvolutionData.canEvolve(pokemon.getName(), pokemon.getLevel());

        System.out.println("➡ Evolves To   : " + evo.evolvedName);
        System.out.println("🆕 New Type     : " + evo.newType);
        System.out.println("🎯 New MoveType : " + evo.newMoveType);
        System.out.println("📶 Requirement  : Level " + evo.levelRequired);
        System.out.println("✅ Ready to Evolve? " + (canEvolve ? "Yes!" : "Not yet"));
    }

}