package backend.block.flag;

import backend.Manager;
import backend.block.Block;
import backend.gamePlay.Section;

public class Flag extends Block {
    final FlagPicture flagPicture;

    public Flag(double x, double y, Section section) {
        super(1, 12, x, y);
        flagPicture = new FlagPicture(x+0.5, y);
        section.Add(flagPicture);
        section.Add(this);
    }

    @Override
    public void Update() {
        if (isIntersect(Manager.getInstance().CurrentMario())) {
            flagPicture.goUp();
            if (flagPicture.Y + flagPicture.H + 1.5 >= Y + H)
                Manager.getInstance().CurrentMario().nextASAP = true;
        }
        super.Update();
    }

    @Override
    protected String getImageName() {
        return "rod.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }
}
