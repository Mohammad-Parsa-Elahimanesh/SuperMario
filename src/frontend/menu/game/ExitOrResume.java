package frontend.menu.game;

import backend.Manager;
import frontend.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class ExitOrResume extends JFrame {
    ExitOrResume() {
        setSize(200, 100);
        setLocation((Manager.getInstance().W - getWidth()) / 2, (Manager.getInstance().H - getHeight()) / 2);
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new GridLayout(2, 1));
        panel.add(ResumeGame());
        panel.add(ExitGame());
        setUndecorated(true);
        setVisible(true);
    }

    JButton ExitGame() {
        JButton exitGame = new JButton("Exit Game");
        exitGame.addActionListener(e -> {
            setVisible(false);
            new MainMenu();
        });
        exitGame.setToolTipText("Your progress will be saved !");
        return exitGame;
    }

    JButton ResumeGame() {
        JButton resumeGame = new JButton("Continue ...");
        resumeGame.addActionListener(e -> {
            Manager.getInstance().CurrentGame().Resume();
        });
        return resumeGame;
    }
}
