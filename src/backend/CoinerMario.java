package backend;

public class CoinerMario extends Mario {
    @Override
    int getCoinRange() {
        return 3;
    }

    public int getCost() {
        return 30;
    }

    public String getName() {
        return "Coiner Mario";
    }

    public String getDescription() {
        return "More Coins with just Coiner Mario !";
    }

    @Override
    public String getImageName() {
        return "CoinerMario.png";
    }
}
