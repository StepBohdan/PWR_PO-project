import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ActionPanel extends JPanel implements ActionListener {
    private final Terrain.TerrainType[][] generatedMap;
    private final ArrayList<Warrior> troops;
    private BufferedImage blueArcherImage;
    private BufferedImage blueSwordsmanImage;
    private BufferedImage blueShieldmanImage;
    private BufferedImage redArcherImage;
    private BufferedImage redSwordsmanImage;
    private BufferedImage redShieldmanImage;
    private final int mapLength;

    public ActionPanel(Terrain.TerrainType[][] generatedMap, ArrayList<Warrior> troops) {
        this.generatedMap = generatedMap;
        this.troops = troops;
        this.mapLength = generatedMap.length;
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
        int size = 7;
        int mapWidth = 100;
        int mapHeight = 100;

        // Определяем размер и положение серой области
        int grayWidth = 30 * size;
        int grayHeight = 30 * size;
        int grayX = (mapWidth * size - grayWidth) / 2;
        int grayY = (mapHeight * size - grayHeight) / 2;

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                // Проверяем, находится ли точка в серой области
                if (i * size >= grayX && i * size < grayX + grayWidth && j * size >= grayY && j * size < grayHeight) {
                    // Если точка в серой области, рисуем её серым цветом
                    g2D.setPaint(Color.GRAY);
                } else {
                    // Иначе, рисуем её соответствующим цветом из generatedMap
                    switch (generatedMap[i][j]) {
                        case Terrain.TerrainType.LAND:
                            g2D.setPaint(new Color(28, 107, 1));
                            break;
                        case Terrain.TerrainType.WATER:
                            g2D.setPaint(Color.BLUE);
                            break;
                        case Terrain.TerrainType.GRAVEL:
                            g2D.setPaint(Color.GRAY);
                            break;
                        case Terrain.TerrainType.MOUNTAIN:
                            g2D.setPaint(Color.DARK_GRAY);
                            break;
                    }
                }

                // Рисуем фон
                g2D.fillRect(i * size, j * size, size, size);
            }
        }
        // Рисуем воинов
        for (Warrior troop : troops) {
            switch (troop.team) {
                case RED -> {
                    switch (troop) {
                        case Archer _ ->
                                g2D.drawImage(redArcherImage, troop.x * size, troop.y * size, size, size, null);
                        case Swordsman _ ->
                                g2D.drawImage(redSwordsmanImage, troop.x * size, troop.y * size, size, size, null);
                        case Shieldman _ ->
                                g2D.drawImage(redShieldmanImage, troop.x * size, troop.y * size, size, size, null);
                        default -> {
                        }
                    }
                }
                case BLUE -> {
                    switch (troop) {
                        case Archer _ ->
                                g2D.drawImage(blueArcherImage, troop.x * size, troop.y * size, size, size, null);
                        case Swordsman _ ->
                                g2D.drawImage(blueSwordsmanImage, troop.x * size, troop.y * size, size, size, null);
                        case Shieldman _ ->
                                g2D.drawImage(blueShieldmanImage, troop.x * size, troop.y * size, size, size, null);
                        default -> {
                        }
                    }
                }
            }
        }
    }

    public void startGame() {
        // TODO: Add configurable delay
        Timer timer = new Timer(100, this);
        timer.start();
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Warrior troop : troops) {
            checkOpponent(troop);
        }
        repaint();
    }
}
