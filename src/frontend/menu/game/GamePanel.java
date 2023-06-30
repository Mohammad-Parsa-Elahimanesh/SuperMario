package frontend.menu.game;

import backend.Manager;
import backend.block.Block;
import backend.gamePlay.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    private final transient Manager manager = Manager.getInstance();
    Image background;

    {
        try {
            background = ImageIO.read(new File("Images\\background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GamePanel() {
        setLayout(null);
    }

    public void PaintInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 40));
        g.setColor(Color.GREEN);
        g.drawString("Time : " + (int) (manager.currentSection().wholeTime - manager.currentSection().spentTime), 0, 40);
        g.setColor(Color.RED);
        g.drawString("Heart: " + manager.currentMario().heart, 300, 40);
        g.setColor(Color.BLUE);
        g.drawString(Game.State(manager.currentGame()), 600, 40);
        g.setColor(Color.DARK_GRAY);
        g.drawString("Score: " + manager.currentGame().score, 1030, 40);
        g.setColor(Color.YELLOW);
        g.drawString("Coins: " + manager.currentSection().coins, 1300, 40);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, manager.SCREEN_WIDTH, manager.SCREEN_HEIGHT, this);
        final int cameraBeforeMario = 10;
        int cameraLeftLine = (int) (manager.SINGLE_BLOCK_WIDTH * Math.min(Math.max(0, manager.currentMario().X - cameraBeforeMario), manager.currentSection().W - manager.COLUMN));
        PaintInfo(g);
        for (Block block : manager.currentSection().blocks)
            block.Draw(g, cameraLeftLine);
    }
}
