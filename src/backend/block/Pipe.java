package backend.block;

import backend.Manager;
import backend.block.enemy.KillerPlant;
import backend.gamePlay.Section;

public class Pipe extends Block {
    public Section destination = null;

    public Pipe(double x, double h, boolean hasPlant, Section section) {
        super(2, h, x, 0);
        section.add(this);
        if (hasPlant)
            section.add(new KillerPlant(this));
    }

    @Override
    protected String getImageName() {
        return "Pipe.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if(Neighbor(Manager.getInstance().currentMario(), Direction.Up) && Manager.getInstance().currentMario().isDirection(Direction.Down) && destination != null)
        {
            Manager.getInstance().currentSection().timer.stop();
            destination.mario.state = Manager.getInstance().currentMario().state;
            Manager.getInstance().currentGame().currentSection = destination;
            destination = null;
            Manager.getInstance().currentSection().timer.start();
        }

        return false;
    }
}
