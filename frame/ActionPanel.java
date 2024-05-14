package frame;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    //add terrain generation
    ActionPanel(){
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
                if((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0)){
                    g2D.setPaint(Color.RED);
                } else{
                    g2D.setPaint(Color.BLACK);
                }
                g2D.fillRect(i * size,j * size,size,size);
            }
        }
    }
}
