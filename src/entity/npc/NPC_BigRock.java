package entity.npc;

import entity.Entity;
import main.GamePanel;
import object.obstacle.OBS_OBJ_DoorIron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class NPC_BigRock extends Entity {
    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gameP){
        super(gameP);

        type = npcType;
        name = npcName;
        direction = "down";
        speed = 2;

        solidArea.x = 6;
        solidArea.y = 16;
        solidArea.width = gameP.tileSize - (solidArea.x * 2);
        solidArea.height = gameP.tileSize - 16;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage(){
//        System.out.println("=====OldMan=====");
//        System.out.println("Solid Area X: " + this.solidArea.x);
//        System.out.println("Solid Area Y: " + this.solidArea.y);
//        System.out.println("Solid Area Width: " + this.solidArea.width);
//        System.out.println("Solid Area Height: " + this.solidArea.height);

        up1 = uTool.setUp("/npc/bigrock");
        up2 = uTool.setUp("/npc/bigrock");
        down1 = uTool.setUp("/npc/bigrock");
        down2 = uTool.setUp("/npc/bigrock");
        left1 = uTool.setUp("/npc/bigrock");
        left2 = uTool.setUp("/npc/bigrock");
        right1 = uTool.setUp("/npc/bigrock");
        right2 = uTool.setUp("/npc/bigrock");
    }

    public void setAction(){}

    public void setDialogue(){
        dialogues[0][0] = "it's a giant rock";
    }

    public void speak(){
        facePlayerDirection();
        startDialogue(this, dialogueSet);
        dialogueSet++;

       if (dialogues[dialogueSet][0] == null) dialogueSet--;
    }

    public void detectPlate(){
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        // Create a plate list
        for (int i = 0; i < gameP.iTile[1].length; i++){
            if (gameP.iTile[gameP.currentMap][i] != null && gameP.iTile[gameP.currentMap][i].name.equals(IT_MetalPlate.itName)) plateList.add(gameP.iTile[gameP.currentMap][i]);
        }

        // Create a rock list
        for (int i = 0; i < gameP.npc[1].length; i++){
            if (gameP.npc[gameP.currentMap][i] != null && gameP.npc[gameP.currentMap][i].name != null && gameP.npc[gameP.currentMap][i].name.equals(NPC_BigRock.npcName)) rockList.add(gameP.npc[gameP.currentMap][i]);
        }

        int count = 0;

        // Scan the plate list
        for (int i = 0; i < plateList.size(); i++){
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);

            if (distance < 8){
                if (linkedEntity == null){
                    linkedEntity = plateList.get(i);
                    gameP.playSE(7);
                } else {
                    linkedEntity = null;
                }
            }
        }

        for (int i = 0; i < rockList.size(); i++){
            if (rockList.get(i).linkedEntity != null) count++;
        }

        System.out.println("Jumlah Batu Diatas Metal Plate: " + count);
        if (count == rockList.size()){
            for (int i = 0; i < gameP.obj.length; i++){
                if (gameP.obj[gameP.currentMap][i] != null && gameP.obj[gameP.currentMap][i].name.equals(OBS_OBJ_DoorIron.objName)){
                    gameP.obj[gameP.currentMap][i] = null;
                    gameP.playSE(19);
                }
            }
        }
    }

    public void move(String direction){
        this.direction = direction;
        checkCollision();

        if (collisionOn == false){
            switch (direction){
                case "up": this.worldY -= this.speed; break;
                case "down": this.worldY += this.speed; break;
                case "left": this.worldX -= this.speed; break;
                case "right": this.worldX += this.speed; break;
            }
        }

        detectPlate();
    }

    public void update(){
        // Panggil update pada children (composite pattern)
        for (Entity child : children) {
            child.update();
        }
    }

    public void draw(Graphics2D g2d){
        super.draw(g2d);
        // Panggil draw pada children (composite pattern)
        for (Entity child : children) {
            child.draw(g2d);
        }
    }
}
