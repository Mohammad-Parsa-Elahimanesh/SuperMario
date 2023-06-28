package backend.block;


public class Checkpoint extends Block {
    boolean active = true;

    protected Checkpoint(double x, double y) {
        super(1, 3, x, y);
    }

    @Override
    protected String getImageName() {
        return "checkpoint.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }
}
