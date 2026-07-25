import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final List<Pokemon> collection;
    private final Map<String, Integer> evolutionItems = new HashMap<>();

    public Player(String name) {
        this.name = Objects.requireNonNull(name, "Player name cannot be null");
        this.collection = new ArrayList<>();
        initializeBasicItems();
    }
    
    private void initializeBasicItems() {
        // Start with some basic evolution items
        evolutionItems.put("Fire Stone", 1);
        evolutionItems.put("Water Stone", 1);
        evolutionItems.put("Thunder Stone", 1);
        evolutionItems.put("Leaf Stone", 1);
    }


    public void catchPokemon() {
        List<Pokemon> wildPokemon = generateWildPokemon(3);
        displayWildPokemon(wildPokemon);
        
        Pokemon caught = selectPokemonToCatch(wildPokemon);
        addCaughtPokemon(caught);
        displayCatchResult(caught);
    }

    private List<Pokemon> generateWildPokemon(int count) {
        List<Pokemon> wildPokemon = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            wildPokemon.add(DataLoader.getRandomPokemon());
        }
        return wildPokemon;
    }

    private void displayWildPokemon(List<Pokemon> wildPokemon) {
        System.out.println("\nThe wild Pokémon appears! Choose one to catch:");
        for (int i = 0; i < wildPokemon.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, wildPokemon.get(i).getStatus());
        }
    }

    private Pokemon selectPokemonToCatch(List<Pokemon> wildPokemon) {
        int choice = Main.getValidIntInput(1, wildPokemon.size());
        return wildPokemon.get(choice - 1);
    }

    private void addCaughtPokemon(Pokemon pokemon) {
        collection.add(pokemon);
        pokemon.gainExpSilent(50);
    }

    private void displayCatchResult(Pokemon caught) {
        PokemonArt.displayArt(caught.getName());
        System.out.printf("✅ You caught %s!%n", caught.getName());
    }

    public Pokemon chooseOnePokemon() {
        if (collection.isEmpty()) {
            System.out.println("❎ You have no Pokémon in your collection.");
            return null;
        }
        displayCollection();
        return selectPokemonFromCollection();
    }

    private Pokemon selectPokemonFromCollection() {
        int index = Main.getValidIntInput(1, collection.size()) - 1;
        return collection.get(index);
    }

    public void displayCollection() {
        if (collection.isEmpty()) {
            System.out.println("💼 Your Pokémon Collection is empty.");
            return;
        }

        // Beautified red ASCII art before showing the collection
        System.out.println("\u001B[93m" + 
                "*******************************************************************************\n" +
                "          |                   |                  |                     |\n" +
                " _________|________________.=\"\"_;=.______________|_____________________|_______\n" +
                "|                   |  ,-\"_,=\"     `\"=.|                  |\n" +
                "|___________________|__\"=._o`\"-._        `\"=.______________|___________________\n" +
                "          |                `\"=._o`\"=._      _`\"=._                     |\n" +
                " _________|_____________________:=._o \"=._.\"_.-=\"'\"=.__________________|_______\n" +
                "|                   |    __.--\" , ; `\"=._o.\" ,-\"\"\"-._ \".   |\n" +
                "|___________________|_._\"  ,. .` ` `` ,  `\"-._\"-._   \". '__|___________________\n" +
                "          |           |o`\"=._` , \"` `; .\". ,  \"-._\"-._; ;              |\n" +
                " _________|___________| ;`-.o`\"=._; .\" ` '`.\"\\` . \"-._ /_______________|_______\n" +
                "|                   | |o;    `\"-.o`\"=._``  '` \" ,__.--o;   |\n" +
                "|___________________|_| ;     (#) `-.o `\"=`_.--\"_o.-; ;___|___________________\n" +
                "____/______/______/___|o;._    \"      `\".o|o_.--\"    ;o;____/______/______/____\n" +
                "/______/______/______/\"=._o--._        ; | ;        ; ;/______/______/______/_\n" +
                "____/______/______/______/__\"=._o--._   ;o|o;     _._;o;____/______/______/____\n" +
                "/______/______/______/______/____\"=._o._; | ;_.--\"o.--\"_/______/______/______/_\n" +
                "____/______/______/______/______/_____\"=.o|o_.--\"\"___/______/______/______/____\n" +
                "/______/______/______/______/______/______/______/______/______/______/______/_\n" +
                "*******************************************************************************\n" +
                "\u001B[0m"); // Reset color

    
        System.out.println("💼 Your Pokémon:");
        int index = 1;
        for (Pokemon pokemon : collection) {
            String status = String.format("%d. 🐾 %s [Lv %d] ❤️ (%d/%d HP)",
                    index++, pokemon.getName(), pokemon.getLevel(),
                    pokemon.getCurrentHP(), pokemon.getMaxHP());
            System.out.println(normalizeWhitespace(status));
        }
    }


    private String normalizeWhitespace(String text) {
        return text.replace("\u00A0", " ")
                  .replace("\u2009", " ")
                  .replace("\u202F", " ");
    }

    public Pokemon getPokemon(int index) {
        if (index < 0 || index >= collection.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return collection.get(index);
    }

    public void healAll() {
        collection.forEach(Pokemon::resetHP);
        System.out.println("\u001B[31m" + // Start red text
                "                                                      \n" +
                "                       #                               \n" +
                "                       ##                             \n" +
                "                     ######                           \n" +
                "                 ############                         \n" +
                "               ############                           \n" +
                "            ######     ###                            \n" +
                "          #####        #                               \n" +
                "         #####                                        \n" +
                "        ####                                          \n" +
                "       ####       #####       #####                   \n" +
                "      ####      #########   #########                \n" +
                "     ####      #######################               \n" +
                "     ####      #######################               \n" +
                "     ####      #######################               \n" +
                "     ####       #####################        ###     \n" +
                "     ####        ###################        ####     \n" +
                "     ####         #################         ####     \n" +
                "      ###           #############           ####     \n" +
                "      ####           ###########           ####      \n" +
                "       ####            #######            ####       \n" +
                "        ####             ###             ####        \n" +
                "         #####                         #####         \n" +
                "           #####                     #####           \n" +
                "             #######             #######             \n" +
                "               #######################               \n" +
                "                    #############                    \n" +
                "                                                      \n" +
                "                                                      \n" +
                "\u001B[0m"); // Reset color
        System.out.println("💧 All Pokémon healed!");
    }


    // Getters
    public String getName() { return name; }
    public int getCollectionSize() { return collection.size(); }
    public Pokemon removePokemon(int index) { 
        return collection.remove(index); 
    }
    public void addPokemon(Pokemon pokemon) { 
        collection.add(pokemon); 
    }
    public List<Pokemon> getCollection() { 
        return Collections.unmodifiableList(collection); 
    }

    public double getAverageLevel() {
        return collection.stream()
                .mapToInt(Pokemon::getLevel)
                .average()
                .orElse(0);
    }

    public String getStrongestPokemonName() {
        return collection.stream()
                .max(Comparator.comparingInt(Pokemon::getPower))
                .map(p -> String.format("%s (Power: %d)", p.getName(), p.getPower()))
                .orElse("None");
    }
}
