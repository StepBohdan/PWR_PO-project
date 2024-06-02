
import java.util.Random;


public class Terrain {
    static final int SIZE = 100;
    private static final int SIDE_BORDER_WIDTH = 5;

    // Using enums for better readability (but still outputting numbers)
    private enum TerrainType {
        LAND, WATER, GRAVEL, MOUNTAIN // 0,1,2,3 according
    }

    private final TerrainType[][] map;
    private final Random random;

    public Terrain() {
        map = new TerrainType[SIZE][SIZE];
        random = new Random(); // Consider making the seed configurable for testing
        generateTerrain();
    }

    private void generateTerrain() {
        // Fill with land, but maybe consider a more interesting starting point
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                map[j][i] = TerrainType.LAND;
            }
        }

        generateMountains();
        generateGravel();
        generateRiver();
    }

    private void generateRiver() {


        int currentX = random.nextInt(SIZE - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
        int currentY = 0; // Start at the very top

        while (currentY < SIZE) { // Go all the way to the bottom
            map[currentX][currentY] = TerrainType.WATER;

            int direction = random.nextInt(4);
            if (direction == 0 && currentX > SIDE_BORDER_WIDTH) {
                currentX--;
            } else if (direction == 1 && currentX < SIZE - SIDE_BORDER_WIDTH - 1) {
                currentX++;
            }
            currentY++;
        }
    }


    private void generateTerrainFeature(TerrainType terrainType, int numFeatures, int maxRadius) {
        for (int i = 0; i < numFeatures; i++) {
            int y = random.nextInt(SIZE - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
            int x = random.nextInt(SIZE); // No border restriction for Y (top to bottom)
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

                    if (newX >= SIDE_BORDER_WIDTH && newX < SIZE - SIDE_BORDER_WIDTH &&
                            newY >= 0 && newY < SIZE && distance <= radius) {
                        map[newX][newY] = terrainType;
                    }
                }
            }
        }
    }

    private void generateMountains() {
        generateTerrainFeature(TerrainType.MOUNTAIN, random.nextInt(10) + 3, 6);
    }

    private void generateGravel() {

        generateTerrainFeature(TerrainType.GRAVEL, random.nextInt(10) + 4, 1);

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
