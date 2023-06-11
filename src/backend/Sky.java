package backend;

import java.awt.*;

public class Sky extends Block {
    String getImageName() {
        return null;
    }

    boolean Pushed(Direction D) {
        return false;
    }

    void Intersect(Block block) {
    }

    @Override
    public void Draw(Graphics g, int cameraLeftLine) {
        //g.setColor(Color.cyan);
        //g.fillRect(Manager.getInstance().w * ((int)X - cameraLeftLine), Manager.getInstance().H - Manager.getInstance().h * ((int)Math.ceil(Y + H)), (int) (Manager.getInstance().w * Math.ceil(W)), (int) (Manager.getInstance().h * Math.ceil(H)));
    }
}
