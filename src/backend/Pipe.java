package backend;

public class Pipe extends Block {
    public Pipe(double h, double x) {
        super(2, h, x, 0);
    }

    @Override
    String getImageName() {
        return "Pipe.png";
    }

    @Override
    boolean Pushed(Direction D) {
        return false;
    }
}
