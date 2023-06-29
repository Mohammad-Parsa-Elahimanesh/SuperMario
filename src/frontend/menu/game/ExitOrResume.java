package frontend.menu.game;

import backend.Manager;
import frontend.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class ExitOrResume extends JFrame {
    ExitOrResume() {
        Manager.getInstance().CurrentMario().reset();
        setSize(200, 100);
        setLocation((Manager.getInstance().W - getWidth()) / 2, (Manager.getInstance().H - getHeight()) / 2);
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new GridLayout(3, 1));
        panel.add(ResumeGame());
        panel.add(Sound());
        panel.add(ExitGame());
        setUndecorated(true);
        setVisible(true);
    }

    JButton ExitGame() {
        JButton exitGame = new JButton("Exit Game");
        exitGame.addActionListener(e -> {
            new MainMenu();
            dispose();
        });
        exitGame.setToolTipText("Your progress will be saved !");
        return exitGame;
    }

    JButton Sound() {
        JButton sound = new JButton(AudioPlayer.getInstance().silenceForever?"turn on sound":"turn off sound");
        sound.addActionListener(e -> {
            AudioPlayer.getInstance().silenceForever = !AudioPlayer.getInstance().silenceForever;
            sound.setText(AudioPlayer.getInstance().silenceForever?"turn on sound":"turn off sound");
        });
        return sound;
    }

    JButton ResumeGame() {
        JButton resumeGame = new JButton("Continue ...");
        resumeGame.addActionListener(e -> {
            Manager.getInstance().CurrentGame().Resume();
            dispose();
        });
        return resumeGame;
    }
}
