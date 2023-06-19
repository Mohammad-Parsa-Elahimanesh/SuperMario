package backend;

import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Block {
    final static double eps = 0.1;

    public double X, W, H, Y;
    transient Image image;

    Block() {
    }

    static boolean Neighbor(Block first, Block second) {
        double x = DistanceHorizontal(first, second);
        double y = DistanceVertical(first, second);
        return Math.min(x, y) < 0 && Math.max(x, y) == 0;
    }

    static Boolean Side(Block first, Block second, Direction side) {
        if(Intersect(first, second))
            return false;
        if(side.isHorizontal())
            return DistanceVertical(first, second) < 0 &&
                    ((side == Direction.Left && second.X + second.W <= first.X) || (side == Direction.Right && first.X + first.W <= second.X));
        else
            return DistanceHorizontal(first, second) < 0 &&
                    ((side == Direction.Down && second.Y + second.H <= first.Y) || (side == Direction.Up && first.Y + first.H <= second.Y));
    }

    static boolean Neighbor(Block first, Block second, Direction direction) {
        return Neighbor(first, second) && Side(first, second, direction);
    }

    public static boolean Intersect(Block first, Block second) {
        return DistanceHorizontal(first, second) < 0 && DistanceVertical(first, second) < 0;
    }

    static double DistanceManhatani(Block first, Block second) {
        return Math.max(0, DistanceHorizontal(first, second)) + Math.max(0, DistanceVertical(first, second));
    }

    static double Distance(Block first, Block second) {
        return Math.sqrt(Math.pow(Math.max(0, DistanceHorizontal(first, second)) , 2)
                + Math.pow(Math.max(0, DistanceVertical(first, second)), 2));
    }

    static double DistanceVertical(Block first, Block second) {
        return Math.max(-eps, Math.max(first.Y, second.Y)-Math.min(first.Y + first.H, second.Y + second.H));
    }

    static double DistanceHorizontal(Block first, Block second) {
        return Math.max(-eps, Math.max(first.X, second.X)-Math.min(first.X + first.W, second.X + second.W));
    }

    public void setShape(double w, double h, double x, double y) {
        W = w;
        H = h;
        X = x;
        Y = y;
    }

    abstract String getImageName();

    public Image getImage() {
        if (image == null) {
            try {
                image = ImageIO.read(new File("Images\\" + getImageName()));
            } catch (IOException e) {
                System.out.println(getImageName());
                e.printStackTrace();
                return null;
            }
        }
        return image;
    }

    abstract boolean Pushed(Direction D);

    abstract void Intersect(Block block);

    public void Draw(Graphics g, int cameraLeftLine) {
        if (cameraLeftLine <= X || X + W <= Manager.getInstance().column + cameraLeftLine) {
            g.drawImage(getImage(), (int) (Manager.getInstance().w * (X - cameraLeftLine)), (int) (Manager.getInstance().H - Manager.getInstance().h * (Y + H)), (int) (W * Manager.getInstance().w), (int) (H * Manager.getInstance().h), null);
        }
    }


    void Update() {}

    @Override
    public String toString() {
        return "Block "+ getImageName() + " {" +
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


        public  Direction Opposite() {
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

        public boolean isVertical() {
            return this == Down || this == Up;
        }
    }
}
