package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    UtilityTool uTool = new UtilityTool();

    Sound(){
        uTool.setUp(soundURL, 0, "BlueBoyAdventure");
        uTool.setUp(soundURL, 1, "coin");
        uTool.setUp(soundURL, 2, "powerup");
        uTool.setUp(soundURL, 3, "fanfare");
        uTool.setUp(soundURL, 4, "hitmonster");
        uTool.setUp(soundURL, 5, "receivedamage");
        uTool.setUp(soundURL, 6, "levelup");
        uTool.setUp(soundURL, 7, "cursor");
        uTool.setUp(soundURL, 8, "burning");

    }

    public void setSound(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e){
            System.out.println("Audio Error");
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
