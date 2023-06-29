package frontend.menu.game;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
    private static final long serialVersionUID = 1L;
    private static final AudioPlayer instance = new AudioPlayer();
    Clip background = getClip("background");
    private boolean silence = true;

    private AudioPlayer() {}
    public static AudioPlayer getInstance() {return instance;}
    public void setSilence(boolean silence) {
        if(silence == this.silence)
            return;
        if(silence)
            background.stop();
        else {
            background.loop(Clip.LOOP_CONTINUOUSLY);
            background.start();
        }
        this.silence = silence;
    }

    private Clip getClip(String name) {
        Clip clip = null;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds//"+name+".wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clip;
    }

    public void Play(String name) {
        if(!silence)
            getClip(name).start();
    }

}
