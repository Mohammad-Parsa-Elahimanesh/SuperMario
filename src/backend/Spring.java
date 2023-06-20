package backend;

public class Spring extends Block {
    Spring(double x, double y) {
        super(1, 1, x, y);
    }

    @Override
    String getImageName() {
        return "spring.png";
    }

    @Override
    boolean Pushed(Direction D) {
        if (D == Direction.Up)
            Manager.getInstance().CurrentGame().mario.vy = Manager.getInstance().CurrentGame().mario.getJumpSpeed() * 1.2;
        return false;
    }
}
