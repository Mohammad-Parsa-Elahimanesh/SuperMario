import java.awt.*;

public class AD extends TileButton {
    boolean sold = false;
    Saleable sales;

    AD(Saleable sales, User user) {
        setTileSize(Main.column / Shop.column, Main.row / Shop.row);
        this.sales = sales;
        for (String sold : user.bought)
            if (sales.getName().equals(sold))
                this.sold = true;
        setToolTipText("<html>" + sales.getName() + "<br>" + sales.getDescription() + "<br>" + sales.getCost() + "</html>");
        addActionListener(e -> {
            if (sold)
                new Massage("you have this since before !");
            else if (Main.CurrentUser().coin < sales.getCost())
                new Massage("you don't have enough coins :/");
            else {
                sold = true;
                Main.CurrentUser().buy(sales);
                Main.CurrentUser().coin -= sales.getCost();
                new Massage("you buy this successfully !");
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(sales.getImage(), getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2, null);
    }

}
