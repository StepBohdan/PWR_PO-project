package frame;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    JPanel sidePanel;
    JCheckBox side;
    JButton sideButton;
    ImageIcon trueCheck = new ImageIcon("frame/true-check.png");
    ImageIcon falseCheck = new ImageIcon("frame/false-check.png");
    JPanel troopsNumberPanel;
    JLabel troopsNumberLabel;
    JButton troopsNumberButton;
    JTextField troopsNumber;
    ButtonGroup troopsType;
    JPanel troopsTypePanel;
    JRadioButton archer;
    JRadioButton swordsman;
    JRadioButton shieldman;
    JPanel menu;
    GameFrame(){
        this.setSize(700,500);
        this.setLayout(new BorderLayout(3, 3));

        menu = new JPanel();
        menu.setLayout(new GridLayout(2,2));

        troopsType = new ButtonGroup();
        troopsTypePanel = new JPanel();
        troopsTypePanel.setLayout(new GridLayout(3, 1));

        archer = new JRadioButton("Archer");
        swordsman = new JRadioButton("Swordsman");
        shieldman = new JRadioButton("Shieldman");
        troopsType.add(archer);
        troopsType.add(swordsman);
        troopsType.add(shieldman);
        troopsTypePanel.add(archer);
        troopsTypePanel.add(swordsman);
        troopsTypePanel.add(shieldman);

        troopsNumberPanel = new JPanel();
        troopsNumberLabel = new JLabel("Enter amount of troops: ");
        troopsNumberButton = new JButton("Submit number");
        troopsNumberPanel.setLayout(new GridLayout(3,1));
        troopsNumber = new JTextField();
        troopsNumberPanel.add(troopsNumberLabel);
        troopsNumberPanel.add(troopsNumber);
        troopsNumberPanel.add(troopsNumberButton);

        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(2,1));
        sideButton = new JButton("Submit");
        side = new JCheckBox("Side");
        side.setFocusable(false);
        side.setIcon(falseCheck);
        side.setSelectedIcon(trueCheck);
        side.setBackground(Color.BLACK);
        sidePanel.add(side);
        sidePanel.add(sideButton);
        System.out.println(side.isSelected());

        menu.add(sidePanel);
        menu.add(troopsNumberPanel);
        menu.add(troopsTypePanel);

        this.add(menu, BorderLayout.LINE_START);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
