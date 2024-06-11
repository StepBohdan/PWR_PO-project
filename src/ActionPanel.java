import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.ErrorManager;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Handles the user interface for the game actions. This includes buttons and other interactive elements that allow the player to control their warriors and perform various in-game actions.
 */
public class ActionPanel extends JPanel {
    private static final int pixelSize = 10;
    private static final int TEAM_RED = 0;
    private static final int TEAM_BLUE = 1;
    private static final int NUM_TEAMS = 2;
    private final Terrain terrain;
    private final ArrayList<Warrior> troops;
    private final Random random;
    private final Function<String, Void> onGameEnd;
    // Variables to store the initial number of warriors and losses for each team and warrior type
    private final int[] initialArchers = new int[NUM_TEAMS];
    private final int[] initialSwordsmen = new int[NUM_TEAMS];
    private final int[] initialShieldmen = new int[NUM_TEAMS];

    private final int[] archerLosses = new int[NUM_TEAMS];
    private final int[] swordsmanLosses = new int[NUM_TEAMS];
    private final int[] shieldmanLosses = new int[NUM_TEAMS];
    private BufferedImage blueArcherImage;
    private BufferedImage blueSwordsmanImage;
    private BufferedImage blueShieldmanImage;
    private BufferedImage redArcherImage;
    private BufferedImage redSwordsmanImage;
    private BufferedImage redShieldmanImage;

    private Timer timer;

    /**
     * Constructor for ActionPanel.
     * Initializes the panel with necessary components and layout.
     */
    public ActionPanel(final Terrain terrain, final ArrayList<Warrior> troops, final Random random, final Function<String, Void> onGameEnd) {
        this.terrain = terrain;
        this.troops = troops;
        this.random = random;
        this.onGameEnd = onGameEnd;
        this.setFocusable(false);
        loadImages();
        for (Warrior warrior : troops) {
            int teamIndex = getTeamIndex(warrior.team);
            switch (warrior) {
                case Archer _ -> initialArchers[teamIndex]++;
                case Swordsman _ -> initialSwordsmen[teamIndex]++;
                case Shieldman _ -> initialShieldmen[teamIndex]++;
                default -> {
                }
            }
        }
    }

    /**
     * Get the index of a team
     *
     * @param team The team whose index is required
     * @return The index of the team
     */
    private int getTeamIndex(Warrior.Team team) {
        return switch (team) {
            case RED -> TEAM_RED;
            case BLUE -> TEAM_BLUE;
        };
    }

