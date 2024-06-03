import java.util.Arrays;

abstract class Warrior {
    public enum Team {
        RED, BLUE
    }

    final Team team;
    final int attackDmg;
    final int attackRate;
    final double evasion;

    public Warrior(int attackDmg, int attackRate, double evasion, Team team) {
        this.attackDmg = attackDmg;
        this.attackRate = attackRate;
        this.evasion = evasion;
        this.team = team;
    }

    public abstract int[] attackRange(int warriorRow, int warriorCol, int rows, int cols);
}

class Swordsman extends Warrior {
    public Swordsman(Team team) {
        super(69, 69, 69, team);
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
    public Archer(Team team) {
        super(69, 69, 69, team);
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
    public Shieldman(Team team) {
        super(69, 69, 69, team);
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
