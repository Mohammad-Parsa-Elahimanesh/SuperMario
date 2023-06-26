package frontend.login;

import backend.Manager;
import backend.User;
import frontend.Massage;
import frontend.tile.TileButton;
import frontend.tile.TileTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage extends JFrame {
    TileTextField userName = UserNameField(), password = PasswordField();
    TileButton enter = Enter(), back = Back();

    SignUpPage() {
        super();
        JPanel panel = new JPanel(null);
        setUndecorated(true);
        setSize(Manager.getInstance().W, Manager.getInstance().H);
        panel.add(userName);
        panel.add(password);
        panel.add(enter);
        panel.add(back);
        add(panel);
        setVisible(true);
    }

    TileTextField UserNameField() {
        TileTextField userNameField = new TileTextField();
        userNameField.setTileLocation(10, 5);
        userNameField.setTileSize(4, 1);
        userNameField.setText("User Name");
        return userNameField;
    }

    TileTextField PasswordField() {
        TileTextField passwordField = new TileTextField();
        passwordField.setTileLocation(10, 7);
        passwordField.setTileSize(4, 1);
        passwordField.setText("Password");
        return passwordField;
    }

    TileButton Enter() {
        TileButton enter = new TileButton();
        enter.setText("Enter");
        enter.setTileLocation(10, 9);
        enter.setTileSize(4, 1);
        enter.addActionListener(e -> {
            for (User user : Manager.getInstance().superMario.users)
                if (user.name.equals(userName.getText())) {
                    new Massage("user already exists");
                    return;
                }
            User user = new User(userName.getText(), password.getText());
            Manager.getInstance().superMario.users.add(user);
            SignInPage.SignIn(user);
            dispose();
        });
        return enter;
    }

    TileButton Back() {
        TileButton back = new TileButton();
        back.setText("Back");
        back.setTileLocation(10, 11);
        back.setTileSize(4, 1);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EnterPage();
                dispose();
            }
        });
        return back;
    }
}
