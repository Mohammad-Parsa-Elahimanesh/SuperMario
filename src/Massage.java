import javax.swing.*;

public class Massage extends JFrame {
    Massage(String s) {
        super();
        setSize(300, 100);
        setLocation((Main.W - getWidth()) / 2, (Main.H - getHeight()) / 2);
        add(new JLabel(s));
        setVisible(true);
    }
}
