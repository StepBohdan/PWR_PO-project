package frame;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    //add terrain generation
    ActionPanel(){ //this whole block is temporary, will be remade using Graphics
        // how to implement game engine
        GridLayout gL = new GridLayout(100,100,1,1);
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        this.setLayout(gL);
        Dimension prefSize = new Dimension(2, 2);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                JPanel cell = new JPanel();
                cell.setBackground(Color.GREEN);
                cell.setPreferredSize(prefSize);
                add(cell);
            }
        }
        this.setPreferredSize(new Dimension(750,750));
        this.setMaximumSize(new Dimension(750,750));
        this.setSize(750,750);
        this.setFocusable(false);
        this.setVisible(true);
    }
}
