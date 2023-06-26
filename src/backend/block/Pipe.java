package backend.block;

public class Pipe extends Block {
    public Pipe(double x, double h) {
        super(2, h, x, 0);
    }

    @Override
    protected String getImageName() {
        return "Pipe.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return false;
    }
}
