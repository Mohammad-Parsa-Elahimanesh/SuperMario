import javax.swing.*;
import java.awt.*;

public class PlayMenu extends JFrame {
    PlayMenu() {
        super();
        JPanel panel = new JPanel(new GridLayout(1, 3));
        for (int i = 0; i < 3; i++)
            panel.add(new LoadGamePanel(i, this));
        setUndecorated(true);
        setSize(Main.W, Main.H);
        add(panel);
        setVisible(true);
    }

}
