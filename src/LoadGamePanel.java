import javax.swing.*;
import java.awt.*;

public class LoadGamePanel extends JPanel {
    final int indexGame;
    final PlayMenu playMenu;

    LoadGamePanel(int indexGameInit, PlayMenu playMenuInit) {
        super();
        indexGame = indexGameInit;
        playMenu = playMenuInit;
        setLayout(new BorderLayout());
        TileButton loadGame = new TileButton();
        loadGame.setText(Game.State(Main.CurrentUser().game[indexGame]));
        loadGame.setTileSize(8, 12);
        loadGame.setTileLocation(0, 0);
        loadGame.addActionListener(e -> {
            playMenu.dispose();
            Main.CurrentUser().RunGame(indexGame);
        });

        TileButton clear = new TileButton();
        clear.setTileSize(8, 4);
        clear.setTileLocation(0, 12);
        clear.setText("Clear");
        clear.addActionListener(e -> {
            Main.CurrentUser().game[indexGame] = null;
            loadGame.setText(Game.State(null));
        });


        add(loadGame);
        JComponent clearComponent = new JComponent() {
        };
        add(clearComponent);
        clearComponent.add(clear);
    }

}
