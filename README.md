# 🎮 PRG1203 - Pokémon Ga-Ole Game (Group 27)

A Java-based arcade battle simulation game inspired by the popular **Pokémon Ga-Ole** series. Developed as a group coursework project for module **PRG1203**.

---

## 📌 Features

* **Battle Systems**: Engage in both **Wild Pokémon Battles** and **Rival Battles**.
* **Player Management**: Save, load, and manage player state and progression via persistent text files.
* **Score & Leaderboards**: Track high scores and monitor trainer ranking across battle sessions.
* **Evolution System**: Dynamically evolve Pokémon as battle requirements and info criteria are met.
* **Type Effectiveness**: Full implementation of Pokémon elemental type advantages and disadvantages.
* **Pokéball Mechanics**: Catch wild Pokémon using standard Pokéballs with rate mechanics.
* **ASCII / Visual Art**: Visual representations and details rendered in console output.

---

## 📁 Project Structure

```text
PRG1203_Pokemon_Ga-Ole_Game_Group_27/
└── Group_27/
    ├── src/                                  # Java source files
    │   ├── Main.java                         # Game entry point
    │   ├── Pokemon.java                      # Pokémon class & stats
    │   ├── Player.java                       # Player entity & stats
    │   ├── WildBattle.java                   # Wild Pokémon battle logic
    │   ├── RivalBattle.java                  # Rival battle logic
    │   ├── Pokeball.java                     # Pokéball capture mechanics
    │   ├── EvolutionData.java                # Evolution info & conditions
    │   ├── TypeEffectiveness.java            # Type advantage matrix
    │   ├── ScoreManager.java                 # High score tracking
    │   ├── UserManager.java                  # User profile management
    │   ├── PlayerStorageManager.java         # Data persistence & loading
    │   ├── TrainerStatusService.java         # Trainer status management
    │   ├── ViewPokemonDetails.java           # Detailed stats viewer
    │   ├── PokemonArt.java                   # ASCII art / visual elements
    │   └── DataLoader.java                   # External data loading utility
    │
    ├── bin/                                  # Compiled .class files
    ├── players_data.txt                      # Saved player profiles & data
    ├── top_scores.txt                        # Leaderboard records
    └── trainer_status.txt                    # Saved trainer progress
