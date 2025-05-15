package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class GamePanel extends JPanel implements Runnable{

    /* Screen Settings */
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    /* Full Screen Setting */
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2d;
    public boolean fullScreenOn;

    public UI ui = new  UI(this);
    /* ===== */

    /* Config Setting */
    public Config config = new Config(this);
    /* ===== */

    public KeyHandler keyH = new KeyHandler(this);
    public Thread gameThread;
    public double FPS = 60;

    /* World Settings */
    public final int maxWorldCol = 50, maxWorldRow = 50;
    public final int maxWorldWidth = maxWorldCol * tileSize;
    public final int maxWorldHeight = maxWorldRow * tileSize;
    /* ===== */

    /* Map Setting */
    public final int maxMap = 10;
    public int currentMap = 0;
    /* ===== */

    /* Player Settings */
//    int playerX = 100;
//    int playerY = 100;
//    int playerSpeed = 5;

    public Player player = new Player(this, keyH);
    /* ===== */

    /* Tile Settings */
    TileManager tileM = new TileManager(this);
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public CollisionChecker cChecker = new CollisionChecker(this);
    /* ===== */

    /* Sound Setting */
    public Sound music = new Sound();
    public Sound se = new Sound();
    /* ===== */

    /* NPC */
    public Entity[][] npc = new Entity[maxMap][10];
    /* ===== */

    /* Monster */
    public Entity[][] monster = new Entity[maxMap][10];
    /* ===== */

    /* Object Settings */
    public Entity[][] obj = new Entity[maxMap][20];
    public AssetSetter aSetter = new AssetSetter(this);
    /* ===== */

    ArrayList<Entity> entityList = new ArrayList<Entity>();
    public ArrayList<Entity> projectileList =  new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();

    /* Game State */
    public int gameState;
    public final int pauseState = -1;
    public final int titleState = 0;
    public final int playState = 1;
    public final int dialogueState = 2;
    public final int characterState = 3;
    public final int optionState = 4;
    public final int gameOverState = 5;
    public final int transitionState = 6;
    public final int tradeState = 7;
    /* ===== */

    /* Event */
    public EventHandler eventH = new EventHandler(this);

    private float fullScreenOffsetFactor;

    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // better render performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = playState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D)tempScreen.getGraphics();

        if (fullScreenOn) setFullScreen();
    }

    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void restart(){
        player.setDefaultValue();
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }

    public void setFullScreen() {
        // 1) Detect actual screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth2  = screenSize.width;
        screenHeight2 = screenSize.height;

        // 2) Maximize the window
        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // 3) Compute mapping factor
        fullScreenOffsetFactor = (float) screenWidth / (float) screenWidth2;
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
                drawToTempScreen();
                drawToScreen();
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

    public void playMusic(int i){
        music.setSound(i);
        music.play();
        music.loop();

    }

    public void stopMusic(){
        music.stop();

    }

    public void playSE(int i){
        se.setSound(i);
        se.play();

    }

    public void update(){
        if (gameState == playState) {

            for (int i = 0; i < iTile[1].length; i++){
                if (iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }

            player.update();

            for (int i = 0; i < npc[1].length; i++){
                if (npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }

            for (int i = 0; i < projectileList.size(); i++){
                if (projectileList.get(i) != null){
                    if (projectileList.get(i).alive == false) projectileList.remove(i);
                    else if (projectileList.get(i).alive == true) projectileList.get(i).update();
                }
            }

            for (int i = 0; i < particleList.size(); i++){
                if (particleList.get(i) != null){
                    if (particleList.get(i).alive == false) particleList.remove(i);
                    else if (particleList.get(i).alive == true) particleList.get(i).update();
                }
            }

            for (int i = 0; i < monster[1].length; i++){
                if (monster[currentMap][i] != null){
                    if (monster[currentMap][i].alive == false){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    } else if (monster[currentMap][i].alive && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }
                }
            }
        }
        if (gameState == pauseState) ;
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void drawToTempScreen(){

        /* Debug */
        long drawStart = 0;
        if (keyH.debug) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState){
            ui.draw(g2d);
        }else {
            tileM.draw(g2d); // Tile

            for (int i = 0; i < iTile[1].length; i++){
                if (iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2d);
                }
            }

            entityList.add(player);

//            entityList.addAll(Arrays.asList(npc));
            for (int i = 0; i < npc[1].length; i++){
                if (npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < projectileList.size(); i++){
                if (projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }

            for (int i = 0; i < particleList.size(); i++){
                if (particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            for (int i = 0; i < monster[1].length; i++){
                if (monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }

//            entityList.addAll(Arrays.asList(obj));
            for (int i = 0; i < obj[1].length; i++){
                if (obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

                @Override
                public boolean equals(Object obj) {
                    return false;
                }
            });

            // Draw Entities
            for (Entity entity : entityList) {
                entity.draw(g2d);
            }

            // Clear Entities
            entityList.clear();

            ui.draw(g2d); // UI

        }
        if (keyH.debug){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2d.setFont(ui.Oswald.deriveFont(Font.PLAIN, 20f));
            g2d.setColor(Color.white);
            int textX = tileSize/2;
            int textY = tileSize*7;
            final int lineHeight = tileSize/2 + 15;

            g2d.drawString("WorldX: " + player.worldX, textX, textY);
            textY += lineHeight;

            g2d.drawString("WorldY: " + player.worldY, textX, textY);
            textY += lineHeight;

            g2d.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, textX, textY);
            textY += lineHeight;

            g2d.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, textX, textY);
            textY += lineHeight;

            g2d.drawString("Draw Time: " + passed, textX, textY);

            g2d.setColor(Color.red);
            g2d.drawRect(player.screenX + player.solidArea.x, player.screenY + player.solidArea.y, player.solidArea.width, player.solidArea.height);

            for (int i = 0; i < npc[1].length; i++){
                if (npc[currentMap][i] != null){
                    int screenX = npc[currentMap][i].worldX - player.worldX + player.screenX;
                    int screenY = npc[currentMap][i].worldY - player.worldY + player.screenY;
                    g2d.drawRect(screenX + npc[currentMap][i].solidArea.x, screenY + npc[currentMap][i].solidArea.y, npc[currentMap][i].solidArea.width, npc[currentMap][i].solidArea.height);
                }
            }

            for (int i = 0; i < monster[1].length; i++){
                if (monster[currentMap][i] != null){
                    int screenX = monster[currentMap][i].worldX - player.worldX + player.screenX;
                    int screenY = monster[currentMap][i].worldY - player.worldY + player.screenY;
                    g2d.drawRect(screenX + monster[currentMap][i].solidArea.x, screenY + monster[currentMap][i].solidArea.y, monster[currentMap][i].solidArea.width, monster[currentMap][i].solidArea.height);
                }
            }

            for (int i = 0; i < obj[1].length; i++){
                if (obj[currentMap][i] != null){
                    int screenX = obj[currentMap][i].worldX - player.worldX + player.screenX;
                    int screenY = obj[currentMap][i].worldY - player.worldY + player.screenY;
                    g2d.drawRect(screenX + obj[currentMap][i].solidArea.x, screenY + obj[currentMap][i].solidArea.y, obj[currentMap][i].solidArea.width, obj[currentMap][i].solidArea.height);
                }
            }

            eventH.render(g2d);
        }

    }
}
