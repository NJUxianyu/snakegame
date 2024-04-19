package screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import asciiPanel.AsciiPanel;
import world.Creature;
import world.CreatureFactory;
import world.World;
import world.WorldBuilder;
import ruler.Win;
import world.EnemyAI;

/**
 * SnakeGameScreen类是游戏的主要屏幕，用于显示游戏世界和处理用户输入。
 */
public class SnakeGameScreen implements Screen {

    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 100;

    private static final int SCREEN_WIDTH = 80;
    private static final int SCREEN_HEIGHT = 33;

    private static final int LEFT_KEY = 37;
    private static final int RIGHT_KEY = 39;
    private static final int UP_KEY = 38;
    private static final int DOWN_KEY = 40;

    private static final int DELAY_TIME = 200;
    private Timer timer;//计时器
    private int timeLeft = 60;
    private boolean TIME_RUNS_OUT=false;
    private int direction = 1;

    private Creature snake;
    private ArrayList<Creature> enemys;
    private ArrayList<Thread> enemiesThread;

    private List<String> messages;
    private List<String> oldMessages;

    private World world;
    private Win win;
    /**
     * 构造函数，创建SnakeGameScreen对象。
     */
    public SnakeGameScreen(Win win) {
        super();
        this.win=win;
        createWorld(win);
        this.messages = new ArrayList<String>();
        this.oldMessages = new ArrayList<String>();
        CreatureFactory creatureFactory = new CreatureFactory(this.world,this.win);
        createCreatures(creatureFactory);
        Timer countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                if (timeLeft <= 0) {
                    ((Timer)e.getSource()).stop();
                    // 倒计时结束，执行倒计时结束后的操作
                    TIME_RUNS_OUT=true;
                }
            }
        });
        this.timer = countdownTimer;
        timer.start();
    }

    /**
     * 创建游戏世界中的生物。
     * 
     * @param creatureFactory 生物工厂对象
     */
    private void createCreatures(CreatureFactory creatureFactory) {
        this.snake = creatureFactory.newSnakebody(this.messages);
        this.enemys = new ArrayList<Creature>();
        for (int i = 0; i < win.getNumberOfEnemys(); i++) {
            Creature enemy = creatureFactory.newEnemy(this.messages);
            this.enemys.add(enemy);
        }

        for (int i = 0; i < 80; i++) {
            creatureFactory.newBean();
        }
        for (int i = 0; i < 10; i++) {
            creatureFactory.newStar();
        }
        for (int i = 0; i < 20; i++) {
            creatureFactory.newTrap();
        }
        this.enemiesThread = new ArrayList<Thread>();
        for (Creature enemy : this.enemys) {
            if (enemy.getAI() instanceof EnemyAI) {
                EnemyAI enemyAI = (EnemyAI) enemy.getAI();
                Thread newenemyThread = new Thread(enemyAI);
                this.enemiesThread.add(newenemyThread)  ;
                System.out.println("enemy born at x:" + enemyAI.getCreature().x() + " y:" + enemyAI.getCreature().y());
                // terminal.write(2,enemyAI.getCreature().color(),enemyAI.getCreature().x(),enemyAI.getCreature().y());
                newenemyThread.start();
            }
        }
    }
    private void stopAllThreads() {
        for (Thread thread : enemiesThread) {
            thread.interrupt();
        }
    }

    /**
     * 创建游戏世界。
     */
    private void createWorld(Win win) {
        world = new WorldBuilder(WORLD_WIDTH, WORLD_HEIGHT,win).makeCaves().build(win);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        // 地形和生物
        displayTiles(terminal, getScrollX(), getScrollY());
        // 玩家
        ((GlyphDelegate) snake.getAI()).printGlyph(terminal, getScrollX(), getScrollY());
        // 统计信息
        // String stats = String.format("%3d/%3d hp", snake.hp(), snake.maxHP());
        // terminal.write(stats, 1, 23);
        // 消息
        // displayMessages(terminal, this.messages);
    }

    /**
     * 在终端上显示地形和生物。
     * 
     * @param terminal 终端对象
     * @param left     左边界
     * @param top      上边界
     */
    private void displayTiles(AsciiPanel terminal, int left, int top) {
        // 显示地形
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                int wx = x + left;
                int wy = y + top;

                if (snake.canSee(wx, wy)) {
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                } else {
                    terminal.write(world.glyph(wx, wy), x, y, Color.DARK_GRAY);
                }
            }
        }
        // 显示生物
        for (Creature creature : world.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + SCREEN_WIDTH && creature.y() >= top
                    && creature.y() < top + SCREEN_HEIGHT) {
                if (snake.canSee(creature.x(), creature.y())) {
                    if (creature.getAI() instanceof GlyphDelegate) {
                        ((GlyphDelegate) creature.getAI()).printGlyph(terminal, left, top);
                    } else {
                        terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                    }
                }
            }
        }
        // 生物可以选择下一步行动了
        terminal.write("Time left: " + " "+timeLeft,0,32);
        world.update();
    }

    /**
     * 在终端上显示消息。
     * 
     * @param terminal 终端对象
     * @param messages 消息列表
     */
    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = SCREEN_HEIGHT - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.write(messages.get(i), 1, top + i + 1);
        }
        this.oldMessages.addAll(messages);
        messages.clear();
    }

    /**
     * 获取水平滚动位置。
     * 
     * @return 水平滚动位置
     */
    public int getScrollX() {
        // System.out.println("snake.x(): " + snake.x());
        // System.out.println("SCREEN_WIDTH: " + SCREEN_WIDTH);
        // System.out.println("world.width(): " + world.width());
        
        return Math.max(0, Math.min(snake.x() - SCREEN_WIDTH / 2, world.width() - SCREEN_WIDTH));
        
    }

    /**
     * 获取垂直滚动位置。
     * 
     * @return 垂直滚动位置
     */
    public int getScrollY() {
        //   System.out.println("snake.y(): " + snake.y());
        // System.out.println("SCREEN_HEIGHT: " + SCREEN_HEIGHT);
        // System.out.println("world.height(): " + world.height());
        
        return Math.max(0, Math.min(snake.y() - SCREEN_HEIGHT / 2, world.height() - SCREEN_HEIGHT));
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                snake.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                snake.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                snake.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                snake.moveBy(0, 1);
                break;
            case KeyEvent.VK_ESCAPE:
                 stopAllThreads();
                return new StartScreen(this.win); // Replace with the appropriate screen
        }
        
        if(snake.getWinCondition() == Creature.WinCondition.PLAYER_REACHES_GOAL) {
            stopAllThreads();
            return new WinScreen(this.win);
        }
        if(!snake.getAI().isalive()){
            stopAllThreads();
            return new LoseScreen(this.win);
        }
        if(TIME_RUNS_OUT){
            stopAllThreads();
            return new LoseScreen(this.win);
        }
        if(snake.hp()==0){
            stopAllThreads();
            return new LoseScreen(this.win);
        }
        return this;
    }

}
