import java.io.Serializable;
import java.util.Scanner;

public class Pokemon implements Serializable {
    private String name, type, moveType;
    private int maxHP, currentHP, power, level, exp;

    public Pokemon(String name, String type, String moveType, int maxHP, int power) {
        this.name = name;
        this.type = type;
        this.moveType = moveType;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.power = power;
        this.level = 1;
        this.exp = 0;
        
    }

    public void takeDamage(int amount) {
        currentHP = Math.max(0, currentHP - amount);
    }

    public boolean isFainted() {
        return currentHP == 0;
    }

    public void resetHP() {
        currentHP = maxHP;
    }

    public int attack(Pokemon opponent) {
        double multiplier = TypeEffectiveness.getEffectiveness(this.moveType, opponent.type);
        int damage = (int) ((this.power + this.level * 2) * multiplier);
        opponent.takeDamage(damage);
        return damage;
    }

    public void gainExp(int amount) {
        if (amount <= 0) return;
        exp += amount;
        while (exp >= 50) {
            exp -= 50;
            level++;
            maxHP += 10;
            power += 2;
            System.out.println("⬆️ " + name + " leveled up to Level " + level + "!");
        }
    }
    
    

    public void gainExpSilent(int amount) {
        if (amount <= 0) return;
        exp += amount;
        while (exp >= 50) {
            exp -= 50;
            level++;
            maxHP += 10;
            power += 2;
        }
    }

    public boolean isReadyToEvolve() {
        return EvolutionData.canEvolve(name, level);
    }

    public EvolutionData.EvolutionInfo getEvolutionInfo() {
        return EvolutionData.getEvolutionInfo(name);
    }

    // Called ONLY when player agrees
    public void evolve() {
        EvolutionData.EvolutionInfo info = getEvolutionInfo();
        if (info == null) return;

        System.out.println("✨ " + name + " is evolving into " + info.evolvedName + "!");
        this.name = info.evolvedName;
        this.type = info.newType;
        this.moveType = info.newMoveType;
        this.maxHP += 20;
        this.power += 5;
        System.out.println("🎉 Evolution complete! New type: " + type + ", Move type: " + moveType);
    }
    
    public void checkForEvolution(Scanner sc) {
        if (isReadyToEvolve()) {
            EvolutionData.EvolutionInfo info = getEvolutionInfo();
            System.out.println("\n✨ " + name + " is ready to evolve into " + info.evolvedName + "!");
            System.out.print("Would you like to evolve? (yes/no): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                evolve();
            } else {
                System.out.println("❌ " + name + " stays the same for now.");
            }
        }
    }
    

    public String getStatus() {
        EvolutionData.EvolutionInfo evo = getEvolutionInfo();
        String evoNote = "";

        if (evo != null) {
            if (level >= evo.levelRequired) {
                evoNote = " 🌟 Ready to evolve!";
            } else {
                evoNote = " 🔒 Can evolve at Lv " + evo.levelRequired;
            }
        } else {
            evoNote = " 🚫 Does not evolve";
        }

        return String.format("🐾 %s [Lv %d] ❤️ (%d/%d HP) 🔥 Type: %s / Move: %s%s",
                name, level, currentHP, maxHP, type, moveType, evoNote);
    }


    public String getName()     { return name; }
    public String getType()     { return type; }
    public String getMoveType() { return moveType; }
    public int getCurrentHP()   { return currentHP; }
    public int getMaxHP()       { return maxHP; }
    public int getPower()       { return power; }
    public int getLevel()       { return level; }
    public int getExp()         { return exp; }

    public void setCurrentHP(int hp) { this.currentHP = Math.max(0, Math.min(hp, maxHP)); }
    public void setLevel(int lvl)    { this.level = lvl; }
}

