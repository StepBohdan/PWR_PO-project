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
    private static final Terrain terrain = new Terrain(mapWidth, mapHeight, maxTroopRows, random);

    private final int maxTroops;

    private final JLabel statusLabel;
    private final JTextField rowNumberTextField;
    private final JTextField troopsNumberTextField;
    private final JButton submitButton;

    private final JButton resetButton;
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

    GameFrame(final int maxTroops) {
        this.maxTroops = maxTroops;

        ImageIcon icon = new ImageIcon("src/images/logo.png");
        this.setIconImage(icon.getImage());
        this.setTitle("Castle battle");
        this.setSize(1500, 800);
        this.setResizable(false);
        this.setLayout(new BorderLayout(10, 10)); // probably will be changed to GridBagLayout

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

        final JLabel troopsNumberLabel = new JLabel("Troops amount (max: " + maxTroops + "): ");
        troopsNumberTextField = new JTextField(8);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        menuPanel.add(troopsNumberLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        menuPanel.add(troopsNumberTextField, gridBagConstraints);

        final JLabel rowNumberLabel = new JLabel("Row number (max: " + maxTroopRows + "): ");
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

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(700, 700));
        final ImageIcon gameLogoIcon = new ImageIcon("src/images/frame.png");
        mainPanel.add(new JLabel(gameLogoIcon), BorderLayout.CENTER);

        final JComboBox<String> terrainTypeComboBox = new JComboBox<>();
        terrainTypeComboBox.setPreferredSize(new Dimension(100, 100));

        this.add(menuPanel, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
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
    private void onSubmitButtonClick(final ActionEvent event) {
        System.out.println("Submit button pressed");
        resetButton.setEnabled(true);
        int troopsAmount;
        int rowNumber;
        try {
            troopsAmount = Integer.parseInt(troopsNumberTextField.getText());
        } catch (NumberFormatException e) {
            troopsNumberTextField.setText("");
            return;
        }
        try {
            rowNumber = Integer.parseInt(rowNumberTextField.getText());
        } catch (NumberFormatException e) {
            rowNumberTextField.setText("");
            return;
        }
        if (troopsAmount < 0 || troopsAmount > maxTroops) {
            troopsNumberTextField.setText("");
            return;
        }
        if (rowNumber < 0 || rowNumber > maxTroopRows) {
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

    private void onResetButtonClick(final ActionEvent event) {
        // Clear the troops array
        troops.clear();
        resetButton.setEnabled(false);
        // Reset configuration flags
        blueConfigured = false;
        redConfigured = false;

        // Clear the main panel
        mainPanel.removeAll();
        mainPanel.repaint();

        // Clear text fields
        rowNumberTextField.setText("");
        troopsNumberTextField.setText("");

        // Reset status label and disable start button
        statusLabel.setText("Configure both teams to start");
        startButton.setEnabled(false);

        // Reset submit button if necessary
        submitButton.setEnabled(true);

        // If there's an ongoing action panel game, stop it
        if (actionPanel != null) {
            actionPanel.stopGame();
        }
        final ImageIcon gameLogoIcon = new ImageIcon("src/images/frame.png");
        mainPanel.add(new JLabel(gameLogoIcon), BorderLayout.CENTER);

        // Optionally, reset the selected troop type and team side
//        archerRadioButton.isSelected(true);
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
        return null;
    }

    private void onStartButtonClick(final ActionEvent e) {
        System.out.println("Start button pressed");

        startButton.setEnabled(false);
        submitButton.setEnabled(false);
        //mainPanel.revalidate();
        //mainPanel.repaint();
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
