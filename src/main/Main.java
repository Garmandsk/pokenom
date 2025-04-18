package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Pokenom");
        GamePanel gamePanel = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(gamePanel);
        window.pack();
        window.setVisible(true);

        window.setLocationRelativeTo(null);
        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
