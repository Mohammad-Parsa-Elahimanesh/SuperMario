import java.util.ArrayList;
import java.util.List;

public class SuperMario {
    static Saleable[] saleables = {new SimpleMario(), new CoinerMario(), new JumperMario(), new KillerMario(), new FasterMario()};
    List<User> users = new ArrayList<>();
    User currentUser;
}
