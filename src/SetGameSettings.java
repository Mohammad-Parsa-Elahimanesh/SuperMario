import javax.swing.*;

public class SetGameSettings extends JFrame {
    SetGameSettings() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JComboBox selectDifficulty = new JComboBox(Game.Difficulty.values());
        JComboBox selectMario = new JComboBox(Main.CurrentUser().Marios().toArray());
        JButton Start = new JButton("Let's Start");
        Start.addActionListener(e -> {
            Main.CurrentGame().setDifficulty(Game.Difficulty.values()[selectDifficulty.getSelectedIndex()]);
            Main.CurrentGame().setMario(Main.CurrentUser().Marios().get(selectMario.getSelectedIndex()));
            setVisible(false);
            Main.CurrentGame().constructor();
        });
        panel.add(selectDifficulty);
        panel.add(selectMario);
        panel.add(Start);
        add(panel);
        setUndecorated(true);
        setSize(Main.W, Main.H);
        setVisible(true);
    }
}