    /**
     * Method to write match results to a file
     *
     * @param message The match result message to write
     */
    private void writeMatchResultsToFile(final String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("match_results.txt"))) {
            writer.write("Results of the battle\n");
            writer.write("----------------\n");
            writer.write(String.format("Winner is: %s\n\n", message));

            for (Warrior.Team team : Warrior.Team.values()) {
                int teamIndex = getTeamIndex(team);
                writer.write(String.format("  - Archers: Initial Archers count: %d, Casualties: %d, Left: %d\n",
                        initialArchers[teamIndex], archerLosses[teamIndex], initialArchers[teamIndex] - archerLosses[teamIndex]));
                writer.write(String.format("  - Swordsmen: Initial Swordsmen count: %d, Casualties: %d, Left: %d\n",
                        initialSwordsmen[teamIndex], swordsmanLosses[teamIndex], initialSwordsmen[teamIndex] - swordsmanLosses[teamIndex]));
                writer.write(String.format("  - Shieldmen: Initial Shieldmen count: %d, Casualties: %d, Left: %d\n\n",
                        initialShieldmen[teamIndex], shieldmanLosses[teamIndex], initialShieldmen[teamIndex] - shieldmanLosses[teamIndex]));
            }
        } catch (IOException e) {
            System.err.printf("Error while writing results to the file: %s\n", e.getMessage());
        }
    }

    /**
     * Loads images for the action panel.
     */
    private void loadImages() {
        try {
            blueArcherImage = ImageIO.read(new File("src/images/blue-archer.png"));
            blueSwordsmanImage = ImageIO.read(new File("src/images/blue-swordsman.png"));
            blueShieldmanImage = ImageIO.read(new File("src/images/blue-shieldman.png"));
            redArcherImage = ImageIO.read(new File("src/images/red-archer.png"));
            redSwordsmanImage = ImageIO.read(new File("src/images/red-swordsman.png"));
            redShieldmanImage = ImageIO.read(new File("src/images/red-shieldman.png"));
        } catch (IOException e) {
            ErrorManager log = new ErrorManager();
            log.error("Image reading failed", e, 0);
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draw the graphics on the panel.
     *
     * @param g The graphics object to draw on.
     */
    private void draw(final Graphics g) {
        Graphics2D g2D = getGraphics2D((Graphics2D) g);
        for (final Warrior troop : troops) {
            final int troopImageX = troop.x * pixelSize;
            final int troopImageY = troop.y * pixelSize;
            switch (troop.team) {
                case RED -> {
                    switch (troop) {
                        case Archer _ ->
                                g2D.drawImage(redArcherImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        case Swordsman _ ->
                                g2D.drawImage(redSwordsmanImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        case Shieldman _ ->
                                g2D.drawImage(redShieldmanImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        default -> {
                        }
                    }
                }
                case BLUE -> {
                    switch (troop) {
                        case Archer _ ->
                                g2D.drawImage(blueArcherImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        case Swordsman _ ->
                                g2D.drawImage(blueSwordsmanImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        case Shieldman _ ->
                                g2D.drawImage(blueShieldmanImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        default -> {
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the Graphics2D object for drawing.
     *
     * @param g The graphics object to convert.
     * @return The converted Graphics2D object.
     */
    private Graphics2D getGraphics2D(Graphics2D g) {

        for (int x = 0; x < terrain.mapWidth; x++) {
            for (int y = 0; y < terrain.mapHeight; y++) {
                switch (terrain.map[x][y]) {
                    case LAND -> g.setPaint(new Color(28, 107, 1));
                    case WATER -> g.setPaint(Color.BLUE);
                    case GRAVEL -> g.setPaint(Color.GRAY);
                    case MOUNTAIN -> g.setPaint(Color.DARK_GRAY);
                }
                g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
            }
        }
        return g;
    }

    /**
     * Start the game by initializing and starting the timer.
     */
    public void startGame() {
        timer = new Timer(100, this::onTimerTick);
        timer.start();
    }

    /**
     * Stop the game by stopping the timer.
     */
    public void stopGame() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            timer = null;
        }
    }

    /**
     * Check the opponent status for a given troop.
     *
     * @param troop The troop to check.
     */
    private void checkOpponent(final Warrior troop) {
        final ArrayList<Warrior> enemies = getAllEnemyTroops(troop);
        if (enemies.isEmpty()) {
            onGameEnd.apply(String.format("%s won by killing all enemies", troop.team.toString()));
            stopGame();
        }

        Warrior warriorToAttack = null;
        Warrior warriorToNavigateTo = null;
        for (final Warrior enemy : enemies) {
            if (troop.canAttack(enemy)) {
                warriorToAttack = enemy;
                break;
            }
        }
        if (warriorToAttack == null) {
            for (final Warrior enemy : enemies) {
                if (troop.canSee(enemy)) {
                    warriorToNavigateTo = enemy;
                    break;
                }
            }
        }

        if (warriorToAttack != null) {
            final int attackPenalty = terrain.getAttackPenalty(troop.x, troop.y);
            final int defensePenalty = terrain.getDefensePenalty(warriorToAttack.x, warriorToAttack.y);
            final boolean attackResult = troop.attack(warriorToAttack, random, attackPenalty, defensePenalty);
            if (attackResult) {
                System.out.printf("%s %s killed a %s %s%n\n", troop.team.toString(), troop.getClass().getCanonicalName(), warriorToAttack.team.toString(), warriorToAttack.getClass().getCanonicalName());
                troops.remove(warriorToAttack);
                int teamIndex = getTeamIndex(warriorToAttack.team);
                switch (warriorToAttack) {
                    case Archer _ -> archerLosses[teamIndex]++;
                    case Swordsman _ -> swordsmanLosses[teamIndex]++;
                    case Shieldman _ -> shieldmanLosses[teamIndex]++;
                    default -> {
                    }
                }
            } else {
                System.out.printf("%s %s defended the attack of %s %s%n\n", warriorToAttack.team.toString(), warriorToAttack.getClass().getCanonicalName(), troop.team.toString(), troop.getClass().getCanonicalName());

            }
        } else if (warriorToNavigateTo != null) {
            moveTowardsAnEnemy(troop, warriorToNavigateTo);
        } else {
            advanceTroop(troop);
        }
    }

    /**
     * Get all enemy troops for a given troop.
     *
     * @param troop The troop to check for enemies.
     * @return A list of enemy troops.
     */
    private ArrayList<Warrior> getAllEnemyTroops(final Warrior troop) {
        final ArrayList<Warrior> enemyTroops = new ArrayList<>();
        for (final Warrior warrior : troops) {
            if (warrior.team != troop.team) {
                enemyTroops.add(warrior);
            }
        }
        return enemyTroops;
    }

    /**
     * Move a troop towards an enemy.
     *
     * @param troop The troop to move.
     * @param enemy The enemy to move towards.
     */
    private void moveTowardsAnEnemy(final Warrior troop, final Warrior enemy) {
        if (troop.attackRadius <= Math.abs(troop.y - enemy.y)) {
            if (troop.y > enemy.y) {
                if (canGoDown(troop)) {
                    troop.moveDown();
                    return;
                }
            } else {
                if (canGoUp(troop)) {
                    troop.moveUp();
                    return;
                }
            }
        }
        advanceTroop(troop);
    }

    /**
     * Advance a troop forward.
     *
     * @param troop The troop to advance.
     */
    private void advanceTroop(final Warrior troop) {
        if (troop.direction != Warrior.Direction.STUCK) {
            if (canGoForward(troop)) {
                troop.direction = Warrior.Direction.FORWARD;
                troop.moveForward();
            } else {
                moveVertically(troop);
            }
        }
    }

    /**
     * Move a troop vertically.
     *
     * @param troop The troop to move.
     */
    private void moveVertically(final Warrior troop) {
        if (troop.direction == Warrior.Direction.FORWARD) {
            final boolean isUp = random.nextBoolean();
            troop.direction = isUp ? Warrior.Direction.UP : Warrior.Direction.DOWN;
        }

        switch (troop.direction) {
            case UP -> {
                if (canGoUp(troop)) {
                    troop.moveUp();
                } else if (!canGoUp(troop) && canGoDown(troop)) {
                    troop.direction = Warrior.Direction.DOWN;
                    troop.moveDown();
                } else {
                    troop.direction = Warrior.Direction.STUCK;
                    System.out.printf("%s %s got stuck\n", troop.team.toString(), troop.getClass().getCanonicalName());
                }
            }
            case DOWN -> {
                if (canGoDown(troop)) {
                    troop.moveDown();
                } else if (!canGoDown(troop) && canGoUp(troop)) {
                    troop.direction = Warrior.Direction.UP;
                    troop.moveUp();
                } else {
                    troop.direction = Warrior.Direction.STUCK;
                }
            }
        }
    }

    /**
     * Check if a troop can move up.
     *
     * @param troop The troop to check.
     * @return True if the troop can move up, false otherwise.
     */
    private boolean canGoUp(final Warrior troop) {
        final int troopX = troop.x;
        final int newTroopY = troop.y + 1;
        return terrain.isInMapBounds(troopX, newTroopY) && !terrain.isMountain(troopX, newTroopY);
    }

    /**
     * Check if a troop can move down.
     *
     * @param troop The troop to check.
     * @return True if the troop can move down, false otherwise.
     */
    private boolean canGoDown(final Warrior troop) {
        final int troopX = troop.x;
        final int newTroopY = troop.y - 1;
        return terrain.isInMapBounds(troopX, newTroopY) && !terrain.isMountain(troopX, newTroopY);
    }

    /**
     * Check if a troop can move forward.
     *
     * @param troop The troop to check.
     * @return True if the troop can move forward, false otherwise.
     */
    private boolean canGoForward(final Warrior troop) {
        final int troopX = troop.x;
        int newTroopX = troopX;
        switch (troop.team) {
            case BLUE -> newTroopX = troopX + 1;
            case RED -> newTroopX = troopX - 1;
        }
        final int troopY = troop.y;
        return terrain.isInMapBounds(newTroopX, troopY) && !terrain.isMountain(newTroopX, troopY);
    }

    /**
     * Check if a troop is at the end of the map.
     *
     * @param troop The troop to check.
     * @return True if the troop is at the end of the map, false otherwise.
     */
    private boolean isAtMapEnd(final Warrior troop) {
        switch (troop.team) {
            case BLUE -> {
                return troop.x == terrain.mapWidth - 1;
            }
            case RED -> {
                return troop.x == 0;
            }
        }
        return false;
    }

    /**
     * Check if it is a draw due to all troops being stuck.
     *
     * @param localTroops The list of local troops to check.
     * @return True if all troops are stuck, false otherwise.
     */
    private boolean isDrawOnStuck(ArrayList<Warrior> localTroops) {
        for (final Warrior troop : localTroops) {
            if (troop.direction != Warrior.Direction.STUCK) {
                return false;
            }
        }
        return true;
    }

    /**
     * Handle the timer tick event.
     *
     * @param e The action event triggered by the timer.
     */
    public void onTimerTick(final ActionEvent e) {
        boolean blueReachedMapEnd = false;
        boolean redReachedMapEnd = false;
        final ArrayList<Warrior> localTroops = new ArrayList<>(troops);
        if (localTroops.isEmpty()) {
            onGameEnd.apply("It's a draw. Everybody died");
            stopGame();
        } else if (isDrawOnStuck(localTroops)) {
            onGameEnd.apply("It's a draw. Everybody is stuck");
            startGame();
        }
        for (final Warrior troop : localTroops) {
            checkOpponent(troop);
            switch (troop.team) {
                case RED -> redReachedMapEnd = isAtMapEnd(troop);
                case BLUE -> blueReachedMapEnd = isAtMapEnd(troop);
            }
        }

        if (blueReachedMapEnd && redReachedMapEnd) {
            onGameEnd.apply("It's a draw. Both teams reached the end at the same time");
            stopGame();
            writeMatchResultsToFile("Draw");
        } else if (blueReachedMapEnd) {
            onGameEnd.apply("BLUE won by reaching the end first");
            stopGame();
            writeMatchResultsToFile("Blue");
        } else if (redReachedMapEnd) {
            onGameEnd.apply("RED won by reaching the end first");
            stopGame();
            writeMatchResultsToFile("Red");
        }

        repaint();
    }
}
