package frontend.tile;

import backend.Manager;

import javax.swing.*;

public class TileTextField extends JTextField {
    public void setTileSize(int width, int height) {
        super.setSize(width * Manager.getInstance().w, height * Manager.getInstance().h);
    }

    public void setTileLocation(int x, int y) {
        super.setLocation(x * Manager.getInstance().w, y * Manager.getInstance().h);
    }
}
