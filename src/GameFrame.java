import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    GameFrame() {
        super();
        setBackground(Color.cyan);
        setUndecorated(true);
        setSize(Manager.getInstance().W, Manager.getInstance().H);
        addKeyListener(ArrowsListener());
        addKeyListener(StopListener());
    }

    KeyListener ArrowsListener() {
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getExtendedKeyCode()) {
                    case 37 -> Manager.getInstance().CurrentGame().mario.left = Manager.getInstance().CurrentGame().mario.getSpeed();
                    case 38 -> Manager.getInstance().CurrentGame().mario.Jump();
                    case 39 -> Manager.getInstance().CurrentGame().mario.right = Manager.getInstance().CurrentGame().mario.getSpeed();
                    case 40 -> Manager.getInstance().CurrentGame().mario.PushDown();
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
                    Manager.getInstance().CurrentGame().Stop();
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
        g.drawString("Time : " + (Integer) (Manager.getInstance().CurrentSection().wholeTime - Manager.getInstance().CurrentSection().spentTimeMS / 1000), 0, 40);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Score: " + Manager.getInstance().CurrentGame().score, Manager.getInstance().W / 4, 40);
        g.setColor(Color.YELLOW);
        g.drawString("Coins: " + Manager.getInstance().CurrentGame().coins, Manager.getInstance().W / 4 * 2, 40);
        g.setColor(Color.RED);
        g.drawString("Heart: " + Manager.getInstance().CurrentGame().mario.heart, Manager.getInstance().W / 4 * 3, 40);
    }

    public void paint(Graphics g) {
        final int cameraBeforeMario = 10;
        int cameraLeftLine = Math.min(Math.max(0, Manager.getInstance().CurrentGame().mario.X - cameraBeforeMario), Manager.getInstance().CurrentSection().W - Manager.getInstance().column);
        g.setColor(Color.cyan);
        for (int i = cameraLeftLine; i < cameraLeftLine + Manager.getInstance().column; i++)
            for (int j = 0; j < Manager.getInstance().row; j++) {
                boolean empty = true;
                Sky sky = new Sky();
                sky.setShape(1, 1, i, j);
                for (Block block : Manager.getInstance().CurrentSection().blocks)
                    if (Block.Intersect(sky, block)) {
                        empty = false;
                        break;
                    }
                if (empty)
                    sky.Draw(g, cameraLeftLine);
            }
        PaintInfo(g);
        for (Block block : Manager.getInstance().CurrentSection().blocks)
            block.Draw(g, cameraLeftLine);

        if (Manager.getInstance().CurrentGame().levels.length == Manager.getInstance().CurrentGame().levelNumber + 1 && Manager.getInstance().CurrentGame().levels[Manager.getInstance().CurrentGame().levelNumber].sections.length == Manager.getInstance().CurrentGame().sectionNumber + 1 && cameraLeftLine + 40 > Manager.getInstance().CurrentSection().W) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 200));
            g.drawString("Boss Fight", 0, 500);
        }
    }
}
