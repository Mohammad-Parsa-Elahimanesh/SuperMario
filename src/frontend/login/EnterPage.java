package frontend.login;

import backend.Manager;
import frontend.tile.TileButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterPage extends JFrame {
    public EnterPage() {
        super();
        JPanel panel = new JPanel(null);
        setUndecorated(true);
        setSize(Manager.getInstance().W, Manager.getInstance().H);
        panel.add(SignInButton());
        panel.add(SignUpButton());
        panel.add(ExitButton());
        add(panel);
        setVisible(true);
    }

    TileButton SignInButton() {
        TileButton signInButton = new TileButton();
        signInButton.setText("Sign In");
        signInButton.setTileLocation(10, 4);
        signInButton.setTileSize(4, 2);
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignInPage();
                dispose();
            }
        });
        return signInButton;
    }

    TileButton SignUpButton() {
        TileButton signUpButton = new TileButton();
        signUpButton.setText("Sign Up");
        signUpButton.setTileLocation(10, 7);
        signUpButton.setTileSize(4, 2);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpPage();
                dispose();
            }
        });
        return signUpButton;
    }

    TileButton ExitButton() {
        TileButton exitButton = new TileButton();
        exitButton.setText("Exit");
        exitButton.setTileLocation(10, 10);
        exitButton.setTileSize(4, 2);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        return exitButton;
    }

}
