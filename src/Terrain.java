import java.util.Random;

public class Terrain {
    // TODO: Match with max rows in GameLauncher
    private static final int SIDE_BORDER_WIDTH = 5;

    // Using enums for better readability
    public enum TerrainType {
        LAND, WATER, GRAVEL, MOUNTAIN
    }

    public final TerrainType[][] map;
    public final int mapWidth;
    public final int mapHeight;
    private final Random random;

    public Terrain(final int mapWidth, final int mapHeight, final Random random) {
        map = new TerrainType[mapWidth][mapHeight];
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.random = random;
        generateTerrain();
    }

    public boolean isInMapBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < mapWidth && y < mapHeight;
    }

    public boolean isMountain(int x, int y) {
        return map[x][y] == TerrainType.MOUNTAIN;
    }

    private void generateTerrain() {
        // Fill with land, TODO: maybe consider a more interesting starting point
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                map[x][y] = TerrainType.LAND;
            }
        }

        generateMountains();
        generateGravel();
        generateRiver();
    }

    private void generateMountains() {
        generateTerrainFeature(TerrainType.MOUNTAIN, random.nextInt(10) + 3, 6);
    }

    private void generateGravel() {
        generateTerrainFeature(TerrainType.GRAVEL, random.nextInt(10) + 4, 1);
    }

    private void generateRiver() {
        int currentX = random.nextInt(mapWidth - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
        int currentY = 0; // Start at the very top

        while (currentY < mapHeight) { // Go all the way to the bottom
            map[currentX][currentY] = TerrainType.WATER;

            int direction = random.nextInt(4);
            if (direction == 0 && currentX > SIDE_BORDER_WIDTH) {
                currentX--;
            } else if (direction == 1 && currentX < mapWidth - SIDE_BORDER_WIDTH - 1) {
                currentX++;
            }
            currentY++;
        }
    }

    private void generateTerrainFeature(TerrainType terrainType, int amount, int maxRadius) {
        for (int index = 0; index < amount; index++) {
            int x = random.nextInt(mapWidth - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
            int y = random.nextInt(mapHeight); // No border restriction for Y (top to bottom)
            int radius = random.nextInt(maxRadius) + 3;

            // Randomly adjust the radius to create non-circular shapes
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    int newY = y + j;
                    int newX = x + k;
                    int distance = (int) Math.sqrt(j * j + k * k);

                    // Randomly adjust distance to create irregularity
                    if (random.nextInt(10) < 2) {
                        distance += random.nextInt(2) - 1;
                    }

                    if (newX >= SIDE_BORDER_WIDTH &&
                            newX < mapWidth - SIDE_BORDER_WIDTH &&
                            newY >= 0 &&
                            newY < mapHeight &&
                            distance <= radius
                    ) {
                        map[newX][newY] = terrainType;
                    }
                }
            }
        }
    }
}
