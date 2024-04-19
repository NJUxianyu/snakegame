package world;

import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import screen.GlyphDelegate;
import screen.WinScreen;
import ruler.Win;

public class SnakeAI extends CreatureAI implements GlyphDelegate {

    ArrayList<SnakePiece> pieces;
    
    private List<String> messages;

    public SnakeAI(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
        this.pieces = new ArrayList<>();
        
        //the creature is virtually the first piece of the snake
        pieces.add(new SnakePiece(creature.x(), creature.y()));
    }

    public ArrayList<SnakePiece> getPieces() {
        return pieces;
    }
  
    @Override
    public void onEnter(int x, int y, Tile tile) {

        int xPrev = pieces.get(0).x();
        int yPrev = pieces.get(0).y();

        if (tile.isGround()) {
            pieces.get(0).setX(x);
            pieces.get(0).setY(y);

            creature.setX(x);
            creature.setY(y);
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        } else {
            return;
        }

        if (pieces.size() > 1) {
            for (int i = 1; i < pieces.size(); i++) {
                int tempX = pieces.get(i).x();
                int tempY = pieces.get(i).y();
                pieces.get(i).setX(xPrev);
                pieces.get(i).setY(yPrev);
                xPrev = tempX;
                yPrev = tempY;
            }
        }
    }

    @Override
    public boolean attack(Creature another) {
        another.modifyHP(-creature.attackValue());
        
        if(another.getAI().attack(this.creature)) {
            this.grow();
        }
        return true;
    }

    public boolean grow() {
        // Get last element in list
        int x = pieces.get(pieces.size() - 1).x();
        int y = pieces.get(pieces.size() - 1).y();

        // Create new snake pieces and place them in a space that is not taken.
        SnakePiece piece = new SnakePiece(x - 1, y);
        if (!pieces.contains(piece)) {
            pieces.add(piece);
            return true;
        }

        SnakePiece piece2 = new SnakePiece(x + 1, y);
        if (!pieces.contains(piece2)) {
            pieces.add(piece);
            return true;
        }

        SnakePiece piece3 = new SnakePiece(x, y - 1);
        if (!pieces.contains(piece3)) {
            pieces.add(piece);
            return true;
        }

        SnakePiece piece4 = new SnakePiece(x, y + 1);
        if (!pieces.contains(piece4)) {
            pieces.add(piece);
            return true;
        }

        return false;

    }
    @Override
    public void onUpdate() {
       if(this.creature.getworld().getwin().checkVictoryCondition(this)){
         this.creature.setWinCondition(Creature.WinCondition.PLAYER_REACHES_GOAL);
        //  System.out.println("you won!");
       }
       //else System.out.println("you haven't won!");
    }
    

    @Override
    public void printGlyph(AsciiPanel terminal, int offsetX, int offsetY) {
        for (SnakePiece p : this.getPieces()) {
            if (p.x() - offsetX >= 80 || p.x() - offsetX < 0 || p.y() - offsetY >= 32 || p.y() - offsetY <= 0) {
                continue;
            }
            try {
                terminal.write(creature.glyph(), p.x() - offsetX, p.y() - offsetY, creature.color());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!this.getPieces().isEmpty()) {
            SnakePiece firstPiece = this.getPieces().get(0);
            if (firstPiece.x() - offsetX >= 80 || firstPiece.x() - offsetX < 0 || firstPiece.y() - offsetY >= 32 || firstPiece.y() - offsetY <= 0) {
                return;
            }
            try {
                terminal.write(creature.glyph(), firstPiece.x() - offsetX, firstPiece.y() - offsetY, creature.headcolor());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNotify(String message) {
        // Implement the method body here
    }
}
