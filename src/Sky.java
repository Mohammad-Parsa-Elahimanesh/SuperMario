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
    void Draw(Graphics g, int cameraLeftLine) {
        g.setColor(Color.cyan);
        g.fillRect(Main.w * (X - cameraLeftLine), Main.H - Main.h * (Y + H), Main.w * W, Main.h * H);
    }
}
