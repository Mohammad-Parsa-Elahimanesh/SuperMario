package backend.gamePlay;

public class Level {
    public Section[] sections;

    Level(int level) {
        sections = new Section[4];
        for (int i = 0; i < 4; i++)
            sections[i] = new Section(level, i);
    }
}
