import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class WarriorTests {

    @Test
    public void testSwordsmanAttributes() {
        Swordsman swordsman = new Swordsman(0, 0, Warrior.Team.RED);
        assertEquals(70, swordsman.attackChance, "Swordsman attackChance should be 70");
        assertEquals(50, swordsman.defenseChance, "Swordsman defenseChance should be 50");
        assertEquals(7, swordsman.visionRadius, "Swordsman visionRadius should be 7");
        assertEquals(2, swordsman.attackRadius, "Swordsman attackRadius should be 2");
        assertEquals(Warrior.Team.RED, swordsman.team, "Swordsman team should be RED");
        assertEquals(0, swordsman.x, "Swordsman x position should be 0");
        assertEquals(0, swordsman.y, "Swordsman y position should be 0");
    }

    @Test
    public void testArcherAttributes() {
        Archer archer = new Archer(1, 1, Warrior.Team.BLUE);
        assertEquals(50, archer.attackChance, "Archer attackChance should be 50");
        assertEquals(20, archer.defenseChance, "Archer defenseChance should be 20");
        assertEquals(10, archer.visionRadius, "Archer visionRadius should be 10");
        assertEquals(5, archer.attackRadius, "Archer attackRadius should be 5");
        assertEquals(Warrior.Team.BLUE, archer.team, "Archer team should be BLUE");
        assertEquals(1, archer.x, "Archer x position should be 1");
        assertEquals(1, archer.y, "Archer y position should be 1");
    }

    @Test
    public void testShieldmanAttributes() {
        Shieldman shieldman = new Shieldman(2, 2, Warrior.Team.RED);
        assertEquals(20, shieldman.attackChance, "Shieldman attackChance should be 20");
        assertEquals(80, shieldman.defenseChance, "Shieldman defenseChance should be 80");
        assertEquals(5, shieldman.visionRadius, "Shieldman visionRadius should be 5");
        assertEquals(1, shieldman.attackRadius, "Shieldman attackRadius should be 1");
        assertEquals(Warrior.Team.RED, shieldman.team, "Shieldman team should be RED");
        assertEquals(2, shieldman.x, "Shieldman x position should be 2");
        assertEquals(2, shieldman.y, "Shieldman y position should be 2");
    }

    @Test
    public void testMoveForward() {
        Swordsman swordsman = new Swordsman(0, 0, Warrior.Team.RED);
        swordsman.moveForward();
        assertEquals(-1, swordsman.x, "Swordsman should move forward to the left (team RED)");
        swordsman.moveForward();
        assertEquals(-2, swordsman.x, "Swordsman should move forward to the left (team RED)");

        Archer archer = new Archer(0, 0, Warrior.Team.BLUE);
        archer.moveForward();
        assertEquals(1, archer.x, "Archer should move forward to the right (team BLUE)");
        archer.moveForward();
        assertEquals(2, archer.x, "Archer should move forward to the right (team BLUE)");
    }

    @Test
    public void testMoveUp() {
        Shieldman shieldman = new Shieldman(0, 0, Warrior.Team.RED);
        shieldman.moveUp();
        assertEquals(1, shieldman.y, "Shieldman should move up");
        shieldman.moveUp();
        assertEquals(2, shieldman.y, "Shieldman should move up");
    }

    @Test
    public void testMoveDown() {
        Shieldman shieldman = new Shieldman(0, 2, Warrior.Team.RED);
        shieldman.moveDown();
        assertEquals(1, shieldman.y, "Shieldman should move down");
        shieldman.moveDown();
        assertEquals(0, shieldman.y, "Shieldman should move down");
    }

    @Test
    public void testAttackFailure() {
        Swordsman swordsman = new Swordsman(0, 0, Warrior.Team.RED);
        Shieldman shieldman = new Shieldman(1, 1, Warrior.Team.BLUE);
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 100; // Always return 100 to simulate failed attack
            }
        };
        assertFalse(swordsman.attack(shieldman, random, 1, 1), "Swordsman should fail to attack Shieldman");
    }

    @Test
    public void testCanSee() {
        Archer archer = new Archer(0, 0, Warrior.Team.BLUE);
        Shieldman shieldman = new Shieldman(0, 15, Warrior.Team.RED);
        assertFalse(archer.canSee(shieldman), "Archer should be able to see Shieldman");
        shieldman.y = 8;
        assertTrue(archer.canSee(shieldman), "Archer should not be able to see Shieldman");
    }

    @Test
    public void testCanAttack() {
        Swordsman swordsman = new Swordsman(0, 0, Warrior.Team.RED);
        Shieldman shieldman = new Shieldman(1, 1, Warrior.Team.BLUE);
        assertTrue(swordsman.canAttack(shieldman), "Swordsman should be able to attack Shieldman");
        shieldman.x = 3;
        assertFalse(swordsman.canAttack(shieldman), "Swordsman should not be able to attack Shieldman");
    }
}
