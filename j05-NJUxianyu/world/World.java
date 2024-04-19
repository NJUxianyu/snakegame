package world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import ruler.Win;

public class World {

    private Tile[][] tiles;
    private int width;
    private int height;
    private List<Creature> creatures;
    private Win win;
    public Win getwin(){ return win; }
    public World(Tile[][] tiles,Win win) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
        this.win = win;
    }

    public Tile tile(int x, int y) {
        if (x <= 0 || x >= width || y <= 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char glyph(int x, int y) {
        return tiles[x][y].glyph();
    }

    public Color color(int x, int y) {
        return tiles[x][y].color();
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void dig(int x, int y) {
        if (tile(x, y).isDiggable()) {
            tiles[x][y] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Creature creature) {//随机生成至空白处
        int x;
        int y;

        do {
            x = (int) (Math.random() * this.width);
            y = (int) (Math.random() * this.height);
        } while (!tile(x, y).isGround() || this.creature(x, y) != null);

        creature.setX(x);
        creature.setY(y);

        this.creatures.add(creature);
    }

    public Creature creature(int x, int y) {//返回x,y处的creature
        for (Creature c : this.creatures) {
            if (c.x() == x && c.y() == y) {
                return c;
            }
        }
        return null;
    }

    public List<Creature> getCreatures() {
        return this.creatures;
    }

    public void remove(Creature target) {
        this.creatures.remove(target);
    }

    public void update() {
        ArrayList<Creature> toUpdate = new ArrayList<>(this.creatures);

        for (Creature creature : toUpdate) {
            if(!(creature.getAI() instanceof EnemyAI)) {//enemy 为单独线程，自更新
            creature.update();}
        }
    }
}
