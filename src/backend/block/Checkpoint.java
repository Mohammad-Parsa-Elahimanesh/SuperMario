package backend.block;


import backend.Manager;
import backend.gamePlay.Game;
import frontend.menu.game.CheckpointMenu;

public class Checkpoint extends Block {
    public double spendTime;
    boolean active = true;
    private double cooldown = 0.0;

    public Checkpoint(double x, double y) {
        super(1, 3, x, y);
    }

    public void Destroy() {
        Manager.getInstance().currentSection().getCoin(Manager.getInstance().currentSection().progressRisk() / 4);
        Delete();
        Continue();
    }

    public void Save() {
        active = false;
        spendTime = Manager.getInstance().currentSection().getSpentTime();
        Manager.getInstance().currentSection().getCoin(-Manager.getInstance().currentSection().progressRisk());
        Manager.getInstance().currentSection().savedCheckpoints.add(this);
        Continue();
    }

    public void Continue() {
        Manager.getInstance().currentSection().timer.start();
    }

    @Override
    public void Update() {
        cooldown = Math.max(cooldown - Game.delay, 0.0);
        if (isIntersect(Manager.getInstance().currentMario()) && cooldown == 0.0 && active) {
            cooldown = 3;
            Manager.getInstance().currentMario().reset();
            Manager.getInstance().currentSection().timer.stop();
            new CheckpointMenu(this);
        }
        super.Update();
    }

    @Override
    protected boolean doesGravityAffects() {
        return true;
    }

    @Override
    protected String getImageName() {
        return "checkpoint.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }
}
