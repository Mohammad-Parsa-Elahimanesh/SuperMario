import javax.swing.*;

public class TileButton extends JButton {
    public void setTileSize(int width, int height) {
        super.setSize(width * Manager.getInstance().w, height * Manager.getInstance().h);
    }

    public void setTileLocation(int x, int y) {
        super.setLocation(x * Manager.getInstance().w, y * Manager.getInstance().h);
    }
}
