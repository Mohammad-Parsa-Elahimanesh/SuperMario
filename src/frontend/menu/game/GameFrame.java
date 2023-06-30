package frontend.menu.game;

import backend.Manager;
import backend.block.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    private final transient Manager manager = Manager.getInstance();

    public GameFrame() {
        super();
        setContentPane(new GamePanel());
        setBackground(Color.cyan);
        setUndecorated(true);
        setSize(manager.SCREEN_WIDTH, manager.SCREEN_HEIGHT);
        addKeyListener(keyListener());
    }

    KeyListener keyListener() {
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_SPACE)
                    manager.currentMario().Shot();
                if (manager.currentGame().timer.isRunning() && e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    manager.currentGame().Stop();
                    new ExitOrResume();
                }
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getExtendedKeyCode()) {
                    case KeyEvent.VK_LEFT -> manager.currentMario().task.put(Block.Direction.Left, true);
                    case KeyEvent.VK_UP -> manager.currentMario().task.put(Block.Direction.Up, true);
                    case KeyEvent.VK_RIGHT -> manager.currentMario().task.put(Block.Direction.Right, true);
                    case KeyEvent.VK_DOWN -> manager.currentMario().task.put(Block.Direction.Down, true);
                }
            }

            public void keyReleased(KeyEvent e) {
                switch (e.getExtendedKeyCode()) {
                    case KeyEvent.VK_LEFT -> manager.currentMario().task.put(Block.Direction.Left, false);
                    case KeyEvent.VK_UP -> manager.currentMario().task.put(Block.Direction.Up, false);
                    case KeyEvent.VK_RIGHT -> manager.currentMario().task.put(Block.Direction.Right, false);
                    case KeyEvent.VK_DOWN -> manager.currentMario().task.put(Block.Direction.Down, false);
                }
            }
        };
    }

}
