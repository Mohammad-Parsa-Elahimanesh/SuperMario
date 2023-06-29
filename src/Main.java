import backend.Manager;
import frontend.login.EnterPage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Manager.Start();
        new EnterPage();
    }
}