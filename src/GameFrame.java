import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame {
    private final JCheckBox side;
    private final JTextField rowNumber;
    private final JTextField troopsNumber;
    private final JButton submitButton;
    private final JButton startButton;

    private final JPanel mainPanel;
    private final int maxTroops;
    private final int rows;

    private boolean leftSelected = false;
    private boolean rightSelected = false;

    private static final int mapSize = 100;
    private static final Terrain terrain = new Terrain(mapSize);
    private static final Terrain.TerrainType[][] generatedMap = terrain.map;

    ActionPanel actionPanel;

    GameFrame(int maxTroops, int rows) {
        this.maxTroops = maxTroops;
        this.rows = rows;

        this.setSize(1500, 800);
        this.setLayout(new BorderLayout(10, 10)); // probably will be changed to GridBagLayout

        JPanel menu = new JPanel();
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
        ImageIcon falseCheck = new ImageIcon("src/images/blue.png");
        side.setIcon(falseCheck);
        ImageIcon trueCheck = new ImageIcon("src/images/red.png");
        side.setSelectedIcon(trueCheck);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        menu.add(side, gbc);

        JLabel troopsNumberLabel = new JLabel("Troops amount (max: 50): ");
        troopsNumber = new JTextField(8);
        gbc.gridx = 0;
        gbc.gridy = 2;
        menu.add(troopsNumberLabel, gbc);
        gbc.gridx = 1;
        menu.add(troopsNumber, gbc);

        JLabel rowNumberLabel = new JLabel("Row number (max: 5): ");
        rowNumber = new JTextField(8);
        gbc.gridx = 0;
        gbc.gridy = 3;
        menu.add(rowNumberLabel, gbc);
        gbc.gridx = 1;
        menu.add(rowNumber, gbc);

        ButtonGroup troopsType = new ButtonGroup();
        JPanel troopsTypePanel = new JPanel();
        troopsTypePanel.setLayout(new GridLayout(3, 1));
        JRadioButton archerRadioButton = new JRadioButton("Archer", true);
        JRadioButton swordsmanRadioButton = new JRadioButton("Swordsman");
        JRadioButton shieldmanRadioButton = new JRadioButton("Shieldman");
        // TODO: Implement actions
        //archerRadioButton.addActionListener(this);
        //swordsmanRadioButton.addActionListener(this);
        //shieldmanRadioButton.addActionListener(this);
        troopsType.add(archerRadioButton);
        troopsType.add(swordsmanRadioButton);
        troopsType.add(shieldmanRadioButton);
        troopsTypePanel.add(archerRadioButton);
        troopsTypePanel.add(swordsmanRadioButton);
        troopsTypePanel.add(shieldmanRadioButton);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        menu.add(troopsTypePanel, gbc);

        submitButton = new JButton("Submit");
        styleButton(submitButton);
        submitButton.addActionListener(this::onSubmitButtonClick);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        menu.add(submitButton, gbc);

        startButton = new JButton("Start");
        styleButton(startButton);
        startButton.setEnabled(false);
        startButton.addActionListener(this::onStartButtonClick);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        menu.add(startButton, gbc);

        // Настройка главной панели
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(700, 700));
        ImageIcon gameLogo = new ImageIcon("src/images/frame.png");
        mainPanel.add(new JLabel(gameLogo), BorderLayout.CENTER);

        // Настройка комбобокса типа местности
        JComboBox<String> terrainType = new JComboBox<>();
        terrainType.setPreferredSize(new Dimension(100, 100));

        // Добавление компонентов в фрейм
        this.add(menu, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private void onSubmitButtonClick(ActionEvent e) {
        System.out.println("Submit button pressed");

        if (Integer.parseInt(troopsNumber.getText()) <= maxTroops && Integer.parseInt(rowNumber.getText()) <= rows) {
            mainPanel.removeAll();
            boolean leftSide = !side.isSelected();
            boolean rightSide = side.isSelected();
            //warriorArrangement();

            actionPanel = new ActionPanel(generatedMap);
            mainPanel.add(actionPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
            if (leftSide) {
                leftSelected = true;
            }
            if (rightSide) {
                rightSelected = true;
            }

            if (leftSelected && rightSelected) {
                startButton.setEnabled(true);
            }
        }
    }

    private void onStartButtonClick(ActionEvent e) {
        System.out.println("Start button pressed");

        startButton.setEnabled(false);
        submitButton.setEnabled(false);
        //mainPanel.revalidate();
        //mainPanel.repaint();
        actionPanel.startGame();
        System.out.println("Game started");
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
        boolean leftSide = !side.isSelected();
        boolean rightSide = side.isSelected();

        for (int i = 0; i < SIZE; i++) {
            if (leftSide) {
                generatedMap[row][i] = 0;
            } else if (rightSide) {
                generatedMap[SIZE - row - 1][i] = 0;
            }
        }
    }

    public void warriorArrangement() {
        int troopsNumberInt = Integer.parseInt(troopsNumber.getText());
        int rowNumberInt = Integer.parseInt(rowNumber.getText());
        int step = SIZE / troopsNumberInt;
        clearRow(rowNumberInt - 1);

        if (archer.isSelected()) {
            boolean leftSide = !side.isSelected();
            boolean rightSide = side.isSelected();

            if (leftSide) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt - 1][i * step + 1] = 5;
                    } else {
                        generatedMap[rowNumberInt - 1][i * step] = 5;
                    }
                }
            } else if (rightSide) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[SIZE - rowNumberInt][i * step + 1] = 7;
                    } else {
                        generatedMap[SIZE - rowNumberInt][i * step] = 7;
                    }
                }
            }
        } else if (swordsman.isSelected()) {
            if (leftSide) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt - 1][i * step + 1] = 4;
                    } else {
                        generatedMap[rowNumberInt - 1][i * step] = 4;
                    }
                }
            } else if (rightSide) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[SIZE - rowNumberInt][i * step + 1] = 8;
                    } else {
                        generatedMap[SIZE - rowNumberInt][i * step] = 8;
                    }
                }
            }
        } else if (shieldman.isSelected()) {
            if (leftSide) {
                for (int i = 0; i < troopsNumberInt; i++) {
                    if (rowNumberInt % 2 == 0) {
                        generatedMap[rowNumberInt - 1][i * step + 1] = 6;
                    } else {
                        generatedMap[rowNumberInt - 1][i * step] = 6;
                    }
                }
            } else if (rightSide) {
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
}
