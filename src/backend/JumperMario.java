package backend;

public class JumperMario extends Mario {
    @Override
    int getJumpHeight() {
        return 9;
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
