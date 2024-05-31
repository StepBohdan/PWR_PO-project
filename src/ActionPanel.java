
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
    int[][] generatedMap;
    int[][] originGenMap;
    private BufferedImage blueArcherImage;
    private BufferedImage blueSwordsmanImage;
    private BufferedImage blueShieldmanImage;
    private BufferedImage redArcherImage;
    private BufferedImage redSwordsmanImage;
    private BufferedImage redShieldmanImage;
    private final int mapLength;
    ArrayList<ArrayList<Integer>> warriorLocation = new ArrayList<>();
    Timer timer;

    public ActionPanel(int[][] generatedMap) {
        this.generatedMap = generatedMap;
        originGenMap = generatedMap.clone();
        this.mapLength = generatedMap.length;
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(700, 700));
        this.setVisible(true);
        loadImages();
        create_war_loc();
        start_game(); // add if start button triggered statement
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


//    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        System.out.println("dick");
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
                if (i * size >= grayX && i * size < grayX + grayWidth &&
                        j * size >= grayY && j * size < grayHeight) {
                    // Если точка в серой области, рисуем её серым цветом
                    g2D.setPaint(Color.GRAY);
                } else {
                    // Иначе, рисуем её соответствующим цветом из generatedMap
                    switch (originGenMap[i][j]) {
                        case 0:
                            g2D.setPaint(new Color(28, 107, 1));
                            break;
                        case 1:
                            g2D.setPaint(Color.BLUE);
                            break;
                        case 2:
                            g2D.setPaint(Color.GRAY);
                            break;
                        case 3:
                            g2D.setPaint(Color.DARK_GRAY);
                            break;
                        default:
                            g2D.setPaint(new Color(28, 107, 1));
                            break;
                    }
                }

                // Рисуем фон
                g2D.fillRect(i * size, j * size, size, size);

                // Рисуем изображение, если необходимо
                switch (generatedMap[i][j]) {
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

    public void create_war_loc() {
//      generates warriorLocation array
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i = 0;i < mapLength; i++){
            for(int j = 0;j < mapLength; j++){ // better method it to have a list of cords of warriors
                if(generatedMap[i][j] > 3){ // so you dont check for them every time
                    temp.add(i);
                    temp.add(j);
                    warriorLocation.add((ArrayList<Integer>) temp.clone());
                    temp.clear();
                }
            }
        }
        for(Object e:warriorLocation){
            System.out.println(e.toString());
        }
        System.out.println("dick"); //test
    }

    private void start_game() {
        for(ArrayList<Integer> e:warriorLocation){
            check_opponent(e.getFirst(), e.getLast());
        }
        timer = new Timer(1000, this);
        timer.start();
    }
    private int range = 5;

    private void check_opponent(Integer ii, Integer jj) {
        for(int i = Math.max(0, ii - range); i < Math.min(mapLength, ii + range); i++){
            for(int j = Math.max(0, jj - range); j < Math.min(mapLength, jj + range); j++) {
                if(generatedMap[i][j] > 3){
                    if(getUnitSide(ii,jj) != getUnitSide(i,j)){ // check if opposite teams
                        actionOpp(ii,jj,i,j);
                    } else {
                        moveCenter(ii, jj);
                    }
                }
            }
        }
    }
    private int getUnitSide(int i, int j){
        if(generatedMap[i][j] > 3 && generatedMap[i][j] < 7){
            return 0;
        } else if(generatedMap[i][j] > 6 && generatedMap[i][j] > 10){
            return 1;
        } else{
            System.err.println("Not unit checked"); // should not invoke
        }
        System.err.println("Not unit checked"); // should not invoke
        return -1;
    }

    private void moveCenter(int i, int j) {
        if(generatedMap[i][j] > 3 && generatedMap[i][j] < 7){
            generatedMap[i + 1][j] = generatedMap[i][j];
        } else {
            generatedMap[i - 1][j] = generatedMap[i][j];
        }
        generatedMap[i][j] = originGenMap[i][j];
    }

    private void actionOpp(int ii, int jj, int i, int j){
        if(Point2D.distance(ii,jj,i,j) <= range){
            attack();
        } else {
            if(ii < i){
                generatedMap[ii + 1][jj] = generatedMap[ii][jj];
                generatedMap[ii][jj] = originGenMap[ii][jj];
            } else if (ii > i){
                generatedMap[ii - 1][jj] = generatedMap[ii][jj];
                generatedMap[ii][jj] = originGenMap[ii][jj];
            }
            if(jj < j){
                generatedMap[ii][jj + 1] = generatedMap[ii][jj];
                generatedMap[ii][jj] = originGenMap[ii][jj];
            } else if (jj > j){
                generatedMap[ii][jj - 1] = generatedMap[ii][jj];
                generatedMap[ii][jj] = originGenMap[ii][jj];
            }
        }
    }

    private void attack() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
