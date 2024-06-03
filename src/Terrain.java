import java.util.Random;

public class Terrain {
    private static final int SIDE_BORDER_WIDTH = 5;

    // Using enums for better readability (but still outputting numbers)
    private enum TerrainType {
        LAND, WATER, GRAVEL, MOUNTAIN
    }

    private final TerrainType[][] map;
    private final int mapSize;
    private final Random random;

    public Terrain(final int mapSize) {
        map = new TerrainType[mapSize][mapSize];
        this.mapSize = mapSize;
        random = new Random(); // Consider making the seed configurable for testing
        generateTerrain();
    }

    private void generateTerrain() {
        // Fill with land, but maybe consider a more interesting starting point
        for (int x = 0; x < mapSize; x++) {
            for (int i = 0; i < mapSize; i++) {
                map[x][i] = TerrainType.LAND;
            }
        }

        generateMountains();
        generateGravel();
        generateRiver();
    }

    private void generateRiver() {
        int currentX = random.nextInt(mapSize - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
        int currentY = 0; // Start at the very top

        while (currentY < mapSize) { // Go all the way to the bottom
            map[currentX][currentY] = TerrainType.WATER;

            int direction = random.nextInt(4);
            if (direction == 0 && currentX > SIDE_BORDER_WIDTH) {
                currentX--;
            } else if (direction == 1 && currentX < mapSize - SIDE_BORDER_WIDTH - 1) {
                currentX++;
            }
            currentY++;
        }
    }

    private void generateTerrainFeature(TerrainType terrainType, int numFeatures, int maxRadius) {
        for (int i = 0; i < numFeatures; i++) {
            int y = random.nextInt(mapSize - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
            int x = random.nextInt(mapSize); // No border restriction for Y (top to bottom)
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

                    if (newX >= SIDE_BORDER_WIDTH &&
                            newX < mapSize - SIDE_BORDER_WIDTH &&
                            newY >= 0 &&
                            newY < mapSize &&
                            distance <= radius
                    ) {
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
        int[][] intMap = new int[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                intMap[i][j] = map[i][j].ordinal(); // Get the ordinal value of the enum
            }
        }
        return intMap;
    }

    // Main method for testing
    public static void main(String[] args) {
        final int mapSize = 100;
        Terrain terrain = new Terrain(mapSize);
        int[][] generatedMap = terrain.getMap();

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                System.out.print(generatedMap[i][j] + " ");
            }
            System.out.println();
        }
    }
}
