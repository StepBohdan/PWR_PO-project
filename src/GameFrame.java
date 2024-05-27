
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    JCheckBox side;
    ImageIcon trueCheck = new ImageIcon("src/true-check.png");
    ImageIcon falseCheck = new ImageIcon("src/false-check.png");
    ImageIcon gameLogo = new ImageIcon("src/game-logo.png");
    JPanel troopsNumberPanel;
    JLabel troopsNumberLabel;
    JPanel rowNumberPanel;
    JLabel rowNumberLabel;
    JTextField rowNumber;
    JTextField troopsNumber;
    ButtonGroup troopsType;
    JPanel troopsTypePanel;
    JRadioButton archer;
    JRadioButton swordsman;
    JRadioButton shieldman;
//    JLabel left_set;
//    JLabel right_set;

    JButton submit;
    JButton start;

    JLabel leftArmy;
    String leftArmyText = "Left army:\n";
    JLabel rightArmy;
    String rightArmyText = "Right army:\n";

    JPanel mainPanel;
    JPanel menu;
    JComboBox<String> terrainType;
    int max_troops;
    int rows;
    boolean left_side = false;
    boolean right_side = false;

    Terrain terrain = new Terrain();
    int[][] generatedMap = terrain.getMap();
    GameFrame(int max_troops, int rows){
        this.max_troops = max_troops;
        this.rows = rows;
        this.setSize(1500,800);
        this.setLayout(new BorderLayout(10, 10)); // probably will be changed to GridBagLayout

        menu = new JPanel();
        menu.setLayout(new GridLayout(6,2, 10, 10)); // same
        menu.setMaximumSize(new Dimension(200,300));

        troopsType = new ButtonGroup();
        troopsTypePanel = new JPanel();
        troopsTypePanel.setLayout(new GridLayout(3, 1));

        archer = new JRadioButton("Archer", true);
        swordsman = new JRadioButton("Swordsman");
        shieldman = new JRadioButton("Shieldman");
        archer.addActionListener(this);
        swordsman.addActionListener(this);
        shieldman.addActionListener(this);
        troopsType.add(archer);
        troopsType.add(swordsman);
        troopsType.add(shieldman);
        troopsTypePanel.add(archer);
        troopsTypePanel.add(swordsman);
        troopsTypePanel.add(shieldman);

        rowNumberPanel = new JPanel();
        rowNumberLabel = new JLabel("Enter the number of row: ");
        rowNumberPanel.setLayout(new GridLayout(2,1));
        rowNumber = new JTextField();
        rowNumberPanel.add(rowNumberLabel);
        rowNumberPanel.add(rowNumber);

        troopsNumberPanel = new JPanel();
        troopsNumberLabel = new JLabel("Enter amount of troops: ");
        troopsNumberPanel.setLayout(new GridLayout(2,1));
        troopsNumber = new JTextField();
        troopsNumberPanel.add(troopsNumberLabel);
        troopsNumberPanel.add(troopsNumber);

        side = new JCheckBox("Side");
        side.setFocusable(false);
        side.setIcon(falseCheck);
        side.setSelectedIcon(trueCheck);

        submit = new JButton("Submit");
        submit.addActionListener(this);

        start = new JButton("Start");
        start.setEnabled(false);

        menu.add(new JLabel(""));
        menu.add(new JLabel(""));
        menu.add(side);
        menu.add(troopsNumberPanel);
        menu.add(troopsTypePanel);
        menu.add(rowNumberPanel);
        menu.add(submit);
        menu.add(start);
        leftArmy = new JLabel(leftArmyText);
        rightArmy = new JLabel(rightArmyText);
        menu.add(new JLabel(""));
        menu.add(new JLabel(""));

//        mainPanel = new ActionPanel(generatedMap);
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(700,700));
        mainPanel.add(new JLabel(gameLogo));

        terrainType = new JComboBox<>();
        terrainType.setPreferredSize(new Dimension(100,50));

        this.add(menu, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(terrainType, BorderLayout.LINE_END);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            if (Integer.parseInt(troopsNumber.getText()) <= max_troops &&
                    Integer.parseInt(rowNumber.getText()) <= rows
            ){
                if (side.isSelected()) {
                    right_side = true;
                    // right army init
                } else {
                    left_side = true;
                    // left army init
                }
                System.out.println(right_side + " " + left_side + " " +Integer.parseInt(troopsNumber.getText()) + " "+Integer.parseInt(rowNumber.getText()));
                if(left_side && right_side){
                    start.setEnabled(true);

                }
            }
        }
        if (e.getSource() == start){
            mainPanel = new ActionPanel(generatedMap);
        }
    }
}
