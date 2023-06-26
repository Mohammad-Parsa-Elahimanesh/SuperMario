package backend.block.brick;

import backend.Manager;
import backend.block.Block;
import backend.block.item.Coin;
import backend.block.item.Item;

public class Solid extends Block {
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
        if (D == Direction.Down && Neighbor(Manager.getInstance().CurrentGame().mario)) {
            Manager.getInstance().CurrentGame().score += 1;
            if (solidType == SolidType.Coins && used < 5) {
                Manager.getInstance().CurrentSection().Add(new Coin(X, Y + 1));
                used++;
            } else if (solidType == SolidType.Prize) {
                Manager.getInstance().CurrentSection().Add(Item.RandomItem(X, Y + 1));
                solidType = SolidType.Simple;
            } else
                Manager.getInstance().CurrentGame().score -= 1;
        }
        return false;
    }
}
