package backend.block.brick;

import backend.Manager;
import backend.block.item.Coin;
import backend.block.item.Item;

public class Solid extends Brick {
    SolidType solidType;
    transient int used = 0;

    public Solid(SolidType solidType, double x, double y) {
        super(1, 1, x, y);
        this.solidType = solidType;
    }

    @Override
    protected String getImageName() {
        return solidType == SolidType.Prize ? "prize.png" : "solid.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (Neighbor(Manager.getInstance().currentMario(), Direction.Down)) {
            Manager.getInstance().currentGame().score += 1;
            if (solidType == SolidType.Coins && used < 5) {
                Manager.getInstance().currentSection().add(new Coin(X, Y + 1));
                used++;
            } else if (solidType == SolidType.Prize) {
                Manager.getInstance().currentSection().add(Item.RandomItem(X, Y + 1));
                solidType = SolidType.Simple;
            } else
                Manager.getInstance().currentGame().score -= 1;
        }
        return false;
    }
}
