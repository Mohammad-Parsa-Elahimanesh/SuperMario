package backend;

public class Soft extends Block{
    SoftType softType;
    Soft(SoftType softType, int x, int y) {
        super(1,1,x,y);
        this.softType = softType;
    }
    @Override
    String getImageName() {
        return "soft.png";
    }

    @Override
    boolean Pushed(Direction D) {
        if(D == Direction.Down)
        {
            if(softType == SoftType.Coin)
            {
                Manager.getInstance().CurrentSection().Add(new Coin(X, Y+1));
                softType = SoftType.Simple;
            }
            else
                Manager.getInstance().CurrentSection().Del(this);
        }
        return false;
    }
}

enum SoftType {
    Simple,
    Coin
}
