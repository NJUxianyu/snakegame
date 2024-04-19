package world;

import java.util.List;

import asciiPanel.AsciiPanel;
import ruler.Win;

public class CreatureFactory {

    private World world;
    private Win win;

    public CreatureFactory(World world, Win win) {
        this.world = world;
        this.win = win;
    }


    public Creature newSnakebody(List<String> messages) {
        Creature snake = new Creature(this.world, (char) 2, AsciiPanel.red, 100, 20, 5, 9);
        world.addAtEmptyLocation(snake);
        new SnakeAI(snake, messages);
        return snake;
    }

    public Creature newBean() {
        Creature bean = new Creature(this.world, (char) 3, AsciiPanel.green, 10, 0, 0, 0);
        world.addAtEmptyLocation(bean);
        new BeanAI(bean, this);
        return bean;
    }
    public Creature newStar(){
        Creature star = new Creature(this.world, (char) 5, AsciiPanel.brightBlue, 10, 0, 0, 0);
        world.addAtEmptyLocation(star);
        new StarAI(star, this);
        return star;
    }
    public Creature newTrap() {
        Creature trap = new Creature(this.world, (char) 15, AsciiPanel.yellow, 50, 100, 0, 0);
        world.addAtEmptyLocation(trap);
        new TrapAI(trap, this);
        return trap;
    }
     public Creature newEnemy(List<String> messages) {
        Creature enemy = new Creature(this.world, (char) 1, AsciiPanel.brightGreen, 100, 20, 5, 9);
        world.addAtEmptyLocation(enemy);
        new EnemyAI(enemy, messages,win.getSpeed());
        
        return enemy;
    }
    
}
