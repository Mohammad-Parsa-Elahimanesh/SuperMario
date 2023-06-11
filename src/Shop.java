import javax.swing.*;
import java.awt.*;

public class Shop extends JFrame {
    final static int row = 2, column = 3;

    Shop() {
        super();
        JPanel panel = new JPanel(new GridLayout(row, column));
        setUndecorated(true);
        setSize(Manager.getInstance().W, Manager.getInstance().H);
        JPanel[][] gridPanel = new JPanel[row][column];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++) {
                gridPanel[i][j] = new JPanel(new GridLayout(1, 1));
                panel.add(gridPanel[i][j]);
            }
        gridPanel[0][0].add(ShowCoin());
        gridPanel[1][0].add(Back());
        gridPanel[0][1].add(new AD(new CoinerMario(), Manager.getInstance().CurrentUser()));
        gridPanel[0][2].add(new AD(new JumperMario(), Manager.getInstance().CurrentUser()));
        gridPanel[1][1].add(new AD(new FasterMario(), Manager.getInstance().CurrentUser()));
        gridPanel[1][2].add(new AD(new KillerMario(), Manager.getInstance().CurrentUser()));
        add(panel);
        setVisible(true);
    }

    TileLabel ShowCoin() {
        TileLabel showCoin = new TileLabel();
        showCoin.setTileSize(Manager.getInstance().column / column, Manager.getInstance().row / row);
        showCoin.setText("Coins: " + Manager.getInstance().CurrentUser().coin);

        Timer t = new Timer(3000, e -> {
            showCoin.setText("Coins: " + Manager.getInstance().CurrentUser().coin);
        });
        t.start();
        showCoin.setFont(new Font("Arial", Font.PLAIN, 50));
        return showCoin;
    }

    TileButton Back() {
        TileButton back = new TileButton();
        back.setText("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 40));
        back.setTileSize(Manager.getInstance().column / column, Manager.getInstance().row / row);
        back.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        return back;
    }
}
