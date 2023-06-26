package frontend.menu;

import backend.Manager;
import backend.Mario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Profile extends JFrame {
    Profile() {
        setSize(640, 360);
        setLocation((Manager.getInstance().W - getWidth()) / 2, (Manager.getInstance().H - getHeight()) / 2);
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("name : " + Manager.getInstance().CurrentUser().name));
        panel.add(new JLabel("max rating : " + Math.max(Manager.getInstance().CurrentUser().maxRating, 0)));
        panel.add(new JLabel("available marios : " + CommaSeparatedList(Manager.getInstance().CurrentUser().Marios())));
        add(panel);
        setVisible(true);
    }

    public static String CommaSeparatedList(List<Mario> list) {
        StringBuilder ret = new StringBuilder(" ");
        for (int i = 0; i < list.size(); i++) {
            ret.append(list.get(i).getName());
            if (i + 1 != list.size())
                ret.append(", ");
        }
        return ret.toString();
    }
}
