package pl.edu.twoj.pakiet;

import java.util.Random;

public class Terrain {
    private static final int SIZE = 100;
    private static final int SIDE_BORDER_WIDTH = 3;

    private enum TerrainType {
        LAND, WATER, GRAVEL, MOUNTAIN
    }

    private TerrainType[][] map;
    private Random random;

    public Terrain() {
        map = new TerrainType[SIZE][SIZE];
        random = new Random();
        generateTerrain();
    }

    private void generateTerrain() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = TerrainType.LAND;
            }
        }
        generategay();
        generateMountains();
        generateRiver();
    }

    private void generateRiver() {
        int startX = random.nextInt(SIZE - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
        int currentX = startX;
        int currentY = 0;

        while (currentY < SIZE) {
            if (map[currentY][currentX] != TerrainType.MOUNTAIN) {
                map[currentY][currentX] = TerrainType.WATER;
            }

            int direction = random.nextInt(2);
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
            // Ensure features are generated within the side borders
            int x = random.nextInt(SIZE - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH;
            int y = random.nextInt(SIZE - 2 * SIDE_BORDER_WIDTH) + SIDE_BORDER_WIDTH; // No border restriction for Y (top to bottom)
            int radius = random.nextInt(maxRadius) + 3;

            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    int newX = x + j;
                    int newY = y + k;
                    int distance = (int) Math.sqrt(j * j + k * k);

                    if (random.nextInt(10) < 2) {
                        distance += random.nextInt(2) - 1;
                    }

                    // Check boundaries and distance for feature placement
                    if (newX >= SIDE_BORDER_WIDTH && newX < SIZE - SIDE_BORDER_WIDTH &&
                            newY >= 0 && newY < SIZE && distance <= radius) {
                        if( map [newX][newY] != TerrainType.MOUNTAIN){
                            map[newY][newX] = terrainType;
                        }

                    }
                }
            }
        }
    }

    private void generateMountains() {
        generateTerrainFeature(TerrainType.MOUNTAIN, random.nextInt(4) + 1, 5);
    }

    private void generategay() {
    int numRivers = random.nextInt(5) + 2;
    double gravelRadius = 1.4; 
    for (int r = 0; r < numRivers; r++) {
        int x = random.nextInt(SIZE);
        int y = random.nextInt(SIZE);
        int length = random.nextInt(SIZE / 2) + SIZE / 4;

        for (int i = 0; i < length; i++) {
            if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                for (double dx = -gravelRadius; dx <= gravelRadius; dx++) {
                    for (double dy = -gravelRadius; dy <= gravelRadius; dy++) {
                        double gravelX = x + dx;
                        double gravelY = y + dy;

                        // Check for both boundaries AND if it's NOT a mountain:
                        if (gravelX >= 0 && gravelX < SIZE &&
                            gravelY >= 0 && gravelY < SIZE &&
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
    public int[][] getMap() {
        int[][] intMap = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                intMap[i][j] = map[i][j].ordinal();
            }
        }
        return intMap;
    }

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
