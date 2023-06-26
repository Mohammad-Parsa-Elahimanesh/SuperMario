package frontend.menu.shop;

import backend.Manager;
import backend.Saleable;
import backend.User;
import frontend.Massage;
import frontend.tile.TileButton;

import java.awt.*;

public class AD extends TileButton {
    private final transient Manager manager = Manager.getInstance();
    boolean sold = false;
    Saleable sales;

    AD(Saleable sales, User user) {
        setTileSize(manager.column / Shop.column, manager.row / Shop.row);
        this.sales = sales;
        for (String sold : user.bought)
            if (sales.getName().equals(sold))
                this.sold = true;
        setToolTipText("<html>" + sales.getName() + "<br>" + sales.getDescription() + "<br>" + sales.getCost() + "</html>");
        addActionListener(e -> {
            if (sold)
                new Massage("you have this since before !");
            else if (manager.CurrentUser().coin < sales.getCost())
                new Massage("you don't have enough coins :/");
            else {
                sold = true;
                manager.CurrentUser().buy(sales);
                manager.CurrentUser().coin -= sales.getCost();
                new Massage("you buy this successfully !");
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(sales.getImage(), getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2, null);
    }

}
