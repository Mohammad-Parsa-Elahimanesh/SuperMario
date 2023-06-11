import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class EDS {
    static final String PATH_NAME = "data.txt";
    Scanner scanner;
    FileWriter writer;
    int tabs = 0;

    EDS(boolean reader) throws IOException {
        if (reader)
            scanner = new Scanner(Files.readString(Path.of(PATH_NAME)));
        else
            writer = new FileWriter(PATH_NAME);
    }

    void Write() throws IOException {
        writer.write("\n");
        for (int i = 0; i < tabs; i++)
            writer.write("   ");
    }

    void Write(int a) throws IOException {
        Write();
        writer.write(((Integer) a).toString());
    }

    void Write(String s) throws IOException {
        Write();
        writer.write(s);
    }

    String Read(String __) {
        String s = "";
        while (s.equals(""))
            s = scanner.nextLine().trim();
        return s;
    }

    void Write(String[] list) throws IOException {
        Write(list.length);
        tabs++;
        for (String t : list)
            Write(t);
        tabs--;
    }

    String[] Read(String[] __) {
        String[] list = new String[scanner.nextInt()];
        for (int i = 0; i < list.length; i++)
            list[i] = Read((String) null);
        return list;
    }

    void Write(Block[] list) throws IOException {
        Write(list.length);
        tabs++;
        for (Block t : list)
            Write(t);
        tabs--;
    }

    Block[] Read(Block[] __) {
        Block[] list = new Block[scanner.nextInt()];
        for (int i = 0; i < list.length; i++)
            list[i] = Read(list[i]);
        return list;
    }

    void Write(User[] list) throws IOException {
        Write(list.length);
        tabs++;
        for (User t : list)
            Write(t);
        tabs--;
    }

    User[] Read(User[] __) {
        User[] list = new User[scanner.nextInt()];
        for (int i = 0; i < list.length; i++)
            list[i] = Read(list[i]);
        return list;
    }

    void Write(Game.Level[] list) throws IOException {
        tabs++;
        for (Game.Level level : list)
            Write(level);
        tabs--;
    }

    void Read(Game.Level[] levels) {
        for (Game.Level level : levels) Read(level);
    }

    void Write(Game.Level.Section[] sections) throws IOException {
        tabs++;
        for (Game.Level.Section section : sections)
            Write(section);
        tabs--;
    }

    void Read(Game.Level.Section[] sections) {
        for (Game.Level.Section section : sections) Read(section);
    }

    void Write(Game.Level level) throws IOException {
        Write(level.sections);
    }

    void Read(Game.Level level) {
        Read(level.sections);
    }

    void Write(Game.Level.Section section) throws IOException {
        Write(section.spentTimeMS);
        Write(section.blocks.toArray(new Block[0]));
    }

    void Read(Game.Level.Section section) {
        section.spentTimeMS = scanner.nextInt();
        section.blocks = new ArrayList<>(Arrays.asList(Read(section.blocks.toArray(new Block[0]))));
    }

    void Write(Block block) throws IOException {
        if (block == null) {
            Write("null");
            return;
        }
        Write(block.getImageName());
        Write(block.X);
        Write(block.Y);
        Write(block.H);
        Write(block.W);
        if (block instanceof Mario)
            Write((Mario) block);
        else if (block instanceof KillerPlant)
            Write((KillerPlant) block);
    }

    Block Read(Block __) {
        String first = Read((String) null);
        if (first.equals("null"))
            return null;
        Block block;
        if (new Brick().getImageName().equals(first))
            block = new Brick();
        else if (new Coin().getImageName().equals(first))
            block = new Coin();
        else if (new CoinerMario().getImageName().equals(first))
            block = new CoinerMario();
        else if (new FasterMario().getImageName().equals(first))
            block = new FasterMario();
        else if (new JumperMario().getImageName().equals(first))
            block = new JumperMario();
        else if (new KillerMario().getImageName().equals(first))
            block = new KillerMario();
        else if (new KillerPlant(null, 0).getImageName().equals(first))
            block = new KillerPlant(null, 0);
        else if (new Pipe().getImageName().equals(first))
            block = new Pipe();
        else if (new SecretPipe().getImageName().equals(first))
            block = new SecretPipe();
        else if (new SimpleMario().getImageName().equals(first))
            block = new SimpleMario();
        else
            block = new Sky();
        block.X = scanner.nextInt();
        block.Y = scanner.nextInt();
        block.H = scanner.nextInt();
        block.W = scanner.nextInt();
        if (block instanceof Mario)
            Read((Mario) block);
        else if (block instanceof KillerPlant)
            Read((KillerPlant) block);
        return block;
    }

    void Write(Mario mario) throws IOException {
        tabs++;
        Write(mario.jump);
        Write(mario.power);
        Write(mario.heart);
        tabs--;
    }

    void Read(Mario mario) {
        mario.jump = scanner.nextInt();
        mario.power = scanner.nextInt();
        mario.heart = scanner.nextInt();
    }

    void Write(KillerPlant killerPlant) throws IOException {
        tabs++;
        Write(killerPlant.base);
        Write(killerPlant.home);
        tabs--;
    }

    void Read(KillerPlant killerPlant) {
        killerPlant.base = scanner.nextInt();
        killerPlant.home = (Pipe) Read(new Sky());
    }

    void Write(Game.Difficulty difficulty) throws IOException {
        if (difficulty == null)
            Write("null");
        else
            Write(difficulty.toString());
    }

    Game.Difficulty Read(Game.Difficulty __) {
        String S = Read((String) null);
        if (Objects.equals(S, "null"))
            return null;
        for (Game.Difficulty difficulty : Game.Difficulty.values())
            if (difficulty.toString().equals(S))
                return difficulty;
        System.err.println("Error");
        return null;
    }

    void Write(Game game) throws IOException {
        if (game == null) {
            Write("null");
            return;
        }
        Write();
        Write(game.levelNumber);
        Write(game.sectionNumber);
        Write(game.score);
        Write(game.coins);
        Write(game.difficulty);
        Write((Block) game.mario);
        Write(game.levels);
    }

    Game Read(Game __) {
        String S = Read((String) null);
        if (S.equals("null"))
            return null;
        Game game = new Game(true);
        game.levelNumber = Integer.parseInt(S);
        game.sectionNumber = scanner.nextInt();
        game.score = scanner.nextInt();
        game.coins = scanner.nextInt();
        game.difficulty = Read(game.difficulty);
        game.mario = (Mario) Read((Block) game.mario);
        Read(game.levels);
        for (Game.Level level : game.levels)
            for (Game.Level.Section section : level.sections)
                for (int i = 0; i < section.blocks.size(); i++)
                    if (section.blocks.get(i) instanceof Mario)
                        section.blocks.set(i, game.mario);
        return game;
    }

    void Write(User user) throws IOException {
        if (user == null) {
            Write("null");
            return;
        }
        Write();
        Write(user.name);
        Write(user.password);
        Write(user.coin);
        Write(user.maxRating);
        Write(user.bought.toArray(new String[0]));
        tabs++;
        for (int i = 0; i < 3; i++)
            Write(user.game[i]);
        tabs--;
    }

    User Read(User __) {
        String S = Read((String) null);
        if (S.equals("null"))
            return null;
        User user = new User(S, Read((String) null));
        user.coin = scanner.nextInt();
        user.maxRating = scanner.nextInt();
        user.bought = new ArrayList<>(Arrays.asList(Read(new String[0])));
        for (int i = 0; i < 3; i++)
            user.game[i] = Read((Game) null);
        return user;
    }

    void Write(SuperMario superMario) throws IOException {
        Write(superMario.users.toArray(new User[0]));
    }

    void Read(SuperMario superMario) {
        superMario.users = new ArrayList<>(Arrays.asList(Read((User[]) null)));
    }

    void flush() throws IOException {
        writer.flush();
    }
}
