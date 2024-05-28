
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ActionPanel extends JPanel {
    int[][] generatedMap;
    private BufferedImage blueArcherImage;
    private BufferedImage blueSwordsmanImage;
    private BufferedImage blueShieldmanImage;
    private BufferedImage redArcherImage;
    private BufferedImage redSwordsmanImage;
    private BufferedImage redShieldmanImage;

    public ActionPanel(int[][] generatedMap) {
        this.generatedMap = generatedMap;
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
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                switch (generatedMap[i][j]) {
                    case 0:
                        g2D.setPaint(new Color(28, 107, 1));
                        g2D.fillRect(i * size, j * size, size, size);
                        break;
                    case 1:
                        g2D.setPaint(Color.BLUE);
                        g2D.fillRect(i * size, j * size, size, size);
                        break;
                    case 2:
                        g2D.setPaint(Color.GRAY);
                        g2D.fillRect(i * size, j * size, size, size);
                        break;
                    case 3:
                        g2D.setPaint(Color.DARK_GRAY);
                        g2D.fillRect(i * size, j * size, size, size);
                        break;
                    case 4:
                        g2D.drawImage(blueSwordsmanImage, i * size, j * size, size, size, null);
                        break;
                    case 5:
                        g2D.drawImage(blueArcherImage, i * size, j * size, size, size, null);
                        break;
                    case 6:
                        g2D.drawImage(blueShieldmanImage, i * size, j * size, size, size, null);
                        break;
                    case 7:
                        g2D.drawImage(redArcherImage, i * size, j * size, size, size, null);
                        break;
                    case 8:
                        g2D.drawImage(redSwordsmanImage, i * size, j * size, size, size, null);
                        break;
                    case 9:
                        g2D.drawImage(redShieldmanImage, i * size, j * size, size, size, null);
                        break;
                }
            }
        }
    }

    // movement methods and so on - repaint()

}
