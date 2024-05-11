package frame;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    JButton confirm;
    JCheckBox side;
    JTextField troopsNumber;
    JRadioButton troopsType;
    JRadioButton archer;
    JRadioButton swordsman;
    JRadioButton shieldman;
    JPanel menu;
    GameFrame(){
        this.setSize(700,500);
        this.setLayout(new BorderLayout(3, 3));

        menu = new JPanel();
        menu.setLayout(new GridLayout(4,2));

        troopsType = new JRadioButton();
        archer = new JRadioButton("Archer");
        swordsman = new JRadioButton("Swordsman");
        shieldman = new JRadioButton("Shieldman");
        troopsType.add(archer);
        troopsType.add(swordsman);
        troopsType.add(shieldman);
        troopsType.setLayout(new FlowLayout());

        menu.add(troopsType);
        menu.setBackground(Color.BLACK);

        this.add(menu, BorderLayout.LINE_START);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
