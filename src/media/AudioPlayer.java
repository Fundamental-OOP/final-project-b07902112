package media;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class AudioPlayer {
    private static final Map<Object, File> sounds = new HashMap<>();
    private static final Random random = new Random();

    public static void addAudioByFileName(String audioName) {
        String path = "assets/Audio/" + audioName + ".wav";
        addAudioByFilePath(audioName, path);
    }

    public static void addAudioByFilePath(Object audioName, String path) {
        sounds.put(audioName, Paths.get(path).toFile());
    }

    public static void playSounds(Object audioName) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sounds.get(audioName)));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playLoopSound(Object audioName) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sounds.get(audioName)));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playRandomSound(Object... sounds) {
        Object sound = sounds[random.nextInt(sounds.length)];
        playSounds(sound);
    }
}
