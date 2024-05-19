package pl.edu.twoj.pakiet;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private static final int SIZE = 100;
    // Using enums for better readability (but still outputting numbers)
    private enum TerrainType {
        LAND, WATER, GRAVEL, MOUNTAIN // 0,1,2,3 according 
    }
    private TerrainType[][] map;
    private Random random;

    public Terrain() {
        map = new TerrainType[SIZE][SIZE];
        random = new Random(); // Consider making the seed configurable for testing
        generateTerrain();
    }

    private void generateTerrain() {
        // Fill with land, but maybe consider a more interesting starting point
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = TerrainType.LAND;
            }
        }

        generateRivers();
        generateMountains();
        generateGravel();
    }

    private void generateRivers() {
        int numRivers = random.nextInt(5) + 2; // Increased range for more variety
        for (int r = 0; r < numRivers; r++) {
            List<Integer[]> riverPoints = new ArrayList<>();
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);
            int length = random.nextInt(SIZE / 2) + SIZE / 4;

            for (int i = 0; i < length; i++) {
                if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                    riverPoints.add(new Integer[]{x, y});

                    // More organic movement using a weighted random direction
                    int direction = random.nextInt(10);
                    if (direction < 5) {
                        x += random.nextInt(3) - 1; // Horizontal movement
                    } else {
                        y += random.nextInt(3) - 1; // Vertical movement
                    }
                } else {
                
                }
            }

            // "Draw" the river on the map
            for (Integer[] point : riverPoints) {
                map[point[0]][point[1]] = TerrainType.WATER;
            }
        }
    }

    private void generateTerrainFeature(TerrainType terrainType, int numFeatures, int maxRadius) {
        for (int i = 0; i < numFeatures; i++) {
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);
            int radius = random.nextInt(maxRadius) + 3;

            // Randomly adjust the radius to create non-circular shapes
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    int newX = x + j;
                    int newY = y + k;
                    int distance = (int) Math.sqrt(j * j + k * k);

                    // Randomly adjust distance to create irregularity
                    if (random.nextInt(10) < 2) {
                        distance += random.nextInt(2) - 1;
                    }

                    if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE && distance <= radius) {
                        map[newX][newY] = terrainType;
                    }
                }
            }
        }
    }

    private void generateMountains() {
        generateTerrainFeature(TerrainType.MOUNTAIN, random.nextInt(8) + 2, 6);
    }

    private void generateGravel() {
        generateTerrainFeature(TerrainType.GRAVEL, random.nextInt(8) + 2, 5);
    }

    public int[][] getMap() {
        // Convert TerrainType to integer values (0, 1, 2, 3)
        int[][] intMap = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                intMap[i][j] = map[i][j].ordinal(); // Get the ordinal value of the enum
            }
        }
        return intMap;
    }

    // Main method for testing
    public static void main(String[] args) {
        Terrain terrain = new Terrain();
        int[][] generatedMap = terrain.getMap();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(generatedMap[i][j] + " ");
            }
            System.out.println();
        }
    }
}
