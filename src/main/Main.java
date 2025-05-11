package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Pokenom");
        GamePanel gameP = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(gameP);
        window.pack();
        window.setVisible(true);

        window.setLocationRelativeTo(null);
        gameP.setUpGame();
        gameP.startGameThread();
    }
}
