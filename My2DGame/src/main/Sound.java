package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;

import java.net.URL;

public class Sound {
    Clip clip;
    FloatControl volumeControl;
    URL soundURL[] = new URL[30];


    public Sound(){
        soundURL[0] = getClass().getResource("/sound/erika.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e){}
    }
    public  void play(){
        clip.start();
    }
    public void loop (){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop (){
        clip.stop();
    }

    public void setVolume(float value){
        if (volumeControl != null) {
            // Gain value must be in decibels, so convert:
            float min = volumeControl.getMinimum();  // usually around -80 dB
            float max = volumeControl.getMaximum();  // usually 6 dB

            float gain = min + (max - min) * value;  // convert percentage to decibels
            volumeControl.setValue(gain);
        }
    }

}
