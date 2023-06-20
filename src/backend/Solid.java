package backend;

public class Solid extends Block {
    SolidType solidType;
    transient int used = 0;
    Solid(SolidType solidType, double x, double y) {
        super(1, 1, x, y);
        this.solidType = solidType;
    }

    @Override
    String getImageName() {
        return used == 0 && solidType == SolidType.Prize ? "prize.png" : "solid.png";
    }

    @Override
    boolean Pushed(Direction D) {
        if(D == Direction.Down)
        {
            if(solidType == SolidType.Coins && used < 5){
                Manager.getInstance().CurrentSection().Add(new Coin(X, Y+1));
                used++;
            }
            else if(solidType == SolidType.Prize && used == 0) {
                // TODO: show prize
                used++;
            }

        }
        return false;
    }
}

enum SolidType {
    Simple,
    Coins,
    Prize
}
