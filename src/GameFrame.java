import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame {
    private static final int mapSize = 100;
    private static final Terrain terrain = new Terrain(mapSize);
    private static final Terrain.TerrainType[][] generatedMap = terrain.map;

    private final int maxTroops;
    private final int rows;

    private final JTextField rowNumberTextField;
    private final JTextField troopsNumberTextField;
    private final JButton submitButton;
    private final JButton startButton;
    private final JPanel mainPanel;

    private enum TroopType {
        ARCHER, SWORDSMAN, SHIELDMAN
    }

    private TroopType selectedTroopType;
    private Warrior.Team selectedTeam;
    private boolean blueConfigured;
    private boolean redConfigured;

    private ActionPanel actionPanel;
    private final Warrior[][] troops = new Warrior[mapSize][mapSize];

    GameFrame(int maxTroops, int rows) {
        this.maxTroops = maxTroops;
        this.rows = rows;

        this.setSize(1500, 800);
        this.setLayout(new BorderLayout(10, 10)); // probably will be changed to GridBagLayout

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);

        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        menuPanel.add(menuLabel, gridBagConstraints);

        JCheckBox sideCheckBox = new JCheckBox("Select a side");
        sideCheckBox.addActionListener(_ -> selectedTeam = sideCheckBox.isSelected() ? Warrior.Team.RED : Warrior.Team.BLUE);
        selectedTeam = Warrior.Team.BLUE;
        sideCheckBox.setFocusable(false);
        ImageIcon blueTeamIcon = new ImageIcon("src/images/blue.png");
        sideCheckBox.setIcon(blueTeamIcon);
        ImageIcon redTeamIcon = new ImageIcon("src/images/red.png");
        sideCheckBox.setSelectedIcon(redTeamIcon);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        menuPanel.add(sideCheckBox, gridBagConstraints);

        JLabel troopsNumberLabel = new JLabel("Troops amount (max: 50): ");
        troopsNumberTextField = new JTextField(8);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        menuPanel.add(troopsNumberLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        menuPanel.add(troopsNumberTextField, gridBagConstraints);

        JLabel rowNumberLabel = new JLabel("Row number (max: 5): ");
        rowNumberTextField = new JTextField(8);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        menuPanel.add(rowNumberLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        menuPanel.add(rowNumberTextField, gridBagConstraints);

        JPanel troopsTypePanel = getTroopsTypePanel(this);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        menuPanel.add(troopsTypePanel, gridBagConstraints);

        submitButton = new JButton("Submit");
        styleButton(submitButton);
        submitButton.addActionListener(this::onSubmitButtonClick);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        menuPanel.add(submitButton, gridBagConstraints);

        startButton = new JButton("Start");
        styleButton(startButton);
        startButton.setEnabled(false);
        startButton.addActionListener(this::onStartButtonClick);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        menuPanel.add(startButton, gridBagConstraints);

        // Настройка главной панели
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(700, 700));
        ImageIcon gameLogoIcon = new ImageIcon("src/images/frame.png");
        mainPanel.add(new JLabel(gameLogoIcon), BorderLayout.CENTER);

        // Настройка комбобокса типа местности
        JComboBox<String> terrainTypeComboBox = new JComboBox<>();
        terrainTypeComboBox.setPreferredSize(new Dimension(100, 100));

        // Добавление компонентов в фрейм
        this.add(menuPanel, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private static JPanel getTroopsTypePanel(GameFrame gameFrame) {
        ButtonGroup troopsTypeButtonGroup = new ButtonGroup();
        JPanel troopsTypePanel = new JPanel();
        troopsTypePanel.setLayout(new GridLayout(3, 1));
        JRadioButton archerRadioButton = new JRadioButton("Archer", true);
        gameFrame.selectedTroopType = TroopType.ARCHER;
        JRadioButton swordsmanRadioButton = new JRadioButton("Swordsman");
        JRadioButton shieldmanRadioButton = new JRadioButton("Shieldman");
        archerRadioButton.addActionListener(_ -> gameFrame.selectedTroopType = TroopType.ARCHER);
        swordsmanRadioButton.addActionListener(_ -> gameFrame.selectedTroopType = TroopType.SWORDSMAN);
        shieldmanRadioButton.addActionListener(_ -> gameFrame.selectedTroopType = TroopType.SHIELDMAN);
        troopsTypeButtonGroup.add(archerRadioButton);
        troopsTypeButtonGroup.add(swordsmanRadioButton);
        troopsTypeButtonGroup.add(shieldmanRadioButton);
        troopsTypePanel.add(archerRadioButton);
        troopsTypePanel.add(swordsmanRadioButton);
        troopsTypePanel.add(shieldmanRadioButton);
        return troopsTypePanel;
    }

    private void onSubmitButtonClick(ActionEvent e) {
        System.out.println("Submit button pressed");

        if (Integer.parseInt(troopsNumberTextField.getText()) <= maxTroops && Integer.parseInt(rowNumberTextField.getText()) <= rows) {
            mainPanel.removeAll();
            generateTroops();

            actionPanel = new ActionPanel(generatedMap, troops);
            mainPanel.add(actionPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();

            switch (selectedTeam) {
                case BLUE -> blueConfigured = true;
                case RED -> redConfigured = true;
            }

            if (blueConfigured && redConfigured) {
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

    public void generateTroops() {
        int troopsNumberInt = Integer.parseInt(troopsNumberTextField.getText());
        int rowNumberInt = Integer.parseInt(rowNumberTextField.getText());
        int step = mapSize / troopsNumberInt;

        switch (selectedTroopType) {
            case ARCHER -> {
                switch (selectedTeam) {
                    case BLUE -> {
                        for (int i = 0; i < troopsNumberInt; i++) {
                            if (rowNumberInt % 2 == 0) {
                                troops[rowNumberInt - 1][i * step + 1] = new Archer(selectedTeam);
                            } else {
                                troops[rowNumberInt - 1][i * step] = new Archer(selectedTeam);
                            }
                        }
                    }
                    case RED -> {
                        for (int i = 0; i < troopsNumberInt; i++) {
                            if (rowNumberInt % 2 == 0) {
                                troops[mapSize - rowNumberInt][i * step + 1] = new Archer(selectedTeam);
                            } else {
                                troops[mapSize - rowNumberInt][i * step] = new Archer(selectedTeam);
                            }
                        }
                    }
                }
            }
            case SWORDSMAN -> {
                switch (selectedTeam) {
                    case BLUE -> {
                        for (int i = 0; i < troopsNumberInt; i++) {
                            if (rowNumberInt % 2 == 0) {
                                troops[rowNumberInt - 1][i * step + 1] = new Swordsman(selectedTeam);
                            } else {
                                troops[rowNumberInt - 1][i * step] = new Swordsman(selectedTeam);
                            }
                        }
                    }
                    case RED -> {
                        for (int i = 0; i < troopsNumberInt; i++) {
                            if (rowNumberInt % 2 == 0) {
                                troops[mapSize - rowNumberInt][i * step + 1] = new Swordsman(selectedTeam);
                            } else {
                                troops[mapSize - rowNumberInt][i * step] = new Swordsman(selectedTeam);
                            }
                        }
                    }
                }
            }
            case SHIELDMAN -> {
                switch (selectedTeam) {
                    case BLUE -> {
                        for (int i = 0; i < troopsNumberInt; i++) {
                            if (rowNumberInt % 2 == 0) {
                                troops[rowNumberInt - 1][i * step + 1] = new Shieldman(selectedTeam);
                            } else {
                                troops[rowNumberInt - 1][i * step] = new Shieldman(selectedTeam);
                            }
                        }
                    }
                    case RED -> {
                        for (int i = 0; i < troopsNumberInt; i++) {
                            if (rowNumberInt % 2 == 0) {
                                troops[mapSize - rowNumberInt][i * step + 1] = new Shieldman(selectedTeam);
                            } else {
                                troops[mapSize - rowNumberInt][i * step] = new Shieldman(selectedTeam);
                            }
                        }
                    }
                }
            }
        }
    }
}
