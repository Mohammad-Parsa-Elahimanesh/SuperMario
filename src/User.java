import java.util.ArrayList;
import java.util.List;

public class User {
    final Game[] game = new Game[3];
    final String name, password;
    int coin = 0, maxRating = -1, currentGameIndex = -1;
    List<String> bought = new ArrayList<>();

    User(String name, String password) {
        this.name = name;
        this.password = password;
        buy(new SimpleMario());
    }

    List<Mario> Marios() {
        List<Mario> marios = new ArrayList<>();
        for (String have : bought)
            for (Saleable mario : SuperMario.saleables)
                if ((mario instanceof Mario) && mario.getName().equals(have))
                    marios.add((Mario) mario);
        return marios;
    }

    void buy(Saleable sold) {
        bought.add(sold.getName());
    }

    void RunGame(int index) {
        currentGameIndex = index;
        if (game[index] == null)
            game[index] = new Game();
        else
            game[index].Resume();
    }
}
