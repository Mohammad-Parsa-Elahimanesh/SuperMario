package backend;

import java.awt.*;

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

    @Override
    public void Draw(Graphics g, int cameraLeftLine) {
        super.Draw(g, cameraLeftLine);
    }
}
