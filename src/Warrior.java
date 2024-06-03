import java.awt.geom.Point2D;
import java.util.Random;

public abstract class Warrior {
    public enum Team {
        RED, BLUE
    }

    public enum Direction {
        FORWARD, UP, DOWN, STUCK
    }

    final int attackChance; // In percent, from 0 to 100
    final int defenceChance; // In percent, from 0 to 100
    final double visionRadius;
    final double attackRadius;
    final Team team;
    Direction direction;
    int x;
    int y;

    public Warrior(int attackChance, int defenceChance, int visionRadius, int attackRadius, Team team, int x, int y) {
        this.attackChance = attackChance;
        this.defenceChance = defenceChance;
        this.visionRadius = visionRadius;
        this.attackRadius = attackRadius;
        this.direction = Direction.FORWARD;
        this.team = team;
        this.x = x;
        this.y = y;
    }

    public void moveForward() {
        int troopX = x;
        int newTroopX = troopX;
        switch (this.team) {
            case BLUE -> newTroopX = troopX + 1;
            case RED -> newTroopX = troopX - 1;
        }
        x = newTroopX;
    }

    public void moveUp() {
        y++;
    }

    public void moveDown() {
        y--;
    }

    public boolean attack(Warrior enemy, Random random) {
        final int attackValue = random.nextInt(100);
        if (attackValue <= attackChance) {
            final int defenseValue = random.nextInt(100);
            return defenseValue > enemy.defenceChance;
        }
        return false;
    }

    public boolean canSee(Warrior enemy) {
        return isInRange(enemy, visionRadius);
    }

    public boolean canAttack(Warrior enemy) {
        return isInRange(enemy, attackRadius);
    }

    private boolean isInRange(Warrior enemy, double range) {
        return Point2D.distance(x, y, enemy.x, enemy.y) <= range;
    }
}

class Swordsman extends Warrior {
    public Swordsman(int x, int y, Team team) {
        super(69, 69, 7, 7, team, x, y);
    }
}

class Archer extends Warrior {
    public Archer(int x, int y, Team team) {
        super(69, 69, 20, 10, team, x, y);
    }
}

class Shieldman extends Warrior {
    public Shieldman(int x, int y, Team team) {
        super(69, 69, 5, 3, team, x, y);
    }
}
