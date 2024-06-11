import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Defines the properties and behaviors of a warrior. This includes attributes such as health, attack power, and defense, as well as methods to perform actions like moving and attacking.
 */
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

    /**
     * Constructor for Warrior.
     *
     * @param attackChance  The attack chance of the warrior
     * @param defenseChance The defense chance of the warrior
     * @param visionRadius  The vision radius of the warrior
     * @param attackRadius  The attack radius of the warrior
     * @param team          The team of the warrior
     * @param x             The x-coordinate of the warrior
     * @param y             The y-coordinate of the warrior
     */
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

    /**
     * Move the warrior forward.
     */

    public void moveForward() {
        final int troopX = x;
        int newTroopX = troopX;
        switch (this.team) {
            case BLUE -> newTroopX = troopX + 1;
            case RED -> newTroopX = troopX - 1;
        }
        x = newTroopX;
    }

    /**
     * Move the warrior up.
     */
    public void moveUp() {
        y++;
    }

    /**
     * Move the warrior down.
     */
    public void moveDown() {
        y--;
    }

    /**
     * Attack an enemy warrior.
     *
     * @param enemy          The enemy warrior to attack
     * @param random         The random number generator
     * @param attackPenalty  The attack penalty
     * @param defensePenalty The defense penalty
     * @return True if the attack was successful, false otherwise
     */
    public boolean attack(final Warrior enemy, final Random random, final int attackPenalty, final int defensePenalty) {
        final int attackValue = random.nextInt(100);
        if (attackValue <= (attackChance / attackPenalty)) {
            final int defenseValue = random.nextInt(100);
            return defenseValue > (enemy.defenseChance / defensePenalty);
        }
        return false;
    }

    /**
     * Check if the warrior can see an enemy.
     *
     * @param enemy The enemy warrior to check
     * @return True if the warrior can see the enemy, false otherwise
     */
    public boolean canSee(final Warrior enemy) {
        return isInRange(enemy, visionRadius);
    }

    /**
     * Check if the warrior can attack an enemy.
     *
     * @param enemy The enemy warrior to check
     * @return True if the warrior can attack the enemy, false otherwise
     */
    public boolean canAttack(final Warrior enemy) {
        return isInRange(enemy, attackRadius);
    }

    /**
     * Check if an enemy is within range.
     *
     * @param enemy The enemy warrior to check
     * @param range The range to check
     * @return True if the enemy is within range, false otherwise
     */
    private boolean isInRange(final Warrior enemy, final double range) {
        return Point2D.distance(x, y, enemy.x, enemy.y) <= range;
    }
}

class Swordsman extends Warrior {
    /**
     * Constructor for Swordsman.
     *
     * @param x    The x-coordinate of the swordsman
     * @param y    The y-coordinate of the swordsman
     * @param team The team of the swordsman
     */
    public Swordsman(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(70, 50, 7, 2, team, x, y);
    }
}

class Archer extends Warrior {

    /**
     * Constructor for Archer.
     *
     * @param x    The x-coordinate of the archer
     * @param y    The y-coordinate of the archer
     * @param team The team of the archer
     */
    public Archer(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(50, 20, 10, 5, team, x, y);
    }
}

class Shieldman extends Warrior {
    /**
     * Constructor for Shieldman.
     *
     * @param x    The x-coordinate of the shieldman
     * @param y    The y-coordinate of the shieldman
     * @param team The team of the shieldman
     */
    public Shieldman(final int x, final int y, final Team team) {
        // TODO: Add real values
        super(20, 80, 5, 1, team, x, y);
    }
}
