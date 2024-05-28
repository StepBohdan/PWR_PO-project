import java.util.Arrays;

abstract class Warrior {
    int attackDmg;
    int attackRate;
    double evasion;

    static Terrain terrain = new Terrain();
    static int[][] generatedMap = terrain.getMap();

    public Warrior(int attackDmg, int attackRate, double evasion) {
        this.attackDmg = attackDmg;
        this.attackRate = attackRate;
        this.evasion = evasion;
    }

    public abstract int[] attackRange(int warriorRow, int warriorCol, int rows, int cols);

}

class Swordsman extends Warrior {
    public Swordsman(int attackDmg, int attackRate, double evasion) {
        super(attackDmg, attackRate, evasion);
    }

    @Override
    public int[] attackRange(int warriorRow, int warriorCol, int rows, int cols) {
        int[] range = new int[8];
        int index = 0;
        for (int i = Math.max(0, warriorRow - 1); i <= Math.min(rows - 1, warriorRow + 1); i++) {
            for (int j = Math.max(0, warriorCol - 1); j <= Math.min(cols - 1, warriorCol + 1); j++) {
                if (i == warriorRow && j == warriorCol) {
                    continue;
                }
                range[index++] = i * cols + j;
            }
        }
        return Arrays.copyOf(range, index);
    }
}

class Archer extends Warrior {
    public Archer(int attackDmg, int attackRate, double evasion) {
        super(attackDmg, attackRate, evasion);
    }

    @Override
    public int[] attackRange(int warriorRow, int warriorCol, int rows, int cols) {
        int[] range = new int[48];
        int index = 0;
        for (int i = Math.max(0, warriorRow - 2); i <= Math.min(rows - 1, warriorRow + 2); i++) {
            for (int j = Math.max(0, warriorCol - 2); j <= Math.min(cols - 1, warriorCol + 2); j++) {
                if (i == warriorRow && j == warriorCol) {
                    continue;
                }
                range[index++] = i * cols + j;
            }
        }
        return Arrays.copyOf(range, index);
    }
}

class Shieldman extends Warrior {
    public Shieldman(int attackDmg, int attackRate, double evasion) {
        super(attackDmg, attackRate, evasion);
    }



    @Override
    public int[] attackRange(int warriorRow, int warriorCol, int rows, int cols) {
        int[] range = new int[8];
        int index = 0;
        for (int i = Math.max(0, warriorRow - 1); i <= Math.min(rows - 1, warriorRow + 1); i++) {
            for (int j = Math.max(0, warriorCol - 1); j <= Math.min(cols - 1, warriorCol + 1); j++) {
                if (i == warriorRow && j == warriorCol) {
                    continue;
                }
                range[index++] = i * cols + j;
            }
        }
        return Arrays.copyOf(range, index);
    }
}

// Main class to run the application
public class Warriors {
    public static void main(String[] args) {
        Warrior[] warriors = new Warrior[]{
                new Swordsman(1, 1, 0.3),
                new Archer(1, 3, 0.0),
                new Shieldman(0, 1, 0.8)
        };

        // Print the map with warriors
        int[][] map = Warrior.generatedMap;
        for (int i = 0; i < Terrain.SIZE; i++) {
            for (int j = 0; j < Terrain.SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}