package main;

import java.io.*;

public class Config {
    GamePanel gameP;

    public Config(GamePanel gameP){
        this.gameP = gameP;
    }

    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Full Screen
            if (gameP.fullScreenOn) bw.write("on");
            else if (!gameP.fullScreenOn) bw.write("off");
            bw.newLine();

            bw.write(String.valueOf((gameP.music.volumeScale)));
            bw.newLine();

            bw.write(String.valueOf(gameP.se.volumeScale));

            bw.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String str = br.readLine();

            // Full Screen
            if (str.equals("on")) gameP.fullScreenOn = true;
            if (str.equals("off")) gameP.fullScreenOn = false;

            // Music and SE
            str = br.readLine();
            gameP.music.volumeScale = Integer.parseInt(str);

            str = br.readLine();
            gameP.se.volumeScale = Integer.parseInt(str);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
