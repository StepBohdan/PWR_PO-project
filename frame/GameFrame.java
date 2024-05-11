package frame;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    JButton confirm;
    JCheckBox side;
    JTextField troopsNumber;
    JRadioButton troopsType;
    JButton archer;
    JButton warrior;
    JButton shielder;
    GameFrame(){
        this.setSize(500,500);
        this.setLayout(new BorderLayout(3, 3));

        troopsType = new JRadioButton();
        archer = new JButton("Archer");
        warrior = new JButton("Warrior");
        shielder = new JButton("Shielder");
        troopsType.add(archer);
        troopsType.add(warrior);
        troopsType.add(shielder);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
