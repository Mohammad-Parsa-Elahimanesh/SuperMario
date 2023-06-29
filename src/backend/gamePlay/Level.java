package backend.gamePlay;

import backend.block.mario.Mario;

public class Level {
    public Section[] sections;

    Level(int level, Mario mario) {
        sections = new Section[4];
        for (int i = 0; i < 4; i++) {
            try {
                sections[i] = new Section(level, i, mario);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
