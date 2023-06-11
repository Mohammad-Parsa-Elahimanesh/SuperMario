import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Block {
    int W, H, X, Y;
    Image image;

    Block() {
    }

    static boolean Neighbor(Block first, Block second) {
        return Distance(first, second) == 1;
    }

    static boolean Neighbor(Block first, Block second, Direction d) {
        if (!Neighbor(first, second))
            return false;
        switch (d) {
            case Left -> {
                return second.X + second.W == first.X;
            }
            case Up -> {
                return first.Y + first.H == second.Y;
            }
            case Right -> {
                return first.X + first.W == second.X;
            }
            case Down -> {
                return second.Y + second.H == first.Y;
            }
        }
        System.err.println("ERROR");
        return false;
    }

    static boolean Intersect(Block first, Block second) {
        return Distance(first, second) == 0;
    }

    static int Distance(Block first, Block second) {
        int[][] d = new int[2][2];
        d[0][0] = Math.max(first.X, second.X);
        d[0][1] = Math.min(first.X + first.W, second.X + second.W);
        d[1][0] = Math.max(first.Y, second.Y);
        d[1][1] = Math.min(first.Y + first.H, second.Y + second.H);
        return Math.max(0, d[0][0] - d[0][1] + 1) + Math.max(0, d[1][0] - d[1][1] + 1);
    }

    void setShape(int w, int h, int x, int y) {
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

    void Draw(Graphics g, int cameraLeftLine) {
        if (cameraLeftLine <= X || X + W <= Main.column + cameraLeftLine)
            g.drawImage(getImage(), Main.w * (X - cameraLeftLine), Main.H - Main.h * (Y + H), W * Main.w, H * Main.h, null);
    }

    void DrawBackground(Graphics g, int cameraLeftLine) {
        g.setColor(Color.CYAN);
        Sky sky = new Sky();
        sky.setShape(W, H, X, Y);
        sky.Draw(g, cameraLeftLine);
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
