package frontend.menu.game;

import backend.Manager;
import backend.gamePlay.Game;

import javax.swing.*;

public class SetGameSettings extends JFrame {
    final private transient Manager manager = Manager.getInstance();

    public SetGameSettings() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JComboBox selectDifficulty = new JComboBox(Game.Difficulty.values());
        String[] marios = new String[manager.CurrentUser().Marios().toArray().length];
        for (int i = 0; i < marios.length; i++)
            marios[i] = manager.CurrentUser().Marios().get(i).getName();
        JComboBox selectMario = new JComboBox(marios);
        JButton Start = new JButton("Let's Start");
        Start.addActionListener(e -> {
            manager.CurrentGame().setDifficulty(Game.Difficulty.values()[selectDifficulty.getSelectedIndex()]);
            setVisible(false);
            manager.CurrentGame().constructor(manager.CurrentUser().Marios().get(selectMario.getSelectedIndex()));
        });
        panel.add(selectDifficulty);
        panel.add(selectMario);
        panel.add(Start);
        add(panel);
        setUndecorated(true);
        setSize(manager.W, manager.H);
        setVisible(true);
    }
}
