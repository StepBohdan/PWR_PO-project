package pl.edu.twoj.pakiet;

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

public class ActionPanel extends JPanel {
    private static final int pixelSize = 10;
    private final GameResultRecorder resultRecorder;
    private final Terrain terrain;
    private final ArrayList<Warrior> troops;
    private final Random random;
    private final Function<String, Void> onGameEnd;

    private BufferedImage blueArcherImage;
    private BufferedImage blueSwordsmanImage;
    private BufferedImage blueShieldmanImage;
    private BufferedImage redArcherImage;
    private BufferedImage redSwordsmanImage;
    private BufferedImage redShieldmanImage;

    private Timer timer;

    public ActionPanel(final Terrain terrain, final ArrayList<Warrior> troops, final Random random, final Function<String, Void> onGameEnd, GameResultRecorder resultRecorder) {
        this.terrain = terrain;
        this.troops = troops;
        this.random = random;
        this.onGameEnd = onGameEnd;
        this.resultRecorder = resultRecorder;
        this.resultRecorder.writeStartingTroops(troops);
        this.setFocusable(false);
        loadImages();
    }

    private void loadImages() {
        try {
            blueArcherImage = ImageIO.read(new File("src/images/blue-archer.png"));
            blueSwordsmanImage = ImageIO.read(new File("src/images/blue-swordsman.png"));
            blueShieldmanImage = ImageIO.read(new File("src/images/blue-shieldman.png"));
            redArcherImage = ImageIO.read(new File("src/images/red-archer.png"));
            redSwordsmanImage = ImageIO.read(new File("src/images/red-swordsman.png"));
            redShieldmanImage = ImageIO.read(new File("src/images/red-shieldman.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(final Graphics g) {
        Graphics2D g2D = getGraphics2D((Graphics2D) g);

        for (final Warrior troop : troops) {
            final int troopImageX = troop.x * pixelSize;
            final int troopImageY = troop.y * pixelSize;
            switch (troop.team) {
                case RED -> {
                    switch (troop) {
                        case Archer unusedVariable ->
                                g2D.drawImage(redArcherImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        case Swordsman unusedVariable ->
                                g2D.drawImage(redSwordsmanImage, troopImageX, troopImageY, pixelSize, pixelSize, null);
                        case Shieldman unusedVariable ->
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

    public void startGame() {
        // TODO: Add configurable delay
        timer = new Timer(100, this::onTimerTick);
        timer.start();
    }

    public void stopGame() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            timer = null;
        }
    }

    private void checkOpponent(final Warrior troop) {
        final ArrayList<Warrior> enemies = getAllEnemyTroops(troop);
        if (enemies.isEmpty()) {
            onGameEnd.apply(STR."\{troop.team.toString()} won by killing all enemies");
            stopGame();
        }

        // TODO: consider randomizing warrior to attack
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
                troops.remove(warriorToAttack);
                resultRecorder.warriorDied(warriorToAttack);
                System.out.println(STR."\{troop.team.toString()} \{troop.getClass().getCanonicalName()} killed a \{warriorToAttack.team.toString()} \{warriorToAttack.getClass().getCanonicalName()}");
                troops.remove(warriorToAttack);
            } else {
                System.out.println(STR."\{warriorToAttack.team.toString()} \{warriorToAttack.getClass().getCanonicalName()} defended the attack of \{troop.team.toString()} \{troop.getClass().getCanonicalName()}");
            }
        } else if (warriorToNavigateTo != null) {
            moveTowardsAnEnemy(troop, warriorToNavigateTo);
        } else {
            advanceTroop(troop);
        }
    }

    private ArrayList<Warrior> getAllEnemyTroops(final Warrior troop) {
        final ArrayList<Warrior> enemyTroops = new ArrayList<>();
        for (final Warrior warrior : troops) {
            if (warrior.team != troop.team) {
                enemyTroops.add(warrior);
            }
        }
        return enemyTroops;
    }

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
                    System.out.println(STR."\{troop.team.toString()} \{troop.getClass().getCanonicalName()} got stuck");
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

    private boolean canGoUp(final Warrior troop) {
        final int troopX = troop.x;
        final int newTroopY = troop.y + 1;
        return terrain.isInMapBounds(troopX, newTroopY) && !terrain.isMountain(troopX, newTroopY);
    }

    private boolean canGoDown(final Warrior troop) {
        final int troopX = troop.x;
        final int newTroopY = troop.y - 1;
        return terrain.isInMapBounds(troopX, newTroopY) && !terrain.isMountain(troopX, newTroopY);
    }

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

    public void onTimerTick(final ActionEvent e) {
        boolean blueReachedMapEnd = false;
        boolean redReachedMapEnd = false;
        final ArrayList<Warrior> localTroops = new ArrayList<>(troops);
        if (localTroops.isEmpty()) {
            onGameEnd.apply("It's a draw. Everybody died");
            stopGame();
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
        } else if (blueReachedMapEnd) {
            onGameEnd.apply("BLUE won by reaching the end first");
            stopGame();
        } else if (redReachedMapEnd) {
            onGameEnd.apply("RED won by reaching the end first");
            stopGame();
        }

        repaint();
    }
}