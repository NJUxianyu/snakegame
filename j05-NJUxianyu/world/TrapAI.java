package world;

public class TrapAI extends CreatureAI {

    private CreatureFactory factory;
    private static int trapCount = 0;

    public static int maxTraps = 5; // 最大陷阱数量
    public static double trapChance = 0.003; // 设置陷阱的概率

    public TrapAI(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate() {
        if (trapCount < TrapAI.maxTraps && Math.random() < TrapAI.trapChance) {
            setTrap();
        }
    }

    private void setTrap() {
        int newx = creature.x() + (int) (Math.random() * 9) - 4; // 生成陷阱的新位置
        int newy = creature.y() + (int) (Math.random() * 9) - 4;

        if (!creature.canEnter(newx, newy)) {
            return; // 如果新位置无法进入，则直接返回
        }

        Creature trap = this.factory.newTrap(); // 使用工厂方法创建新陷阱
        trap.setX(newx);
        trap.setY(newy);
        trapCount++;
    }

    // 其他方法可以根据陷阱的特定逻辑进行实现
    @Override
    public void onEnter(int x, int y, Tile tile) {
        // 实现生物进入陷阱时的逻辑
    }

    @Override
    public void onNotify(String message) {
        // 实现陷阱接收通知时的逻辑
    }

    @Override
    public boolean attack(Creature another) {
        // 如果陷阱具有攻击性，实现攻击逻辑
        another.modifyHP(-creature.attackValue());
        System.out.println("now hp is : "+ another.hp());
        return false;
    }
}

