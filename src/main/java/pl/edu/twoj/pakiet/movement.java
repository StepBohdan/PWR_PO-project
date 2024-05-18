package pl.edu.twoj.pakiet;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class WarriorsMovement {
    private static final int VISION_RADIUS = 7;
    private Warrior[][] army1;
    private Warrior[][] army2;

    public WarriorsMovement() {
        // Initialize armies
        this.army1 = createArmy(1); // Team 1
        this.army2 = createArmy(2); // Team 2
    }

    private Warrior[][] createArmy(int team) {
        int numWarriorsPerRow = 12;
        int numRows = 3;
        Warrior[][] army = new Warrior[numRows][numWarriorsPerRow];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numWarriorsPerRow; col++) {
                Warrior.Type type;
                switch (row) {
                    case 0:
                        type = Warrior.Type.ARCHER;
                        break;
                    case 1:
                        type = Warrior.Type.SWORDSMAN;
                        break;
                    default:
                        type = Warrior.Type.SHIELD;
                        break;
                }
                int warriorX = (team == 1) ? col * 10 : 80 + col * 10;
                int warriorY = row * 20;
                army[row][col] = new Warrior(type, warriorX, warriorY, team, row);
            }
        }
        return army;
    }

    public void moveWarriors() {
        moveArmy(army1, army2);
        moveArmy(army2, army1);
    }

    private void moveArmy(Warrior[][] myArmy, Warrior[][] enemyArmy) {
        for (Warrior[] warriorRow : myArmy) {
            for (Warrior warrior : warriorRow) {
                if (!warrior.isAlive()) {
                    continue;
                }

                List<Warrior> enemies = findEnemiesInVision(warrior, enemyArmy);
                if (!enemies.isEmpty()) {
                    Warrior nearestEnemy = findNearestEnemy(warrior, enemies);
                    if (isWithinAttackRange(warrior, nearestEnemy)) {
                        warrior.attack(nearestEnemy);
                    } else {
                        moveTowardsEnemy(warrior, nearestEnemy);
                    }
                } else {
                    // Default movement when no enemies in sight (replace with desired logic)
                    if (warrior.getX() < 90 && warrior.isMovingRight) { // Replace 90 with map width
                        warrior.moveRight();
                    } else if (warrior.getX() > 0 && !warrior.isMovingRight) {
                        warrior.moveLeft();
                    } else {
                        warrior.isMovingRight = !warrior.isMovingRight;
                    }
                }
            }
        }
    }

    private List<Warrior> findEnemiesInVision(Warrior warrior, Warrior[][] enemyArmy) {
        List<Warrior> enemies = new ArrayList<>();
        for (Warrior[] enemyRow : enemyArmy) {
            for (Warrior enemy : enemyRow) {
                if (enemy.isAlive() && isWithinVision(warrior, enemy)) {
                    enemies.add(enemy);
                }
            }
        }
        return enemies;
    }

    private boolean isWithinVision(Warrior warrior, Warrior other) {
        int distance = Math.abs(warrior.getX() - other.getX()) + Math.abs(warrior.getY() - other.getY());
        return distance <= VISION_RADIUS;
    }

    private boolean isWithinAttackRange(Warrior warrior, Warrior other) {
        int attackRange = 1;
        int distance = Math.abs(warrior.getX() - other.getX()) + Math.abs(warrior.getY() - other.getY());
        return distance <= attackRange;
    }

    private Warrior findNearestEnemy(Warrior warrior, List<Warrior> enemies) {
        Warrior nearestEnemy = null;
        int minDistance = Integer.MAX_VALUE;
        for (Warrior enemy : enemies) {
            int distance = Math.abs(warrior.getX() - enemy.getX()) + Math.abs(warrior.getY() - enemy.getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearestEnemy = enemy;
            }
        }
        return nearestEnemy;
    }

    private void moveTowardsEnemy(Warrior warrior, Warrior enemy) {
        if (warrior.getX() < enemy.getX()) {
            warrior.moveRight();
        } else if (warrior.getX() > enemy.getX()) {
            warrior.moveLeft();
        }
    }
}


