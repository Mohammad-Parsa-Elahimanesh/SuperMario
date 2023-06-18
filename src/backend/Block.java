package backend;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Block {
    final static double eps = 1e-4;
    public double X, W, H, Y;
    transient Image image;

    Block() {
    }

    static boolean Neighbor(Block first, Block second) {
        return Distance(first, second) <= eps;
    }

    static boolean Neighbor(Block first, Block second, Direction d) {
        if (!Neighbor(first, second))
            return false;
        switch (d) {
            case Left -> {
                return Math.abs(second.X + second.W - first.X) <= eps;
            }
            case Up -> {
                return Math.abs(first.Y + first.H - second.Y) <= eps;
            }
            case Right -> {
                return Math.abs(first.X + first.W - second.X) <= eps;
            }
            case Down -> {
                return Math.abs(second.Y + second.H - first.Y) <= eps;
            }
        }
        return false;
    }

    public static boolean Intersect(Block first, Block second) {
        return Distance(first, second) == 0;
    }

    static double Distance(Block first, Block second) {
        double[][] d = new double[2][2];
        d[0][0] = Math.max(first.X, second.X);
        d[0][1] = Math.min(first.X + first.W, second.X + second.W);
        d[1][0] = Math.max(first.Y, second.Y);
        d[1][1] = Math.min(first.Y + first.H, second.Y + second.H);
        return Math.sqrt(Math.pow(Math.max(0, d[0][0] - d[0][1] + eps), 2) + Math.pow(Math.max(0, d[1][0] - d[1][1] + eps), 2));
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


    void Update() {
    }

    enum Direction {
        Left,
        Up,
        Right,
        Down;


        public static Direction Opposite(Direction direction) {
            return switch (direction) {
                case Left -> Right;
                case Right -> Left;
                case Up -> Down;
                case Down -> Up;
            };
        }
    }
}
