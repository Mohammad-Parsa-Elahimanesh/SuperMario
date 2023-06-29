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
        Manager.getInstance().CurrentSection().coins += Manager.getInstance().CurrentSection().ProgressRisk() / 4;
        Delete();
        Continue();
    }

    public void Save() {
        active = false;
        spendTime = Manager.getInstance().CurrentSection().spentTime;
        Manager.getInstance().CurrentSection().coins -= Manager.getInstance().CurrentSection().ProgressRisk();
        Manager.getInstance().CurrentSection().savedCheckpoints.add(this);
        Continue();
    }

    public void Continue() {
        Manager.getInstance().CurrentGame().timer.start();
    }

    @Override
    public void Update() {
        cooldown = Math.max(cooldown - Game.delay, 0.0);
        if (isIntersect(Manager.getInstance().CurrentMario()) && cooldown == 0.0 && active) {
            cooldown = 3;
            Manager.getInstance().CurrentMario().reset();
            Manager.getInstance().CurrentGame().timer.stop();
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
