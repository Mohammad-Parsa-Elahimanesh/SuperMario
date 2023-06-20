package backend;

public class Solid extends Block {
    SolidType solidType;
    transient int used = 0;
    Solid(SolidType solidType, double w, double h, double x, double y) {
        super(w, h, x, y);
        this.solidType = solidType;
    }

    @Override
    String getImageName() {
        return used == 0 && solidType == SolidType.Prize ? "prize.png" : "solid.png";
    }

    @Override
    boolean Pushed(Direction D) {
        return false;
    }

    enum SolidType {
        Simple,
        Coins,
        Prize
    }
}
