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
    final int defenseChance; // In percent, from 0 to 100
    final double visionRadius;
    public final double attackRadius;
    public final Team team;
    public Direction direction;
    public int x;
    public int y;

    public Warrior(final int attackChance, final int defenseChance, final int visionRadius, final int attackRadius, final Team team, final int x, final int y) {
        this.attackChance = attackChance;
        this.defenseChance = defenseChance;
        this.visionRadius = visionRadius;
        this.attackRadius = attackRadius;
        this.direction = Direction.FORWARD;
        this.team = team;
        this.x = x;
        this.y = y;
    }

    public void moveForward() {
        final int troopX = x;
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

    public boolean attack(final Warrior enemy, final Random random, final int attackPenalty, final int defensePenalty) {
        final int attackValue = random.nextInt(100);
        if (attackValue <= (attackChance / attackPenalty)) {
            final int defenseValue = random.nextInt(100);
            return defenseValue > (enemy.defenseChance / defensePenalty);
        }
        return false;
    }

    public boolean canSee(final Warrior enemy) {
        return isInRange(enemy, visionRadius);
    }

    public boolean canAttack(final Warrior enemy) {
        return isInRange(enemy, attackRadius);
    }

    private boolean isInRange(final Warrior enemy, final double range) {
        return Point2D.distance(x, y, enemy.x, enemy.y) <= range;
    }
}

class Swordsman extends Warrior {
    public Swordsman(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(70, 50, 7, 2, team, x, y);
    }
}

class Archer extends Warrior {
    public Archer(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(50, 20, 10, 5, team, x, y);
    }
}

class Shieldman extends Warrior {
    public Shieldman(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(20, 80, 5, 1, team, x, y);
    }
}
