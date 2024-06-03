import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame extends JFrame {
    private static final int mapHeight = 100;
    private static final int mapWidth = 100;
    private static final Random random = new Random(); // TODO: Consider making the seed configurable for testing
    private static final Terrain terrain = new Terrain(mapWidth, mapHeight, random);

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
    private final ArrayList<Warrior> troops = new ArrayList<>();

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

        int troopsNumber = Integer.parseInt(troopsNumberTextField.getText());
        int rowNumber = Integer.parseInt(rowNumberTextField.getText());
        if (troopsNumber <= maxTroops && rowNumber <= rows) {
            mainPanel.removeAll();
            generateTroops();

            actionPanel = new ActionPanel(terrain, troops, random);
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
        int troopsNumber = Integer.parseInt(troopsNumberTextField.getText());
        int rowNumber = Integer.parseInt(rowNumberTextField.getText());
        int step = mapHeight / troopsNumber;

        int troopX = 0;
        switch (selectedTeam) {
            case BLUE -> troopX = rowNumber - 1;
            case RED -> troopX = mapWidth - rowNumber;
        }
        for (int troopIndex = 0; troopIndex < troopsNumber; troopIndex++) {
            int troopY = troopIndex * step;
            if (rowNumber % 2 == 0) {
                troopY++;
            }

            switch (selectedTroopType) {
                case ARCHER -> troops.add(new Archer(troopX, troopY, selectedTeam));
                case SWORDSMAN -> troops.add(new Swordsman(troopX, troopY, selectedTeam));
                case SHIELDMAN -> troops.add(new Shieldman(troopX, troopY, selectedTeam));
            }
        }
    }
}
