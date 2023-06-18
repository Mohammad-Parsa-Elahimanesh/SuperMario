package frontend;

import backend.Block;
import backend.Manager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel() {
        setLayout(null);
        setBackground(Color.CYAN);
    }

    public void PaintInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 40));
        g.setColor(Color.DARK_GRAY);
        g.drawString("Time : " + (Integer) (Manager.getInstance().CurrentSection().wholeTime - Manager.getInstance().CurrentSection().spentTimeMS / 1000), 0, 40);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Score: " + Manager.getInstance().CurrentGame().score, Manager.getInstance().W / 4, 40);
        g.setColor(Color.YELLOW);
        g.drawString("Coins: " + Manager.getInstance().CurrentGame().coins, Manager.getInstance().W / 4 * 2, 40);
        g.setColor(Color.RED);
        g.drawString("Heart: " + Manager.getInstance().CurrentGame().mario.heart, Manager.getInstance().W / 4 * 3, 40);
    }

    @Override
    protected void paintComponent(Graphics g) {
        final int cameraBeforeMario = 10;
        int cameraLeftLine = (int) Math.min(Math.max(0, Manager.getInstance().CurrentGame().mario.X - cameraBeforeMario), Manager.getInstance().CurrentSection().W - Manager.getInstance().column);
        PaintInfo(g);
        for (Block block : Manager.getInstance().CurrentSection().blocks)
            block.Draw(g, cameraLeftLine);
    }
}
