package world;

import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import screen.GlyphDelegate;
import world.Creature.IntPair;

public class EnemyAI extends SnakeAI implements Runnable {
    private List<String> messages;
    private static  int iD=0;
    public Creature getCreature(){
        return this.creature;
    }
    public EnemyAI(Creature creature, List<String> messages,int speed) {
        super(creature, messages);
        this.creature.setheadcolor(AsciiPanel.cyan); 
        this.messages = messages;
        this.speed = speed;
        this.pieces = new ArrayList<>();
        iD++;
        //the creature is virtually the first piece of the snake
        pieces.add(new SnakePiece(creature.x(), creature.y()));
    }

    @Override
    public boolean attack(Creature another) {
        // Example attack logic: deal damage to the other creature
        another.modifyHP(-creature.attackValue());
        return true;
    }

    @Override
    public void onUpdate() {
        ArrayList<IntPair> choice = creature.enterablepair();
        if (choice.isEmpty()) {
            // Handle case where no movement is possible
            return;
        }
    
        Creature player = this.creature.getworld().getCreatures().get(0);
        int dx = player.x() - creature.x();
        int dy = player.y() - creature.y();
    
        // 判断在哪个轴上移动
        int mx, my;
        if (Math.abs(dx) > Math.abs(dy)) {
            // 如果x轴的距离更大，则在x轴上移动
            mx = Integer.signum(dx);
            my = 0;
        } else {
            // 否则，在y轴上移动
            mx = 0;
            my = Integer.signum(dy);
        }
    
        IntPair movePair = new IntPair(mx, my);
        if (!choice.contains(movePair)) {
            movePair = chooseBestAlternative(choice);
        }
        Creature other = getCreature().getworld().creature(creature.x() + mx, creature.y() + my);

        if (other == null) {
            
            this.onEnter(creature.x() + mx, creature.y() + my, this.creature.getworld().tile(creature.x() + mx, creature.y() + my));
        } else {
            this.attack(other);
        }

        creature.moveBy(mx, my);
        System.out.println("now Snake " + iD + " at : " + creature.x() + "," + creature.y());
        if (creature.hp() == 0) {
            System.out.println("now Snake " + iD + " is dead");
        }
    }
    private IntPair chooseBestAlternative(ArrayList<IntPair> choices) {//最佳选项不可达时的选项，实际运行时ai还是存在一些逻辑问题
        IntPair bestChoice = null;
        double minDistance = Double.MAX_VALUE;
    
        for (IntPair choice : choices) {
            double distance = calculateDistance(choice);
            if (distance < minDistance) {
                minDistance = distance;
                bestChoice = choice;
            }
        }
    
        return bestChoice;
    }
    private int calculateDistance(IntPair choice) {//路径
        int deltaX = choice.getFirst() + creature.x();
        int deltaY = choice.getSecond() +creature.y();
        return deltaX * deltaX + deltaY * deltaY;
    }
    private int speed;
    public int getSpeed() {
        return speed;}
    public void setSpeed(int speed) {
        this.speed = speed;}
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // 在这里实现敌人的行为逻辑
            // 例如，计算移动、做出决策等
            onUpdate();

            try {
                Thread.sleep(2000/speed); // 根据需要调整时间间隔
            } catch (InterruptedException e) {
                // 线程被中断时的处理
                break;
            }
        }
    }
    

}
