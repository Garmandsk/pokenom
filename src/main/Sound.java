package main;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl fc;
    public int volumeScale = 3;
    float volume;
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
        uTool.setUp(soundURL, 9, "cuttree");
        uTool.setUp(soundURL, 10, "gameover");
        uTool.setUp(soundURL, 11, "stairs");
        uTool.setUp(soundURL, 12, "sleep");
        uTool.setUp(soundURL, 13, "blocked");
        uTool.setUp(soundURL, 14, "parry");
        uTool.setUp(soundURL, 15, "speak");
        uTool.setUp(soundURL, 16, "merchant");
        uTool.setUp(soundURL, 17, "dungeon");
        uTool.setUp(soundURL, 18, "chipwall");
        uTool.setUp(soundURL, 19, "dooropen");
        uTool.setUp(soundURL, 20, "finalbattle");


    }

    public void setSound(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            // Cek dulu apakah MASTER_GAIN tersedia
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();
            } else {
                System.out.println("Warning: MASTER_GAIN control not supported.");
                fc = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkVolume() {
        if (fc == null) return;   // guard: jangan panggil setValue kalau tidak ada kontrol
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        fc.setValue(volume);
    }

    public void play() {
        if (clip != null) {
            clip.start();
        } else {
            System.out.println("Warning: play() called before clip was initialized.");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Warning: loop() called before clip was initialized.");
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        } else {
            // either clip was null, or it wasn't running
            System.out.println("Warning: stop() called before clip was initialized or while it was not running.");
        }
    }

}
