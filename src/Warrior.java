public abstract class Warrior {
    public enum Team {
        RED, BLUE
    }

    public enum Direction {
        FORWARD, UP, DOWN, STUCK
    }

    final int attackChance;
    final int defenceChange;
    final int visionRadius;
    final int attackRadius;
    final Team team;
    Direction direction;
    int x;
    int y;

    public Warrior(int attackChance, int defenceChange, int visionRadius, int attackRadius, Team team, int x, int y) {
        this.attackChance = attackChance;
        this.defenceChange = defenceChange;
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
}

class Swordsman extends Warrior {
    public Swordsman(int x, int y, Team team) {
        super(69, 69, 69, 69, team, x, y);
    }
}

class Archer extends Warrior {
    public Archer(int x, int y, Team team) {
        super(69, 69, 69, 69, team, x, y);
    }
}

class Shieldman extends Warrior {
    public Shieldman(int x, int y, Team team) {
        super(69, 69, 69, 69, team, x, y);
    }
}
