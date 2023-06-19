package frontend;

import backend.Block;
import backend.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    public GameFrame() {
        super();
        setContentPane(new GamePanel());
        setBackground(Color.cyan);
        setUndecorated(true);
        setSize(Manager.getInstance().W, Manager.getInstance().H);
        addKeyListener(keyListener());
    }

    KeyListener keyListener() {
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if (Manager.getInstance().CurrentGame().timer.isRunning() && e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    Manager.getInstance().CurrentGame().Stop();
                    new ExitOrResume();
                }
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getExtendedKeyCode()) {
                    case KeyEvent.VK_LEFT -> Manager.getInstance().CurrentGame().mario.task.put(Block.Direction.Left, true);
                    case KeyEvent.VK_UP -> Manager.getInstance().CurrentGame().mario.task.put(Block.Direction.Up, true);
                    case KeyEvent.VK_RIGHT -> Manager.getInstance().CurrentGame().mario.task.put(Block.Direction.Right, true);
                    case KeyEvent.VK_DOWN -> Manager.getInstance().CurrentGame().mario.task.put(Block.Direction.Down, true);
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
    }

}
