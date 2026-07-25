// -------------------------------- RivalBattle.java -----------------------------------
import java.util.Scanner;

public class RivalBattle {

    public static void start(Player player, Scanner sc) {
        System.out.println("\n🔥 A rival trainer challenges you with 2 Pokémon!");
        Pokemon enemy1 = DataLoader.getRandomPokemon();
        Pokemon enemy2 = DataLoader.getRandomPokemon();
        System.out.println("Rival's team:");
        System.out.println("1. " + enemy1.getStatus());
        System.out.println("2. " + enemy2.getStatus());

        if (player.getCollectionSize() < 2) { System.out.println("❌ You need at least 2 Pokémon to battle a rival."); return; }

        System.out.println("\nChoose your 2 Pokémon (they must be different):");
        player.displayCollection();
        int idx1 = Main.getValidIntInput(1, player.getCollectionSize()) - 1;
        int idx2;
        do {
            idx2 = Main.getValidIntInput(1, player.getCollectionSize()) - 1;
            if (idx2 == idx1) System.out.print("❎ You already picked that one. Choose a different Pokémon: ");
        } while (idx2 == idx1);

        Pokemon ally1 = player.getPokemon(idx1);
        Pokemon ally2 = player.getPokemon(idx2);

        int score = 0;
        if (duel(ally1, enemy1)) score += 50;
        if (duel(ally2, enemy2)) score += 50;
        if (!ally1.isFainted()) score += 50;
        if (!ally2.isFainted()) score += 50;
        score += 30; // Rival bonus

        System.out.println("🏆 Rival Battle Score: " + score);
        ScoreManager.saveScore(player.getName(), score);
    }

    private static boolean duel(Pokemon a, Pokemon b) {
        a.resetHP(); b.resetHP();
        while (!a.isFainted() && !b.isFainted()) {
            int dmg = a.attack(b);
            System.out.printf("%s ➜ %s (%d dmg)%n", a.getName(), b.getName(), dmg);
            if (b.isFainted()) break;
            dmg = b.attack(a);
            System.out.printf("%s ↩ %s (%d dmg)%n", b.getName(), a.getName(), dmg);
        }
        if (a.isFainted()) {
            System.out.println("💀 " + a.getName() + " fainted.");
            return false;
        } else {
            System.out.println("🎉 " + b.getName() + " fainted!");
            a.gainExp(30);

            if (a.isReadyToEvolve()) {
                System.out.println("🌟 " + a.getName() + " is ready to evolve!");
                System.out.println("✨ Evolve Pokémon to power up!");
            }

            return true;
        }

    }
}