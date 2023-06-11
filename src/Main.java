import java.io.IOException;
import java.util.List;

public class Main {
    static SuperMario superMario;

    public static String CommaSeparatedList(List list) {
        StringBuilder ret = new StringBuilder(" ");
        for (int i = 0; i < list.size(); i++) {
            ret.append(list.get(i));
            if (i + 1 != list.size())
                ret.append(", ");
        }
        return ret.toString();
    }

    static User CurrentUser() {
        return superMario.currentUser;
    }

    static Game CurrentGame() {
        return CurrentUser().game[CurrentUser().currentGameIndex];
    }

    static Game.Level.Section CurrentSection() {
        return CurrentGame().levels[CurrentGame().levelNumber].sections[CurrentGame().sectionNumber];
    }

    public static void main(String[] args) throws IOException {

        superMario = new SuperMario();
        EDS eds = new EDS(true);
        eds.Read(superMario);
        new EnterPage();
    }

    final static int W = 1536, column = 24, w = W / column;


    final static int H = 864, row = 16, h = H / row;

    /* Users:
        Mohammad Parsa
        1383

        AP
        AP

        A
        A A

        B
        B_B

        C
        C
    */
}