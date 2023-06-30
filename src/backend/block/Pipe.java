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
        if(Neighbor(Manager.getInstance().currentMario(), Direction.Up) && Manager.getInstance().currentMario().isDirection(Direction.Down) && destination != null)
        {
            Manager.getInstance().currentMario().X = destination.X;
            Manager.getInstance().currentMario().Y = destination.Y+ destination.H;
        }
        return false;
    }
}
