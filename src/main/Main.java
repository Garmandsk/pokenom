package main;

import javax.swing.*;
import java.util.Objects;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        GamePanel gameP = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pokénom");
        new Main().setIcon();

        window.add(gameP);
        gameP.config.loadConfig();
        if (gameP.fullScreenOn) window.setUndecorated(true);

        window.pack();
        window.setVisible(true);

        window.setLocationRelativeTo(null);
        gameP.setUpGame();
        gameP.startGameThread();
    }
    public void setIcon(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("player/boy_down_1.png")));
        window.setIconImage(icon.getImage());
    }
}
