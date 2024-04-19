package world;

import java.awt.Color;

import asciiPanel.AsciiPanel;
import java.util.ArrayList;

public class Creature {

    public static final int MOVE_UP = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_LEFT = 3;
    public static final int MOVE_RIGHT = 4;

    private World world;
    public World getworld() { return world; }
    private Color headcolor;
    
    public void setheadcolor(Color headcolor){
        this.headcolor = headcolor;
    }
    public Color headcolor(){
        return headcolor;
    }
    private int x, prevX;

    public void setX(int x) {
        this.prevX = this.x;
        this.x = x;
    }

    public int x() {
        return x;
    }

    public int prevX() {
        return prevX;
    }

    private int y, prevY;

    public void setY(int y) {
        this.prevY = this.y;
        this.y = y;
    }

    public int y() {
        return y;
    }

    public int prevY() {
        return prevY;
    }

    private char glyph;

    public char glyph() {
        return this.glyph;
    }

    private Color color;

    public Color color() {
        return this.color;
    }

    private CreatureAI ai;

    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    public CreatureAI getAI(){
        return this.ai;
    }

    private int maxHP;

    public int maxHP() {
        return this.maxHP;
    }

    private int hp;

    public int hp() {
        return this.hp;
    }

    public void modifyHP(int amount) {
        this.hp += amount;
        System.out.println("creature HP: +" +amount );
        if (this.hp < 1) {
            world.remove(this);
        }
    }

    private int attackValue;

    public int attackValue() {
        return this.attackValue;
    }

    private int defenseValue;

    public int defenseValue() {
        return this.defenseValue;
    }

    private int visionRadius;

    public int visionRadius() {
        return this.visionRadius;
    }
    public void changevisionRadius(int value){
        this.visionRadius = value;
    }

    public boolean canSee(int wx, int wy) {
        return ai.canSee(wx, wy);
    }

    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        Creature other = world.creature(x + mx, y + my);

        if (other == null) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        } else {
            ai.attack(other);
        }
    }

    public void update() {
        this.ai.onUpdate();
    }
    public ArrayList<IntPair> enterablepair() {
        ArrayList<IntPair> arrayList = new ArrayList<>();
        if (canEnter(x() + 1, y())) {
            arrayList.add(new IntPair(1, 0));
        }
        if (canEnter(x() -1, y())) {
            arrayList.add(new IntPair(1, 0));
        }
        if (canEnter(x() , y()+1)) {
            arrayList.add(new IntPair(1, 0));
        }
        if (canEnter(x() , y()-1)) {
            arrayList.add(new IntPair(1, 0));
        }
        return arrayList;
    }

    public static class IntPair {
        private int first;
        private int second;

        public IntPair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }
    public boolean canEnter(int x, int y) {
        return world.tile(x, y).isGround();
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public Creature(World world, char glyph, Color color, int maxHP, int attack, int defense, int visionRadius) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = visionRadius;
        this.headcolor = AsciiPanel.brightBlue;
    }
    public enum WinCondition {
    PLAYER_REACHES_GOAL,
    ENEMY_DEFEATED,
    TIME_RUNS_OUT
    }
    private WinCondition winCondition;
    public WinCondition getWinCondition(){
        return this.winCondition;
    }
    public void setWinCondition(WinCondition winCondition) {
        this.winCondition = winCondition;}
}
