package frontend;

import backend.Game;
import backend.Manager;

import javax.swing.*;

public class SetGameSettings extends JFrame {
    public SetGameSettings() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JComboBox selectDifficulty = new JComboBox(Game.Difficulty.values());
        JComboBox selectMario = new JComboBox(Manager.getInstance().CurrentUser().Marios().toArray());
        JButton Start = new JButton("Let's Start");
        Start.addActionListener(e -> {
            Manager.getInstance().CurrentGame().setDifficulty(Game.Difficulty.values()[selectDifficulty.getSelectedIndex()]);
            Manager.getInstance().CurrentGame().setMario(Manager.getInstance().CurrentUser().Marios().get(selectMario.getSelectedIndex()));
            setVisible(false);
            Manager.getInstance().CurrentGame().constructor();
        });
        panel.add(selectDifficulty);
        panel.add(selectMario);
        panel.add(Start);
        add(panel);
        setUndecorated(true);
        setSize(Manager.getInstance().W, Manager.getInstance().H);
        setVisible(true);
    }
}
