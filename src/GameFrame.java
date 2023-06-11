import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    GameFrame() {
        super();
        setBackground(Color.cyan);
        setUndecorated(true);
        setSize(Main.W, Main.H);
        addKeyListener(ArrowsListener());
        addKeyListener(StopListener());
    }

    KeyListener ArrowsListener() {
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getExtendedKeyCode()) {
                    case 37 -> Main.CurrentGame().mario.left = Main.CurrentGame().mario.getSpeed();
                    case 38 -> Main.CurrentGame().mario.Jump();
                    case 39 -> Main.CurrentGame().mario.right = Main.CurrentGame().mario.getSpeed();
                    case 40 -> Main.CurrentGame().mario.PushDown();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
    }

    KeyListener StopListener() {
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if (Game.timer.isRunning() && e.getKeyChar() == 's') {
                    Main.CurrentGame().Stop();
                    new ExitOrResume();
                }
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        };
    }

    public void PaintInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 40));
        g.setColor(Color.DARK_GRAY);
        g.drawString("Time : " + (Integer) (Main.CurrentSection().wholeTime - Main.CurrentSection().spentTimeMS / 1000), 0, 40);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Score: " + Main.CurrentGame().score, Main.W / 4, 40);
        g.setColor(Color.YELLOW);
        g.drawString("Coins: " + Main.CurrentGame().coins, Main.W / 4 * 2, 40);
        g.setColor(Color.RED);
        g.drawString("Heart: " + Main.CurrentGame().mario.heart, Main.W / 4 * 3, 40);
    }

    public void paint(Graphics g) {
        final int cameraBeforeMario = 10;
        int cameraLeftLine = Math.min(Math.max(0, Main.CurrentGame().mario.X - cameraBeforeMario), Main.CurrentSection().W - Main.column);
        g.setColor(Color.cyan);
        for (int i = cameraLeftLine; i < cameraLeftLine + Main.column; i++)
            for (int j = 0; j < Main.row; j++) {
                boolean empty = true;
                Sky sky = new Sky();
                sky.setShape(1, 1, i, j);
                for (Block block : Main.CurrentSection().blocks)
                    if (Block.Intersect(sky, block)) {
                        empty = false;
                        break;
                    }
                if (empty)
                    sky.Draw(g, cameraLeftLine);
            }
        PaintInfo(g);
        for (Block block : Main.CurrentSection().blocks)
            block.Draw(g, cameraLeftLine);

        if (Main.CurrentGame().levels.length == Main.CurrentGame().levelNumber + 1 && Main.CurrentGame().levels[Main.CurrentGame().levelNumber].sections.length == Main.CurrentGame().sectionNumber + 1 && cameraLeftLine + 40 > Main.CurrentSection().W) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 200));
            g.drawString("Boss Fight", 0, 500);
        }
    }
}
