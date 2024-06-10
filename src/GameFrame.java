import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame extends JFrame {
    private static final int mapHeight = 100;
    private static final int mapWidth = 100;
    private static final int maxTroopRows = 5;
    private static final Random random = new Random(); // TODO: Consider making the seed configurable for testing
    private static Terrain terrain = new Terrain(mapWidth, mapHeight, maxTroopRows, random);

    private final int maxTroops;

    private final JLabel statusLabel;
    private final JTextField rowNumberTextField;
    private final JTextField troopsNumberTextField;
    private final JButton submitButton;

    private final JButton resetButton;
    private final JButton startButton;
    private final JButton exitButton;
    private final JButton generateNewMap;
    private JPanel mainPanel;
    final ImageIcon gameLogoIcon = new ImageIcon("src/images/frame4.png");

    private enum TroopType {
        ARCHER, SWORDSMAN, SHIELDMAN
    }

    private TroopType selectedTroopType;
    private Warrior.Team selectedTeam;
    private boolean blueConfigured;
    private boolean redConfigured;

    private ActionPanel actionPanel;
    private final ArrayList<Warrior> troops = new ArrayList<>();

    GameFrame(final int maxTroops) {
        this.maxTroops = maxTroops;

        ImageIcon icon = new ImageIcon("src/images/logo2.png");
        this.setIconImage(icon.getImage());
        this.setTitle("Castle battle");
        this.setLocationRelativeTo(null);
//        this.setSize(1500, 800);
        this.setLayout(new BorderLayout(10, 10));

        final JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);

        statusLabel = new JLabel("Configure both teams to start");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        menuPanel.add(statusLabel, gridBagConstraints);

        final JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        menuPanel.add(menuLabel, gridBagConstraints);

        final JCheckBox sideCheckBox = new JCheckBox("Select a side");
        sideCheckBox.addActionListener(_ -> selectedTeam = sideCheckBox.isSelected() ? Warrior.Team.RED : Warrior.Team.BLUE);
        selectedTeam = Warrior.Team.BLUE;
        final ImageIcon blueTeamIcon = new ImageIcon("src/images/blue.png");
        sideCheckBox.setIcon(blueTeamIcon);
        sideCheckBox.setIcon(blueTeamIcon);
        final ImageIcon redTeamIcon = new ImageIcon("src/images/red.png");
        sideCheckBox.setSelectedIcon(redTeamIcon);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        menuPanel.add(sideCheckBox, gridBagConstraints);

        final JLabel troopsNumberLabel = new JLabel(STR."Troops amount (max: \{maxTroops}): ");
        troopsNumberTextField = new JTextField(8);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        menuPanel.add(troopsNumberLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        menuPanel.add(troopsNumberTextField, gridBagConstraints);

        final JLabel rowNumberLabel = new JLabel(STR."Row number (max: \{maxTroopRows}): ");
        rowNumberTextField = new JTextField(8);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        menuPanel.add(rowNumberLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        menuPanel.add(rowNumberTextField, gridBagConstraints);

        final JPanel troopsTypePanel = getTroopsTypePanel(this);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        menuPanel.add(troopsTypePanel, gridBagConstraints);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this::onSubmitButtonClick);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        styleButton(submitButton);
        menuPanel.add(submitButton, gridBagConstraints);

        resetButton = new JButton("Reset");
        resetButton.setEnabled(false);
        resetButton.addActionListener(this::onResetButtonClick);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        styleButton(resetButton);
        menuPanel.add(resetButton, gridBagConstraints);

        startButton = new JButton("Start");
        startButton.setEnabled(false);
        startButton.addActionListener(this::onStartButtonClick);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        styleButton(startButton);
        menuPanel.add(startButton, gridBagConstraints);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this::onExitButtonClick);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 1;
        styleButton(exitButton);
        exitButton.setBackground(Color.RED);
        menuPanel.add(exitButton, gridBagConstraints);

        generateNewMap = new JButton("New map");
        generateNewMap.setEnabled(false);
        generateNewMap.addActionListener(this::onGenerateNewMapClick);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 1;
        styleButton(generateNewMap);
        menuPanel.add(generateNewMap, gridBagConstraints);

        setMainPanel();

        final JComboBox<String> terrainTypeComboBox = new JComboBox<>();
        terrainTypeComboBox.setPreferredSize(new Dimension(100, 100)); // ?

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        }

        this.add(menuPanel, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1000, 1000));
        mainPanel.add(new JLabel(gameLogoIcon), BorderLayout.CENTER);
        mainPanel.setVisible(true);
    }

    private static JPanel getTroopsTypePanel(final GameFrame gameFrame) {
        final ButtonGroup troopsTypeButtonGroup = new ButtonGroup();
        final JPanel troopsTypePanel = new JPanel();
        troopsTypePanel.setLayout(new GridLayout(3, 1));
        final JRadioButton archerRadioButton = new JRadioButton("Archer", true);
        gameFrame.selectedTroopType = TroopType.ARCHER;
        final JRadioButton swordsmanRadioButton = new JRadioButton("Swordsman");
        final JRadioButton shieldmanRadioButton = new JRadioButton("Shieldman");
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

    private void clearTroopsInRow(final int rowNumber) {
        int targetX = (selectedTeam == Warrior.Team.BLUE) ? rowNumber - 1 : mapWidth - rowNumber;
        troops.removeIf(troop -> troop.x == targetX && troop.team == selectedTeam);
    }

    private void onExitButtonClick(ActionEvent actionEvent) {
        this.dispose();
    }

    private void onSubmitButtonClick(final ActionEvent event) {
        System.out.println("Submit button pressed");
        resetButton.setEnabled(true);
        generateNewMap.setEnabled(true);
        int troopsAmount;
        int rowNumber;
        try {
            troopsAmount = Integer.parseInt(troopsNumberTextField.getText());
            if (troopsAmount < 0 || troopsAmount > maxTroops) {
                throw new NumberFormatException("Troops amount out of range");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, STR."Please enter a valid number of troops (0-\{maxTroops}).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            troopsNumberTextField.setText("");
            return;
        }
        try {
            rowNumber = Integer.parseInt(rowNumberTextField.getText());
            if (rowNumber < 0 || rowNumber > maxTroopRows) {
                throw new NumberFormatException("Row number out of range");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, STR."Please enter a valid row number (0-\{maxTroopRows}).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            rowNumberTextField.setText("");
            return;
        }

        clearTroopsInRow(rowNumber);

        mainPanel.removeAll();
        generateTroops(troopsAmount, rowNumber);

        actionPanel = new ActionPanel(terrain, troops, random, this::onGameEnd);
        mainPanel.add(actionPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();

        switch (selectedTeam) {
            case BLUE -> blueConfigured = true;
            case RED -> redConfigured = true;
        }

        if (blueConfigured && redConfigured) {
            statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
            statusLabel.setText("Game not started");
            startButton.setEnabled(true);
        }
    }

    private void onGenerateNewMapClick(ActionEvent actionEvent) {
        onResetButtonClick(actionEvent);
        terrain = new Terrain(mapWidth, mapHeight, maxTroopRows, random);
        actionPanel = new ActionPanel(terrain, troops, random, this::onGameEnd);
    }

    private void onResetButtonClick(final ActionEvent event) {

        troops.clear();
        resetButton.setEnabled(false);
        generateNewMap.setEnabled(false);

        blueConfigured = false;
        redConfigured = false;

        mainPanel.removeAll();
        this.getContentPane().remove(mainPanel);
        setMainPanel();
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.invalidate();
        this.validate();

        rowNumberTextField.setText("");
        troopsNumberTextField.setText("");

        statusLabel.setText("Configure both teams to start");
        startButton.setEnabled(false);
        exitButton.setEnabled(true);
        submitButton.setEnabled(true);

        if (actionPanel != null) {
            actionPanel.stopGame();
        }

        selectedTroopType = TroopType.ARCHER;
        selectedTeam = Warrior.Team.BLUE;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(100, 30));
    }

    private Void onGameEnd(final String message) {
        statusLabel.setText(message);
        System.out.println(message);
        exitButton.setEnabled(true);
        return null;
    }

    private void onStartButtonClick(final ActionEvent e) {
        System.out.println("Start button pressed");
        generateNewMap.setEnabled(false);
        startButton.setEnabled(false);
        submitButton.setEnabled(false);
        exitButton.setEnabled(false);
        actionPanel.startGame();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setText("Game in progress");
        System.out.println("Game started");
    }


    public void generateTroops(final int amount, final int rowNumber) {
        final int step = mapHeight / amount;

        int troopX = 0;
        switch (selectedTeam) {
            case BLUE -> troopX = rowNumber - 1;
            case RED -> troopX = mapWidth - rowNumber;
        }
        for (int troopIndex = 0; troopIndex < amount; troopIndex++) {
            int troopY = troopIndex * step;
            // Offset every second row
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
