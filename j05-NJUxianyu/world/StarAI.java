package world;

public class StarAI extends CreatureAI {

    private CreatureFactory factory;
    private int spreadcount = 0;

    public static int stars = 5; // 修改变量名称以反映它们代表的是星星
    public static double spreadchance = 0.004;

    public StarAI(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate() {
        if (this.spreadcount < StarAI.stars && Math.random() < StarAI.spreadchance) {
            spread();
        }
    }

    private void spread() {
        int newx = creature.x() + (int) (Math.random() * 11) - 5;
        int newy = creature.y() + (int) (Math.random() * 11) - 5;

        if (!creature.canEnter(newx, newy)) {
            return;
        }

        Creature child = this.factory.newStar(); // 修改方法名称以反映创建星星
        child.setX(newx);
        child.setY(newy);
        spreadcount++;
    }

    // 以下方法根据需要进行调整或实现
    @Override
    public void onEnter(int x, int y, Tile tile) {
        // 实现进入新坐标时星星的逻辑
    }

    @Override
    public void onNotify(String message) {
        // 实现星星接收消息时的逻辑
    }

    @Override
    public boolean attack(Creature another) {
        another.changevisionRadius(another.visionRadius()+3);
        return true;
    }
}

