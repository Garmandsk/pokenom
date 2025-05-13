package main;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame("Pokenom");
        GamePanel gameP = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(gameP);
        gameP.config.loadConfig();
        if (gameP.fullScreenOn) window.setUndecorated(true);

        window.pack();
        window.setVisible(true);

        window.setLocationRelativeTo(null);
        gameP.setUpGame();
        gameP.startGameThread();
    }
}
