import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ActionPanel extends JPanel {
    private final Terrain terrain;
    private final ArrayList<Warrior> troops;

    private BufferedImage blueArcherImage;
    private BufferedImage blueSwordsmanImage;
    private BufferedImage blueShieldmanImage;
    private BufferedImage redArcherImage;
    private BufferedImage redSwordsmanImage;
    private BufferedImage redShieldmanImage;

    public ActionPanel(Terrain terrain, ArrayList<Warrior> troops) {
        this.terrain = terrain;
        this.troops = troops;
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(700, 700));
        this.setVisible(true);
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        int pixelSize = 7;

        for (int x = 0; x < terrain.mapWidth; x++) {
            for (int y = 0; y < terrain.mapHeight; y++) {
                switch (terrain.map[x][y]) {
                    case LAND -> g2D.setPaint(new Color(28, 107, 1));
                    case WATER -> g2D.setPaint(Color.BLUE);
                    case GRAVEL -> g2D.setPaint(Color.GRAY);
                    case MOUNTAIN -> g2D.setPaint(Color.DARK_GRAY);
                }

                // Рисуем фон
                g2D.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
            }
        }
        // Рисуем воинов
        for (Warrior troop : troops) {
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

    public void startGame() {
        // TODO: Add configurable delay
        Timer timer = new Timer(100, this::onTimerTick);
        timer.start();
    }

    // TODO: use range from warrior
    private final int range = 5;

    private void checkOpponent(Warrior troop) {
        for (int i = Math.max(0, currentX - range); i < Math.min(mapLength, currentX + range); i++) {
            for (int j = Math.max(0, currentY - range); j < Math.min(mapLength, currentY + range); j++) {
                final Warrior troop = troops[i][j];
                final Warrior opponentTroop = troops[currentX][currentY];
                if (troop != null && opponentTroop != null) {
                    if (troop.team != opponentTroop.team) { // check if opposite teams
                        actionOpp(currentX, currentY, i, j);
                    }
                }
            }
        }

        advanceTroop(troop);
    }

    // TODO: Take into accound terrain
    private void advanceTroop(Warrior troop) {
        switch (troop.team) {
            case BLUE -> troop.x++;
            case RED -> troop.x--;
        }
    }

    private void actionOpp(int ii, int jj, int i, int j) {
        if (Point2D.distance(ii, jj, i, j) <= range - 3) {
            attack();
        }
    }

    private void attack() {
        System.out.println("dick attacked");
    }

    public void onTimerTick(ActionEvent e) {
        for (Warrior troop : troops) {
            checkOpponent(troop);
        }
        repaint();
    }
}
