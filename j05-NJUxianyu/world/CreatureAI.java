/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package world;
import java.awt.Point;

/**
 *
 * @author Aeranythe Echosong
 */
public abstract class CreatureAI {

    protected Creature creature;

    public CreatureAI(Creature creature) {
        this.creature = creature;
        this.creature.setAI(this);
    }
    public abstract void onEnter(int x, int y, Tile tile);

    public abstract boolean attack(Creature another);

    public abstract void onNotify(String message);

    public abstract void onUpdate();
    public boolean canSee(int x, int y) {//视野判断，玩家是否可见
        if ((creature.x() - x) * (creature.x() - x) + (creature.y() - y) * (creature.y() - y) > creature.visionRadius()
                * creature.visionRadius()) {
            return false;
        }
        // if (x == creature.x() || y == creature.y()){
        //     System.out.println("current location: " + x + "," + y );
        //     System.out.println("current creature: " + creature.x() + "," + creature.y());
        // }
        for (Point p : new Line(creature.x(), creature.y(), x, y)) {
            if (creature.tile(p.x, p.y).isGround() || (p.x == x && p.y == y)) {
                //             if (x == creature.x() || y == creature.y()){
                // System.out.println("Current coordinates: (" + p.x + ", " + p.y + ")");}
                continue;
            }
            // Print coordinates at the beginning of each new loop

           
            return false;
        }
        return true;
    }
    private boolean alive = true;
    public boolean isalive(){
        return creature.hp()>=0;
}
}
