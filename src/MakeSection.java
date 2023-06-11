public class MakeSection {
    static void AddBrick(Game.Level.Section S, int W, int H, int X, int Y) {
        for (int i = X; i < X + W; i++)
            for (int j = Y; j < Y + H; j++) {
                Brick brick = new Brick();
                brick.setShape(1, 1, i, j);
                S.Add(brick);
            }
    }

    static void AddPipe(Game.Level.Section S, int X, int H) {
        Pipe pipe = new Pipe();
        pipe.setShape(2, H, X, 0);
        S.Add(pipe);
    }

    static void AddPipeWithKillerPlant(Game.Level.Section S, int X, int H) {
        AddPipeWithKillerPlant(S, X, H, 0);
    }

    static void AddPipeWithKillerPlant(Game.Level.Section S, int X, int H, int base) {
        Pipe pipe = new Pipe();
        pipe.setShape(2, H, X, 0);
        KillerPlant plant = new KillerPlant(pipe, base);
        S.Add(pipe);
        S.Add(plant);
    }

}
