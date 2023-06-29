package frontend.menu.game;

import backend.Manager;
import backend.block.Checkpoint;

import javax.swing.*;
import java.awt.*;

public class CheckpointMenu extends JFrame {
    final Checkpoint checkpoint;
    final int progressRisk;
    public CheckpointMenu(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
        progressRisk = Manager.getInstance().CurrentSection().ProgressRisk();
        setSize(200, 100);
        setLocation((Manager.getInstance().W - getWidth()) / 2, (Manager.getInstance().H - getHeight()) / 2);
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new GridLayout(3, 1));
        panel.add(Save());
        panel.add(Ignore());
        panel.add(Destroy());
        setUndecorated(true);
        setVisible(true);
    }

    JButton Save() {
        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            if(Manager.getInstance().CurrentSection().coins < progressRisk)
                return;
            checkpoint.Save();
            dispose();
        });
        save.setToolTipText("Your progress and time will be saved and you must pay "+progressRisk+" coins.");
        return save;
    }

    JButton Ignore() {
        JButton ignore = new JButton("Ignore");
        ignore.addActionListener(e -> {
            checkpoint.Continue();
            dispose();
        });
        ignore.setToolTipText("Ignore checkpoint.");
        return ignore;
    }

    JButton Destroy() {
        JButton destroy = new JButton("Destroy");
        destroy.addActionListener(e -> {
            checkpoint.Destroy();
            dispose();
        });
        destroy.setToolTipText("Checkpoint will be destroyed and you gain "+progressRisk/4+" coins.");
        return destroy;
    }
}
