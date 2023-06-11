import javax.swing.*;

public class TileLabel extends JLabel {
    public void setTileSize(int width, int height) {
        super.setSize(width * Main.w, height * Main.h);
    }

    public void setTileLocation(int x, int y) {
        super.setLocation(x * Main.w, y * Main.h);
    }
}
