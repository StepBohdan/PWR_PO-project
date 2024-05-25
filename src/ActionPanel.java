
import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    //add terrain generation
    Terrain terrain = new Terrain();
    int[][] generatedMap = terrain.getMap();

    public ActionPanel(){
        // how to implement game engine
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(700,700));
        this.setVisible(true);
        // to add tick rate
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        int size = 7;
        for(int i = 0 ; i < 100; i ++){
            for(int j = 0; j < 100; j ++){
                switch (generatedMap[i][j]){
                    case 0:
                        g2D.setPaint(Color.GREEN);
                        g2D.fillRect(i * size,j * size,size,size);
                        break;
                    case 1:
                        g2D.setPaint(Color.BLUE);
                        g2D.fillRect(i * size,j * size,size,size);
                        break;
                    case 2:
                        g2D.setPaint(Color.GRAY);
                        g2D.fillRect(i * size,j * size,size,size);
                        break;
                    case 3:
                        g2D.setPaint(Color.DARK_GRAY);
                        g2D.fillRect(i * size,j * size,size,size);
                        break;
                }
            }
        }
    }
    // movement methods and so on - repaint()
}
