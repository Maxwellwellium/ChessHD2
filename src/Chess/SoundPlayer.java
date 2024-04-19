package Chess;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {

    public void playSound(String soundFile, boolean loop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // MUSIC & SOUND PLAYER
        URL url = this.getClass().getClassLoader().getResource(soundFile);
        assert url != null;
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}
