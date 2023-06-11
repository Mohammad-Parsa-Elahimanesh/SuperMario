import javax.swing.*;
import java.awt.*;

public class Profile extends JFrame {
    Profile() {
        setSize(640, 360);
        setLocation((Main.W - getWidth()) / 2, (Main.H - getHeight()) / 2);
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("name : " + Main.CurrentUser().name));
        panel.add(new JLabel("max rating : " + Math.max(Main.CurrentUser().maxRating, 0)));
        panel.add(new JLabel("available marios : " + Main.CommaSeparatedList(Main.CurrentUser().Marios())));
        add(panel);
        setVisible(true);
    }
}
