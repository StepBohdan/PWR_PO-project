import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    JCheckBox side;
    ImageIcon trueCheck = new ImageIcon("src/images/red.png");
    ImageIcon falseCheck = new ImageIcon("src/images/blue.png");
    ImageIcon gameLogo = new ImageIcon("src/images/frame.png");
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
    private boolean leftSelected = false;
    private boolean rightSelected = false;

    static Terrain terrain = new Terrain();
    static int[][] generatedMap = terrain.getMap();
    static final int SIZE = generatedMap.length;

    GameFrame(int max_troops, int rows) {
        this.max_troops = max_troops;
        this.rows = rows;
        this.setSize(1500, 800);
        this.setLayout(new BorderLayout(10, 10)); // probably will be changed to GridBagLayout

        menu = new JPanel();
        menu.setLayout(new GridLayout(6, 2, 10, 10)); // same
        menu.setMaximumSize(new Dimension(200, 300));

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
        rowNumberPanel.setLayout(new GridLayout(2, 1));
        rowNumber = new JTextField();
        rowNumberPanel.add(rowNumberLabel);
        rowNumberPanel.add(rowNumber);

        troopsNumberPanel = new JPanel();
        troopsNumberLabel = new JLabel("Enter amount of troops: ");
        troopsNumberPanel.setLayout(new GridLayout(2, 1));
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
        start.addActionListener(this);

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

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(700, 700));
        mainPanel.add(new JLabel(gameLogo));

        terrainType = new JComboBox<>();
        terrainType.setPreferredSize(new Dimension(100, 50));

        this.add(menu, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(terrainType, BorderLayout.LINE_END);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    public void warriorArrangement() {
        // Red team
        // Swordsman - 4, Archer - 5, Shieldsman - 6
        // Blue team
        // Swordsman -7, Archer - 8, Shieldsman - 9
        int troopsNumberInt = Integer.parseInt(troopsNumber.getText());
        int rowNumberInt = Integer.parseInt(rowNumber.getText());
        int step = SIZE / troopsNumberInt;

        if (archer.isSelected()) {
            if (left_side) {
                new Archer(1, 3, 0.0, "BLUE");
                for (int i = 0; i < troopsNumberInt; i++) {
                    if(rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt-1][i * step +1] = 5;
                    }else{
                        generatedMap[rowNumberInt-1][i * step] = 5;
                    }
                }
            } else if (right_side) {
                new Archer(1, 3, 0.0, "RED");
                for (int i = 0; i < troopsNumberInt; i++) {
                    if(rowNumberInt % 2 == 0) {
                        generatedMap[SIZE - rowNumberInt][i * step+1] = 7;
                    }else{
                        generatedMap[SIZE - rowNumberInt][i * step] = 7;
                    }
                }
            }
        } else if (swordsman.isSelected()) {
            if (left_side) {
                new Swordsman(1, 1, 0.3, "BLUE");
                for (int i = 0; i < troopsNumberInt; i++) {
                    if(rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt-1][i * step + 1] = 4;
                    }else{
                        generatedMap[rowNumberInt-1][i * step] = 4;
                    }
                }
            } else if (right_side) {
                new Swordsman(1, 1, 0.3, "RED");
                for (int i = 0; i < troopsNumberInt; i++) {
                    if(rowNumberInt % 2 == 0) {
                            generatedMap[SIZE - rowNumberInt][i * step+1] = 8;
                        }else{
                            generatedMap[SIZE - rowNumberInt][i * step] = 8;
                    }
                }
            }
        } else if (shieldman.isSelected()) {
            if (left_side) {
                new Shieldman(0, 1, 0.8, "BLUE");
                for (int i = 0; i < troopsNumberInt; i++) {
                    if(rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt-1][i * step+1] = 6;
                    }else{
                        generatedMap[rowNumberInt-1][i * step] = 6;
                    }
                }
            } else if (right_side) {
                new Shieldman(0, 1, 0.8, "RED");
                for (int i = 0; i < troopsNumberInt; i++) {
                    if(rowNumberInt % 2 == 0) {
                        generatedMap[SIZE - rowNumberInt][i * step+1] = 9;
                    }else{
                        generatedMap[SIZE - rowNumberInt][i * step] = 9;
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submit) {
            System.out.println("Submit button pressed");
            if (Integer.parseInt(troopsNumber.getText()) <= max_troops &&
                    Integer.parseInt(rowNumber.getText()) <= rows) {
                left_side = !side.isSelected();
                right_side = side.isSelected();
                warriorArrangement();
                System.out.println("Right side: " + right_side + ", Left side: " + left_side + ", Troops number: " + Integer.parseInt(troopsNumber.getText()) + ", Row number: " + Integer.parseInt(rowNumber.getText()));

                if (left_side) {
                    leftSelected = true;
                }
                if (right_side) {
                    rightSelected = true;
                }

                if(leftSelected && rightSelected){
                    start.setEnabled(true);
                }
            }
        }else if (e.getSource() == start) {
            System.out.println("Start button pressed");
            mainPanel.removeAll();

            ActionPanel newPanel = new ActionPanel(generatedMap);
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(newPanel, BorderLayout.CENTER);

            mainPanel.revalidate();
            mainPanel.repaint();
            System.out.println("Game started");
        }
    }
}
