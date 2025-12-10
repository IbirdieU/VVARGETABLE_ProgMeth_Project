package logic.managers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private static final AudioClip throwingSound;
    private static final AudioClip strikeSound;
    private static final AudioClip skillUsedSound;
    private static final AudioClip tickingSound;
    private static final AudioClip clickSound;
    private static final AudioClip hoverSound;
    private static final MediaPlayer bgmPlayer;

    static {
        throwingSound = new AudioClip(ClassLoader.getSystemResource("sound/Throwing.mp3").toString());
        strikeSound = new AudioClip(ClassLoader.getSystemResource("sound/Strike.wav").toString());
        skillUsedSound = new AudioClip(ClassLoader.getSystemResource("sound/SkillUsed.wav").toString());
        tickingSound = new AudioClip(ClassLoader.getSystemResource("sound/Ticking.wav").toString());
        clickSound = new AudioClip(ClassLoader.getSystemResource("sound/Click.wav").toString());
        hoverSound = new AudioClip(ClassLoader.getSystemResource("sound/Hover.wav").toString());

        Media backgroundMusic = new Media(ClassLoader.getSystemResource("sound/music.mp3").toString());
        bgmPlayer = new MediaPlayer(backgroundMusic);
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmPlayer.setVolume(0.5);
    }

    public static void playThrowSound() {
        throwingSound.play();
    }

    public static void playStrikeSound() {
        strikeSound.play();
    }

    public static void playSkillSound() {
        skillUsedSound.play();
    }

    public static void playTickingSound() {
        tickingSound.play();
    }

    public static void playClickSound() {
        clickSound.play();
    }

    public static void playHoverSound() {
        hoverSound.play();
    }

    public static void playBackgroundMusic() {
        bgmPlayer.play();
    }
}
