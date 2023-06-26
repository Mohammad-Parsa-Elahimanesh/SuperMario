package frontend.menu.game;

import backend.Block;
import backend.Game;
import backend.Manager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final transient Manager manager = Manager.getInstance();

    public GamePanel() {
        setLayout(null);
        setBackground(Color.CYAN);
    }

    public void PaintInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 40));
        g.setColor(Color.GREEN);
        g.drawString("Time : " + (Integer) (manager.CurrentSection().wholeTime - manager.CurrentSection().spentTimeMS / 1000), 0, 40);
        g.setColor(Color.RED);
        g.drawString("Heart: " + manager.CurrentGame().mario.heart, 300, 40);
        g.setColor(Color.BLUE);
        g.drawString(Game.State(manager.CurrentGame()), 600, 40);
        g.setColor(Color.DARK_GRAY);
        g.drawString("Score: " + manager.CurrentGame().score, 1030, 40);
        g.setColor(Color.YELLOW);
        g.drawString("Coins: " + manager.CurrentGame().coins, 1300, 40);

    }

    @Override
    protected void paintComponent(Graphics g) {
        final int cameraBeforeMario = 10;
        int cameraLeftLine = (int) (manager.w * Math.min(Math.max(0, manager.CurrentGame().mario.X - cameraBeforeMario), manager.CurrentSection().W - manager.column));
        PaintInfo(g);
        for (Block block : manager.CurrentSection().blocks)
            block.Draw(g, cameraLeftLine);
    }
}
