//import java.util.*;
//
//import java.util.*;
//
//public class Simulation {
//    public static void main(String[] args) {
//        Terrain terrain = new Terrain();
//        List<Warrior> redTeam = new ArrayList<>();
//        List<Warrior> blueTeam = new ArrayList<>();
//
//        // Initialize warriors
//        redTeam.add(new Swordsman(10, 2, 0.1, 0, 0, "Red"));
//        redTeam.add(new Archer(8, 3, 0.2, 0, 1, "Red"));
//        redTeam.add(new Shieldman(12, 1, 0.3, 1, 0, "Red"));
//
//        blueTeam.add(new Swordsman(10, 2, 0.1, Terrain.SIZE - 1, Terrain.SIZE - 1, "Blue"));
//        blueTeam.add(new Archer(8, 3, 0.2, Terrain.SIZE - 1, Terrain.SIZE - 2, "Blue"));
//        blueTeam.add(new Shieldman(12, 1, 0.3, Terrain.SIZE - 2, Terrain.SIZE - 1, "Blue"));
//
//        // Run simulation
//        while (anyAlive(redTeam) && anyAlive(blueTeam)) {
//            for (Warrior red : redTeam) {
//                if (red.alive) {
//                    Warrior target = findClosestEnemy(red, blueTeam);
//                    if (target != null) {
//                        if (red.canAttack(target)) {
//                            red.attack(target);
//                        } else {
//                            red.move(target.row, target.col, terrain);
//                        }
//                    }
//                }
//            }
//
//            for (Warrior blue : blueTeam) {
//                if (blue.alive) {
//                    Warrior target = findClosestEnemy(blue, redTeam);
//                    if (target != null) {
//                        if (blue.canAttack(target)) {
//                            blue.attack(target);
//                        } else {
//                            blue.move(target.row, target.col, terrain);
//                        }
//                    }
//                }
//            }
//
//            printState(redTeam, blueTeam, terrain);
//        }
//
//        System.out.println("Simulation finished.");
//    }
//
//    private static boolean anyAlive(List<Warrior> team) {
//        for (Warrior warrior : team) {
//            if (warrior.alive) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static Warrior findClosestEnemy(Warrior warrior, List<Warrior> enemies) {
//        Warrior closest = null;
//        int minDistance = Integer.MAX_VALUE;
//
//        for (Warrior enemy : enemies) {
//            if (enemy.alive) {
//                int distance = Math.abs(warrior.row - enemy.row) + Math.abs(warrior.col - enemy.col);
//                if (distance < minDistance) {
//                    minDistance = distance;
//                    closest = enemy;
//                }
//            }
//        }
//
//        return closest;
//    }
//
//    private static void printState(List<Warrior> redTeam, List<Warrior> blueTeam, Terrain terrain) {
//        int[][] map = terrain.getMap();
//
//        // Clear previous state
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[i].length; j++) {
//                if (map[i][j] == 0) { // Reset land cells
//                    map[i][j] = 0;
//                }
//            }
//        }
//
//        // Mark warriors
//        for (Warrior red : redTeam) {
//            if (red.alive) {
//                map[red.row][red.col] = 5; // Red warriors
//            }
//        }
//        for (Warrior blue : blueTeam) {
//            if (blue.alive) {
//                map[blue.row][blue.col] = 6; // Blue warriors
//            }
//        }
//
//        // Print map
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[i].length; j++) {
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//}
