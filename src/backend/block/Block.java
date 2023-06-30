package backend.block;

import backend.Manager;
import backend.gamePlay.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public abstract class Block {
    final static double G = 35;
    final static double MAX_MOVE = 0.8;
    final static double eps = 0.01;
    static Map<String, BufferedImage> images = new HashMap<>();
    public double X, W, H, Y;
    protected double vx = 0, vy = 0;

    protected Block(double w, double h, double x, double y) {
        setShape(w, h, x, y);
    }

    protected boolean Neighbor(Block other) {
        double x = DistanceHorizontal(other);
        double y = DistanceVertical(other);
        return min(x, y) < 0 && Math.max(x, y) == 0;
    }

    public void Delete() {
        Manager.getInstance().currentSection().del(this);
    }

    protected Boolean Side(Block other, Direction side) {
        if (isIntersect(other))
            return false;
        if (side.isHorizontal())
            return DistanceVertical(other) < 0 &&
                    ((side == Direction.Left && other.X + other.W <= X) || (side == Direction.Right && X + W <= other.X));
        else
            return DistanceHorizontal(other) < 0 &&
                    ((side == Direction.Down && other.Y + other.H <= Y) || (side == Direction.Up && Y + H <= other.Y));
    }

    protected boolean Neighbor(Block other, Direction direction) {
        return Neighbor(other) && Side(other, direction);
    }

    public boolean isIntersect(Block other) {
        return DistanceHorizontal(other) < 0 && DistanceVertical(other) < 0 && other != this;
    }

    double ManhattanDistance(Block other) {
        return Math.max(0, DistanceHorizontal(other)) + Math.max(0, DistanceVertical(other));
    }

    protected double Distance(Block other) {
        return Math.sqrt(Math.pow(Math.max(0, DistanceHorizontal(other)), 2)
                + Math.pow(Math.max(0, DistanceVertical(other)), 2));
    }

    double DistanceVertical(Block other) {
        return Math.max(-eps, Math.max(Y, other.Y) - min(Y + H, other.Y + other.H));
    }

    double DistanceHorizontal(Block other) {
        return Math.max(-eps, Math.max(X, other.X) - min(X + W, other.X + other.W));
    }

    public void setShape(double w, double h, double x, double y) {
        W = w;
        H = h;
        X = x;
        Y = y;
    }

    protected abstract String getImageName();

    private BufferedImage getMirroredImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage mirrorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                int p = originalImage.getRGB(lx, y);
                mirrorImage.setRGB(rx, y, p);
            }
        }
        return mirrorImage;
    }

    public Image getImage() {
        if (!images.containsKey(getImageName())) {
            try {
                images.put(getImageName(), ImageIO.read(new File("Images\\" + getImageName())));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (vx < 0 && !images.containsKey("mirrored" + getImageName())) {
            images.put("mirrored" + getImageName(), getMirroredImage(images.get(getImageName())));
        }
        return images.get((vx < 0 ? "mirrored" : "") + getImageName());
    }

    protected double Push(Direction direction) {
        double canMove = MAX_MOVE;
        for (Block block : Manager.getInstance().currentSection().blocks)
            if (Neighbor(block, direction)) {
                if (!block.Pushed(direction.Opposite()))
                    canMove = 0;
            } else if (Side(block, direction))
                canMove = min(canMove, ManhattanDistance(block));
        return canMove;
    }

    public int getDirection() {
        return vx < 0 ? -1 : 1;
    }

    protected abstract boolean Pushed(Direction D);

    protected boolean doesGravityAffects() {
        return false;
    }

    protected void Intersect(Block block) {
    }

    public void Draw(Graphics g, int cameraLeftLine) {
        if (cameraLeftLine <= Manager.getInstance().SINGLE_BLOCK_WIDTH * X || (X + W - Manager.getInstance().COLUMN) * Manager.getInstance().SINGLE_BLOCK_WIDTH <= cameraLeftLine) {
            g.drawImage(getImage(), (int) (Manager.getInstance().SINGLE_BLOCK_WIDTH * X - cameraLeftLine), (int) (Manager.getInstance().SCREEN_HEIGHT - Manager.getInstance().SINGLE_BLOCK_HEIGHT * (Y + H)), (int) (W * Manager.getInstance().SINGLE_BLOCK_WIDTH), (int) (H * Manager.getInstance().SINGLE_BLOCK_HEIGHT), null);
        }
    }

    public void Update() {

        if (doesGravityAffects()) {
            if (Push(Direction.Down) > 0)
                vy -= Game.delay * G;
            else if (vy < 0)
                vy = 0;
        }

        if (Math.abs(vx) < eps && vx != 0) vx = eps * getDirection();
        if (Math.abs(vy) < eps && vy != 0) vy = eps * (vy < 0 ? -1 : 1);

        if (vx < 0) {
            double maxMove = -vx * Game.delay;
            double canMove = maxMove;
            while (canMove > 0) {
                canMove = min(maxMove, Push(Direction.Left));
                X -= canMove;
                maxMove -= canMove;
            }
            if (maxMove > 0)
                Pushed(Direction.Left);
        }
        if (vx > 0) {
            double maxMove = vx * Game.delay;
            double canMove = maxMove;
            while (canMove > 0) {
                canMove = min(maxMove, Push(Direction.Right));
                X += canMove;
                maxMove -= canMove;
            }
            if (maxMove > 0)
                Pushed(Direction.Right);
        }

        if (vy < 0) {
            double maxMove = -vy * Game.delay;
            double canMove = maxMove;
            while (canMove > 0) {
                canMove = min(maxMove, Push(Direction.Down));
                Y -= canMove;
                maxMove -= canMove;
            }
            if (maxMove > 0)
                Pushed(Direction.Down);
        }
        if (vy > 0) {
            double maxMove = vy * Game.delay;
            double canMove = maxMove;
            while (canMove > 0) {
                canMove = min(maxMove, Push(Direction.Up));
                Y += canMove;
                maxMove -= canMove;
            }
            if (maxMove > 0)
                Pushed(Direction.Up);
        }
    }

    @Override
    public String toString() {
        return "Block " + getImageName() + " {" +
                "X=" + X +
                ", SCREEN_WIDTH=" + W +
                ", H=" + H +
                ", Y=" + Y +
                '}';
    }

    public enum Direction {
        Left,
        Up,
        Right,
        Down;


        public Direction Opposite() {
            return switch (this) {
                case Left -> Right;
                case Right -> Left;
                case Up -> Down;
                case Down -> Up;
            };
        }

        public boolean isHorizontal() {
            return this == Right || this == Left;
        }
    }
}


