package backend;

public class KillerMario extends Mario {
    @Override
    int getShotSpeed() {
        return 4;
    }

    public int getCost() {
        return 15;
    }

    public String getName() {
        return "Killer Mario";
    }

    public String getDescription() {
        return "Kill enemies faster than drinking a glass of water !";
    }

    @Override
    public String getImageName() {
        return "KillerMario.png";
    }
}
