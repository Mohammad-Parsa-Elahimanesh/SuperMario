package backend;

public class JumperMario extends Mario {
    @Override
    int getJumpSpeed() {
        return super.getJumpSpeed()*3/2;
    }

    public int getCost() {
        return 50;
    }

    public String getName() {
        return "Jumper Mario";
    }

    public String getDescription() {
        return "Jump on the eiffel by Jumper Mario!";
    }

    @Override
    public String getImageName() {
        return "JumperMario.png";
    }
}
