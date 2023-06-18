package frontend;

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
        addKeyListener(ArrowsListener());
        addKeyListener(StopListener());
    }

    KeyListener ArrowsListener() {
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getExtendedKeyCode()) {
                    case 37 ->
                            Manager.getInstance().CurrentGame().mario.left = Manager.getInstance().CurrentGame().mario.getSpeed();
                    case 38 -> Manager.getInstance().CurrentGame().mario.Jump();
                    case 39 ->
                            Manager.getInstance().CurrentGame().mario.right = Manager.getInstance().CurrentGame().mario.getSpeed();
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
                if (Manager.getInstance().CurrentGame().timer.isRunning() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

}
