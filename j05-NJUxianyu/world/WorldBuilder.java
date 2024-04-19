package world;

import java.util.Random;
import ruler.Win;
public class WorldBuilder {

    private int width;
    private int height;
    private Tile[][] tiles;
    private int NUMBEROFBLOCKS;

    public WorldBuilder(int width, int height,Win win) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        NUMBEROFBLOCKS=win.getNumberOfBlocks();
    }

    public World build(Win win) {
        return new World(tiles,win);
    }

    private WorldBuilder randomizeTiles() {
        Random rand = new Random();
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                tiles[width][height] = Tile.FLOOR;
            }
        }

        int numBlocks = NUMBEROFBLOCKS;
        int blockSize = 10;
        int mapSize = 100;

        for (int i = 0; i < numBlocks; i++) {
            int startX = rand.nextInt(mapSize - blockSize);
            int startY = rand.nextInt(mapSize - blockSize);

            for (int x = startX; x < startX + blockSize; x++) {
                for (int y = startY; y < startY + blockSize; y++) {
                    switch (rand.nextInt(Tile.values().length - 1)) {
                        case 0:
                            tiles[x][y] = Tile.FLOOR;
                            break;
                        case 1:
                            tiles[x][y] = Tile.WALL;
                            break;
                    }
                }
            }
        }

        return this;
    }

    private WorldBuilder smooth(int factor) {
        Tile[][] newtemp = new Tile[width][height];
        if (factor > 1) {
            smooth(factor - 1);
        }
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                // Surrounding walls and floor
                int surrwalls = 0;
                int surrfloor = 0;

                // Check the tiles in a 3x3 area around center tile
                for (int dwidth = -1; dwidth < 2; dwidth++) {
                    for (int dheight = -1; dheight < 2; dheight++) {
                        if (width + dwidth < 0 || width + dwidth >= this.width || height + dheight < 0
                                || height + dheight >= this.height) {
                            continue;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.FLOOR) {
                            surrfloor++;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.WALL) {
                            surrwalls++;
                        }
                    }
                }
                Tile replacement;
                if (surrwalls > surrfloor) {
                    replacement = Tile.WALL;
                } else {
                    replacement = Tile.FLOOR;
                }
                newtemp[width][height] = replacement;
            }
        }
        tiles = newtemp;
        return this;
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles().smooth(19);
    }
}
