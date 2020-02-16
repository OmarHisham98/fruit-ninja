package sample;

import javafx.scene.media.AudioClip;


public class PlayComboSound implements ICommand {
    AudioClip audioClip = new AudioClip(this.getClass().getResource("combo sound.mp3").toString());

    @Override
    public void unexecute() {

    }

    @Override
    public void execute() {
        audioClip.play();
    }
}
