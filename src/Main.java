import backend.Manager;
import frontend.EnterPage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Manager.Start();
        new EnterPage();
    }
}