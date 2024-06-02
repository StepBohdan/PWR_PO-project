import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    JCheckBox side;
    ImageIcon trueCheck = new ImageIcon("src/images/red.png");
    ImageIcon falseCheck = new ImageIcon("src/images/blue.png");
    ImageIcon gameLogo = new ImageIcon("src/images/frame.png");
    JLabel troopsNumberLabel;
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

    ActionPanel actionPanel;

    GameFrame(int max_troops, int rows) {
        this.max_troops = max_troops;
        this.rows = rows;
        this.setSize(1500, 800);
        this.setLayout(new BorderLayout(10, 10)); // probably will be changed to GridBagLayout

        menu = new JPanel();
        menu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        menu.add(menuLabel, gbc);

        side = new JCheckBox("Select a side");
        side.setFocusable(false);
        side.setIcon(falseCheck);
        side.setSelectedIcon(trueCheck);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        menu.add(side, gbc);

        troopsNumberLabel = new JLabel("Troops amount (max: 50): ");
        troopsNumber = new JTextField(8);
        gbc.gridx = 0;
        gbc.gridy = 2;
        menu.add(troopsNumberLabel, gbc);
        gbc.gridx = 1;
        menu.add(troopsNumber, gbc);

        rowNumberLabel = new JLabel("Row number (max: 5): ");
        rowNumber = new JTextField(8);
        gbc.gridx = 0;
        gbc.gridy = 3;
        menu.add(rowNumberLabel, gbc);
        gbc.gridx = 1;
        menu.add(rowNumber, gbc);

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
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        menu.add(troopsTypePanel, gbc);

        submit = new JButton("Submit");
        styleButton(submit);
        submit.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        menu.add(submit, gbc);

        start = new JButton("Start");
        styleButton(start);
        start.setEnabled(false);
        start.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        menu.add(start, gbc);

        // Настройка главной панели
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(700, 700));
        mainPanel.add(new JLabel(gameLogo), BorderLayout.CENTER);

        // Настройка комбобокса типа местности
        terrainType = new JComboBox<>();
        terrainType.setPreferredSize(new Dimension(100, 100));

        // Добавление компонентов в фрейм
        this.add(menu, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(100, 30));
    }

    private void clearRow(int row) {
        left_side = !side.isSelected();
        right_side = side.isSelected();
        for (int i = 0; i < SIZE; i++) {
            if (left_side) {
                generatedMap[row][i] = 0;
            } else if (right_side) {
                generatedMap[SIZE - row - 1][i] = 0;
            }
        }
    }

    public void warriorArrangement() {
        // Red team
        // Swordsman - 4, Archer - 5, Shieldsman - 6
        // Blue team
        // Swordsman -7, Archer - 8, Shieldsman - 9
        int troopsNumberInt = Integer.parseInt(troopsNumber.getText());
        int rowNumberInt = Integer.parseInt(rowNumber.getText());
        int step = SIZE / troopsNumberInt;
        clearRow(rowNumberInt - 1);

        if (archer.isSelected()) {
            if (left_side) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt - 1][i * step + 1] = 5;
                    } else {
                        generatedMap[rowNumberInt - 1][i * step] = 5;
                    }
                }
            } else if (right_side) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[SIZE - rowNumberInt][i * step + 1] = 7;
                    } else {
                        generatedMap[SIZE - rowNumberInt][i * step] = 7;
                    }
                }
            }
        } else if (swordsman.isSelected()) {
            if (left_side) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt - 1][i * step + 1] = 4;
                    } else {
                        generatedMap[rowNumberInt - 1][i * step] = 4;
                    }
                }
            } else if (right_side) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[SIZE - rowNumberInt][i * step + 1] = 8;
                    } else {
                        generatedMap[SIZE - rowNumberInt][i * step] = 8;
                    }
                }
            }
        } else if (shieldman.isSelected()) {
            if (left_side) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt - 1][i * step + 1] = 6;
                    } else {
                        generatedMap[rowNumberInt - 1][i * step] = 6;
                    }
                }
            } else if (right_side) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[SIZE - rowNumberInt][i * step + 1] = 9;
                    } else {
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

            if (Integer.parseInt(troopsNumber.getText()) <= max_troops && Integer.parseInt(rowNumber.getText()) <= rows) {
                mainPanel.removeAll();
                left_side = !side.isSelected();
                right_side = side.isSelected();
                warriorArrangement();

                actionPanel = new ActionPanel(generatedMap);
                mainPanel.add(actionPanel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                if (left_side) {
                    leftSelected = true;
                }
                if (right_side) {
                    rightSelected = true;
                }

                if (leftSelected && rightSelected) {
                    start.setEnabled(true);
                }
            }
        } else if (e.getSource() == start) {
            System.out.println("Start button pressed");
            start.setEnabled(false);
            submit.setEnabled(false);
//            mainPanel.revalidate();
//            mainPanel.repaint();
            actionPanel.start_game();
            System.out.println("Game started");
        }
    }
}
