import java.util.HashMap;
import java.util.Map;

public class TypeEffectiveness {

    // Complete type effectiveness chart
    private static final Map<String, Map<String, Double>> EFFECTIVENESS_CHART = new HashMap<>();

    static {
        // Initialize all type interactions
        Map<String, Double> normal = new HashMap<>();
        normal.put("Rock", 0.5);
        normal.put("Ghost", 0.0);
        normal.put("Steel", 0.5);

        Map<String, Double> fire = new HashMap<>();
        fire.put("Fire", 0.5);
        fire.put("Water", 0.5);
        fire.put("Grass", 2.0);
        fire.put("Ice", 2.0);
        fire.put("Bug", 2.0);
        fire.put("Rock", 0.5);
        fire.put("Dragon", 0.5);
        fire.put("Steel", 2.0);

        Map<String, Double> water = new HashMap<>();
        water.put("Fire", 2.0);
        water.put("Water", 0.5);
        water.put("Grass", 0.5);
        water.put("Ground", 2.0);
        water.put("Rock", 2.0);
        water.put("Dragon", 0.5);


        EFFECTIVENESS_CHART.put("Normal", normal);
        EFFECTIVENESS_CHART.put("Fire", fire);
        EFFECTIVENESS_CHART.put("Water", water);
      
    }


    public static double getEffectiveness(String attackType, String... defenderTypes) {
        double multiplier = 1.0;
        
        for (String defenderType : defenderTypes) {
            multiplier *= EFFECTIVENESS_CHART.getOrDefault(attackType, Map.of())
                            .getOrDefault(defenderType, 1.0);
        }
        
        return multiplier;
    }


    public static String getEffectivenessDescription(double multiplier) {
        if (multiplier == 0.0) return "No effect";
        if (multiplier > 1.0) return "Super effective";
        if (multiplier < 1.0) return "Not very effective";
        return "Normal effectiveness";
    }
}