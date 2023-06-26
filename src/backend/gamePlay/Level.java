package backend.gamePlay;

public class Level {
    public Section[] sections;

    Level(int level) {
        if (level == 0)
            sections = new Section[]{new Section(0, 0), new Section(0, 1), new Section(0, 2)};
    }
}
