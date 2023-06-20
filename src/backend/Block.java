package backend;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Block {
    final static double eps = 0.1;

    public double X, W, H, Y;
    transient Map<String, Image> images = new HashMap<String, Image>();

    Block() {
    }

    boolean Neighbor(Block other) {
        double x = DistanceHorizontal(other);
        double y = DistanceVertical(other);
        return Math.min(x, y) < 0 && Math.max(x, y) == 0;
    }

    Boolean Side(Block other, Direction side) {
        if (isIntersect(other))
            return false;
        if (side.isHorizontal())
            return DistanceVertical(other) < 0 &&
                    ((side == Direction.Left && other.X + other.W <= X) || (side == Direction.Right && X + W <= other.X));
        else
            return DistanceHorizontal(other) < 0 &&
                    ((side == Direction.Down && other.Y + other.H <= Y) || (side == Direction.Up && Y + H <= other.Y));
    }

    boolean Neighbor(Block other, Direction direction) {
        return Neighbor(other) && Side(other, direction);
    }

    public boolean isIntersect(Block other) {
        return DistanceHorizontal(other) < 0 && DistanceVertical(other) < 0;
    }

    double ManhattanDistance(Block other) {
        return Math.max(0, DistanceHorizontal(other)) + Math.max(0, DistanceVertical(other));
    }

    double Distance(Block other) {
        return Math.sqrt(Math.pow(Math.max(0, DistanceHorizontal(other)), 2)
                + Math.pow(Math.max(0, DistanceVertical(other)), 2));
    }

    double DistanceVertical(Block other) {
        return Math.max(-eps, Math.max(Y, other.Y) - Math.min(Y + H, other.Y + other.H));
    }

    double DistanceHorizontal(Block other) {
        return Math.max(-eps, Math.max(X, other.X) - Math.min(X + W, other.X + other.W));
    }

    public void setShape(double w, double h, double x, double y) {
        W = w;
        H = h;
        X = x;
        Y = y;
    }

    abstract String getImageName();

    public Image getImage() {
        if (!images.containsKey(getImageName())) {
            try {
                images.put(getImageName(), ImageIO.read(new File("Images\\" + getImageName())));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return images.get(getImageName());
    }

    abstract boolean Pushed(Direction D);

    abstract void Intersect(Block block);

    public void Draw(Graphics g, int cameraLeftLine) {
        if (cameraLeftLine <= Manager.getInstance().w * X || (X + W - Manager.getInstance().column) * Manager.getInstance().w <= cameraLeftLine) {
            g.drawImage(getImage(), (int) (Manager.getInstance().w * X - cameraLeftLine), (int) (Manager.getInstance().H - Manager.getInstance().h * (Y + H)), (int) (W * Manager.getInstance().w), (int) (H * Manager.getInstance().h), null);
        }
    }


    void Update() {
    }

    @Override
    public String toString() {
        return "Block " + getImageName() + " {" +
                "X=" + X +
                ", W=" + W +
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
