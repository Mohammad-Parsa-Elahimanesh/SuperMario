package frontend;

import backend.Manager;

import javax.swing.*;

public class Massage extends JFrame {
    Massage(String s) {
        super();
        setSize(300, 100);
        setLocation((Manager.getInstance().W - getWidth()) / 2, (Manager.getInstance().H - getHeight()) / 2);
        add(new JLabel(s));
        setVisible(true);
    }
}
