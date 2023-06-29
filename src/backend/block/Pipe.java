package backend.block;

import backend.Manager;
import backend.block.enemy.KillerPlant;
import backend.gamePlay.Section;

public class Pipe extends Block {
    public Pipe destination = null;

    public Pipe(double x, double h, boolean hasPlant, Section section) {
        super(2, h, x, 0);
        section.Add(this);
        if (hasPlant)
            section.Add(new KillerPlant(this));
    }

    @Override
    protected String getImageName() {
        return "Pipe.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (Neighbor(Manager.getInstance().CurrentMario(), Direction.Up) && Manager.getInstance().CurrentMario().isDirection(Direction.Down) && destination != null) {
            Manager.getInstance().CurrentMario().X = destination.X;
            Manager.getInstance().CurrentMario().Y = destination.Y + destination.H;
        }
        return false;
    }
}
