package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;
import ui.UI;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    /* Screen Settings */
    final int originalTileSize = 16;
    final int scale = 4;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public UI ui = new UI(this);
    /* ===== */

    KeyHandler keyH = new KeyHandler();
    public Thread gameThread;
    double FPS = 60;

    /* World Settings */
    public final int maxWorldCol = 50, maxWorldRow = 50;
    public final int maxWorldWidth = maxWorldCol * tileSize;
    public final int maxWorldHeight = maxWorldRow * tileSize;
    /* ===== */

    /* Player Settings */
//    int playerX = 100;
//    int playerY = 100;
//    int playerSpeed = 5;

    public Player player = new Player(this, keyH);
    /* ===== */

    /* Tile Settings */
    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    /* ===== */

    /* Object Settings */
    public SuperObject[] obj = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);
    /* ===== */

    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true); // better render performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        aSetter.setObject();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @Override
//    public void run() {
//
//        double drawInterval = 1000000000d/FPS;
//        double nextDrawTime = drawInterval + System.nanoTime();
//
//        while(gameThread != null){
//            System.out.println("Key Up Pressed: " + keyH.upPressed);
//            /* Update */
//            update();
//            /* ===== */
//
//            /* Paint */
//            repaint();
//            /* ===== */
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime /= 1000000;
//
//                if (remainingTime < 0){
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
//                System.out.printf("FPS: %d\n", drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        tileM.draw(g2d); // Tile
        for (int i = 0; i < obj.length; i++){
            if (obj[i] != null){
                obj[i].draw(g2d, this);
            }
        }
        player.draw(g2d); // Player
        ui.draw(g2d); // UI
        g2d.dispose();
    }
}
