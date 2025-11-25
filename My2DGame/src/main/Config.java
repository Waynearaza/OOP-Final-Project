package main;

import java.io.*;

public class Config {
    GamePanel gp;
    File configFile;

    public Config(GamePanel gp){
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            File file = new File("config.txt");
            // FileWriter automatically creates the file if it doesn't exist
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // FULL SCREEN
            bw.write(gp.fullScreenOn ? "On" : "Off");
            bw.newLine();

            // MUSIC VOLUME
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SFX VOLUME
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig() {
        File file = new File("config.txt");
        if(!file.exists()){
            // Use defaults, don't crash
            gp.fullScreenOn = false;
            gp.music.volumeScale = 3;
            gp.se.volumeScale = 3;
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s = br.readLine();
            gp.fullScreenOn = "On".equals(s);

            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);

        } catch (Exception e) {
            e.printStackTrace();
            // fallback to defaults
            gp.fullScreenOn = false;
            gp.music.volumeScale = 3;
            gp.se.volumeScale = 3;
        }
    }
}
