package backend;

public class Pipe extends Block {
    @Override
    String getImageName() {
        return "Pipe.png";
    }

    @Override
    void Intersect(Block block) {
    }

    @Override
    boolean Pushed(Direction D) {
        return false;
    }
}
