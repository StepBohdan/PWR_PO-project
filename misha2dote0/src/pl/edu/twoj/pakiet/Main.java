package pl.edu.twoj.pakiet;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import static java.lang.StringTemplate.STR;


class GameResultRecorder {
    private Map<Warrior.Team, Map<Class<? extends Warrior>, Integer>> startingTroops;
    private Map<Warrior.Team, Map<Class<? extends Warrior>, Integer>> deadTroops;

    public GameResultRecorder() {
        startingTroops = new HashMap<>();
        deadTroops = new HashMap<>();
    }

    public void writeStartingTroops(final ArrayList<Warrior> troops) {
        startingTroops.put(Warrior.Team.BLUE, new HashMap<>());
        startingTroops.put(Warrior.Team.RED, new HashMap<>());
        deadTroops.put(Warrior.Team.BLUE, new HashMap<>());
        deadTroops.put(Warrior.Team.RED, new HashMap<>());

        // Count each type of warrior at the start
        for (Warrior troop : troops) {
            Warrior.Team team = troop.team;
            Class<? extends Warrior> troopType = troop.getClass();

            int currentCount = startingTroops.get(team).getOrDefault(troopType, 0);
            startingTroops.get(team).put(troopType, currentCount + 1);
        }
    }

    public void warriorDied(Warrior warrior) {
        Warrior.Team team = warrior.team;
        Class<? extends Warrior> troopType = warrior.getClass();

        int currentDeathCount = deadTroops.get(team).getOrDefault(troopType, 0);
        deadTroops.get(team).put(troopType, currentDeathCount + 1);
    }

    public void announceWinnerAndSaveResults(final String message) {
        String winningTeam = "";
        if (message.contains("BLUE")) {
            winningTeam = "BLUE";
        } else if (message.contains("RED")) {
            winningTeam = "RED";
        }

        writeResultsToFile(winningTeam);
    }

    private void writeResultsToFile(String winningTeam) {
        try (FileWriter writer = new FileWriter("game_results.txt")) {
            writer.write("Game Results:\n\n");
            writer.write("Winning Team: " + winningTeam + "\n\n");

            for (Warrior.Team team : Warrior.Team.values()) {
                writer.write("Team " + team + ":\n");

                writer.write("  Starting Troops:\n");
                for (Class<? extends Warrior> troopType : startingTroops.get(team).keySet()) {
                    writer.write(STR."    \{troopType.getSimpleName()}: \{startingTroops.get(team).get(troopType)}\n");
                }

                writer.write("  Dead Troops:\n");
                for (Class<? extends Warrior> troopType : deadTroops.get(team).keySet()) {
                    writer.write(STR."    \{troopType.getSimpleName()}: \{deadTroops.get(team).get(troopType)}\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving game results!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


class Swordsman extends Warrior {
    public Swordsman(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(70, 50, 7, 2, team, x, y);
    }
}

class Archer extends Warrior {
    public Archer(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(50, 20, 10, 5, team, x, y);
    }
}

class Shieldman extends Warrior {
    public Shieldman(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(30, 80, 5, 1, team, x, y);
    }
}


