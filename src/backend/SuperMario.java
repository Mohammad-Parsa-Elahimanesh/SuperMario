package backend;

import java.util.ArrayList;
import java.util.List;

public class SuperMario {
    static Saleable[] saleables = {new SimpleMario(), new CoinerMario(), new JumperMario(), new KillerMario(), new FasterMario()};
    public List<User> users = new ArrayList<>();
    public User currentUser;
}
