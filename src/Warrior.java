public abstract class Warrior {
    public enum Team {
        RED, BLUE
    }

    final Team team;
    final int attackChance;
    final int defenceChange;
    final int visionRadius;
    final int attackRadius;
    int x;
    int y;

    public Warrior(int attackChance, int defenceChange, int visionRadius, int attackRadius, Team team, int x, int y) {
        this.attackChance = attackChance;
        this.defenceChange = defenceChange;
        this.visionRadius = visionRadius;
        this.attackRadius = attackRadius;
        this.team = team;
        this.x = x;
        this.y = y;
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
