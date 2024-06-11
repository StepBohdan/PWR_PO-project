
import java.util.Random;

public class Terrain {
    // Using enums for better readability
    public enum TerrainType {
        LAND, WATER, GRAVEL, MOUNTAIN
    }

    // TODO: Add real values
    private static final int landPenalty = 1;
    private static final int gravelDefensePenalty = 2;
    private static final int waterAttackPenalty = 3;

    public final TerrainType[][] map;
    public final int mapWidth;
    public final int mapHeight;
    private final int verticalSafeZoneSize;
    private final Random random;

    public Terrain(final int mapWidth, final int mapHeight, final int verticalSafeZoneSize, final Random random) {
        map = new TerrainType[mapWidth][mapHeight];
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.verticalSafeZoneSize = verticalSafeZoneSize;
        this.random = random;
        generateTerrain();
    }

    public int getAttackPenalty(final int x, final int y) {
        if (isInMapBounds(x, y) && map[x][y] == TerrainType.WATER) {
            return waterAttackPenalty;
        } else {
            return landPenalty;
        }
    }

    public int getDefensePenalty(final int x, final int y) {
        if (isInMapBounds(x, y) && map[x][y] == TerrainType.GRAVEL) {
            return gravelDefensePenalty;
        } else {
            return landPenalty;
        }
    }

    public boolean isInMapBounds(final int x, final int y) {
        return x >= 0 && y >= 0 && x < mapWidth && y < mapHeight;
    }

    public boolean isMountain(final int x, final int y) {
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
        generateTerrainFeature(random.nextInt(10) + 3, 6);
    }

    private void generateGravel() {
        int numRivers = random.nextInt(5) + 2;
        double gravelRadius = 1.4;
        for (int r = 0; r < numRivers; r++) {
            int x = random.nextInt(mapWidth);
            int y = random.nextInt(mapHeight);
            int length = random.nextInt(mapWidth / 2) + mapHeight / 4;

            for (int i = 0; i < length; i++) {
                if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                    for (double dx = -gravelRadius; dx <= gravelRadius; dx++) {
                        for (double dy = -gravelRadius; dy <= gravelRadius; dy++) {
                            double gravelX = x + dx;
                            double gravelY = y + dy;

                            // Check for both boundaries AND if it's NOT a mountain:
                            if (gravelX >= 0 && gravelX < mapWidth &&
                                    gravelY >= 0 && gravelY < mapHeight &&
                                    map[(int) gravelX][(int) gravelY] != TerrainType.MOUNTAIN) {

                                map[(int) gravelX][(int) gravelY] = TerrainType.GRAVEL;
                            }
                        }
                    }
                    int direction = random.nextInt(10);
                    if (direction < 5) {
                        x += random.nextInt(3) - 1;
                    } else {
                        y += random.nextInt(3) - 1;
                    }
                }
            }
        }
    }

    private void generateRiver() {
        int currentX = random.nextInt(mapWidth - 2 * verticalSafeZoneSize) + verticalSafeZoneSize;
        int currentY = 0; // Start at the very top

        while (currentY < mapHeight) { // Go all the way to the bottom
            map[currentX][currentY] = TerrainType.WATER;

            final int direction = random.nextInt(4);
            if (direction == 0 && currentX > verticalSafeZoneSize) {
                currentX--;
            } else if (direction == 1 && currentX < mapWidth - verticalSafeZoneSize - 1) {
                currentX++;
            }
            currentY++;
        }
    }



    private void generateTerrainFeature(final int amount, final int maxRadius) {
        for (int index = 0; index < amount; index++) {
            final int x = random.nextInt(mapWidth - 2 * verticalSafeZoneSize) + verticalSafeZoneSize;
            final int y = random.nextInt(mapHeight); // No border restriction for Y (top to bottom)
            final int radius = random.nextInt(maxRadius) + 3;

            // Randomly adjust the radius to create non-circular shapes
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    final int newY = y + j;
                    final int newX = x + k;
                    int distance = (int) Math.sqrt(j * j + k * k);

                    // Randomly adjust distance to create irregularity
                    if (random.nextInt(10) < 2) {
                        distance += random.nextInt(2) - 1;
                    }

                    if (newX >= verticalSafeZoneSize &&
                            newX < mapWidth - verticalSafeZoneSize &&
                            newY >= 0 &&
                            newY < mapHeight &&
                            distance <= radius
                    ) {
                        map[newX][newY] = TerrainType.MOUNTAIN;
                    }
                }
            }
        }
    }
}
