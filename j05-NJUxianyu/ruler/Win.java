package ruler;
import java.util.Iterator;

import world.SnakeAI;
public class Win{//rulers for win and others
    private int victoryLength;
    public void setVictoryLength(int victoryLength) {
        this.victoryLength = victoryLength;
    }
    public boolean checkVictoryCondition(SnakeAI snake) {
        return snake.getPieces().size() >= this.victoryLength;
    }
    public int getVictoryLength() {
        return this.victoryLength;
    }


    private int numberOfEnemys=1; 
    public int getNumberOfEnemys() {
        return numberOfEnemys;
    }
    public void setNumberOfEnemys(int numberOfEnemys) {
        this.numberOfEnemys = numberOfEnemys;
    }
    

    public Win(int victoryLength) {
        this.victoryLength = victoryLength;
    }

    
    private int speed=2;
    public int getSpeed() {
        return speed;}
    public void setSpeed(int speed) {
        this.speed=speed;
    }


    public int getNumberOfBlocks() {
        return mapmode.getNumber();}
    public void setNumberOfBlocks(NumberOfBlocksOfMap numberOfBlocks){
        this.mapmode =numberOfBlocks;
    }
    private NumberOfBlocksOfMap mapmode=NumberOfBlocksOfMap.NORMAL_BLOCKS;//地图方块数的间接控制
    public NumberOfBlocksOfMap getmapmode() {
        return mapmode;}
    public enum NumberOfBlocksOfMap{
        NO_BLOCK,
        FEWER_BLOCKS,
        NORMAL_BLOCKS,
        MORE_BLOCKS,
        MOST_BLOCKS;
        
        public int getNumber(){
            switch (this) {
                case NO_BLOCK:
                    return 0;
                case FEWER_BLOCKS:
                    return 100;
                case NORMAL_BLOCKS:
                    return 160;
                case MORE_BLOCKS:
                    return 210;
                case MOST_BLOCKS:
                    return 260;
                default:
                    return 160;
            }
        }
        public static NumberOfBlocksOfMap next(NumberOfBlocksOfMap current, boolean reverse) {
            NumberOfBlocksOfMap[] values = NumberOfBlocksOfMap.values();
            int currentIndex = current.ordinal();
    
            if (reverse) {
                currentIndex = (currentIndex - 1 + values.length) % values.length;
            } else {
                currentIndex = (currentIndex + 1) % values.length;
            }
    
            return values[currentIndex];
        }
    }
    public Win(){
        this.victoryLength = 25;
        this.numberOfEnemys = 1;
        this.speed =2;
        this.mapmode = NumberOfBlocksOfMap.NORMAL_BLOCKS;
    }

}
    


