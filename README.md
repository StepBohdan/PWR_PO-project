# Project Overview

## Project Name
PWR_PO Project

## Description
The PWR_PO project is a game application where players can engage in a strategic battle using warriors of different types. The game features various classes of warriors, each with unique abilities and characteristics. The project includes a graphical user interface (GUI) for an interactive user experience.

## Repository Structure
- **Root Directory**
  - `.gitignore`: Specifies files and directories to be ignored by Git.
  - `LICENSE`: License information for the project.
  - `warriors.iml`: IntelliJ IDEA module file.
  - `.idea` Directory: Configuration files for the IntelliJ IDEA project.
    - `misc.xml`
    - `modules.xml`
    - `uiDesigner.xml`
    - `vcs.xml`
    - `inspectionProfiles/`
      - `Default.xml`
      - `profiles_settings.xml`
    - `runConfigurations/`
      - `GameLauncher.xml`
- **src Directory**: Contains the source code and resources for the game.
  - **Java Source Files**
    - `ActionPanel.java`: Manages the user interface for the actions that can be taken by the player.
    - `GameFrame.java`: The main game window.
    - `GameLauncher.java`: The main class to launch the game.
    - `Terrain.java`: Represents the game terrain.
    - `Warrior.java`: Represents a warrior in the game.
    - `WarriorTests.java`: Unit tests for the Warrior class.
  - **Images Directory**: Contains image assets used in the game.
    - `blue-archer.png`: Image of a blue archer warrior.
    - `blue-shieldman.png`: Image of a blue shieldman warrior.
    - `blue-swordsman.png`: Image of a blue swordsman warrior.
    - `blue.png`: Image representing the blue team.
    - `frame.png`: First frame image.
    - `frame2.png`: Second frame image.
    - `frame3.png`: Third frame image.
    - `frame4.png`: Fourth frame image.
    - `logo.png`: Primary logo of the game.
    - `logo2.png`: Secondary logo of the game.
    - `red-archer.png`: Image of a red archer warrior.
    - `red-shieldman.png`: Image of a red shieldman warrior.
    - `red-swordsman.png`: Image of a red swordsman warrior.
    - `red.png`: Image representing the red team.

## Detailed Description of Java Classes
- **ActionPanel.java**: Handles the user interface for the game actions. This includes buttons and other interactive elements that allow the player to control their warriors and perform various in-game actions.
- **GameFrame.java**: Represents the main game window where the game is displayed. This class is responsible for setting up the main game interface, including the game area and any additional panels or components required for gameplay.
- **GameLauncher.java**: The entry point of the game application. This class initializes and launches the game, setting up necessary configurations and starting the game loop.
- **Terrain.java**: Represents the game terrain. This class handles the different types of terrain that can exist in the game, which may affect the movement and abilities of the warriors.
- **Warrior.java**: Defines the properties and behaviors of a warrior. This includes attributes such as health, attack power, and defense, as well as methods to perform actions like moving and attacking.
- **WarriorTests.java**: Contains unit tests for the Warrior class to ensure its methods and functionalities work as expected. This is essential for maintaining code reliability and catching any bugs or issues in the Warrior class.

## How to Run the Project
1. **Clone the Repository**:
    ```sh
    git clone <repository_url>
    ```
2. **Open in IntelliJ IDEA**:
   Open the cloned repository in IntelliJ IDEA.
3. **Build the Project**:
   Build the project using the build tools provided by IntelliJ IDEA.
4. **Run the Game**:
   Execute the `GameLauncher` class to start the game.

## License
This project is licensed under the terms specified in the `LICENSE` file.
