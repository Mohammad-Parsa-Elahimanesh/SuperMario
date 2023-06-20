package backend;

import java.awt.*;

public class Brick extends Block {

    public Brick(double w, double h, double x, double y) {
        super(w, h, x, y);
    }

    @Override
    boolean Pushed(Direction D) {
        return false;
    }

    @Override
    String getImageName() {
        return "Brick.png";
    }

    @Override
    public void Draw(Graphics g, int cameraLeftLine) {
        if (W == 1 && H == 1) {
            super.Draw(g, cameraLeftLine);
            return;
        }

        for (int i = (int) X; i < X + W; i++)
            for (int j = (int) Y; j < Y + H; j++)
                new Brick(1, 1, i, j).Draw(g, cameraLeftLine);
    }
}
