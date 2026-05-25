package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;

public class UISound {

    private static final AudioClip CLICK_SOUND;

    static {
        CLICK_SOUND = new AudioClip(
                UISound.class
                        .getResource("/sounds/luxury-click.wav")
                        .toExternalForm()
        );
        CLICK_SOUND.setVolume(0.35);
    }

    public static void applyClickSound(Button button) {
        button.addEventHandler(ActionEvent.ACTION, e -> {
            CLICK_SOUND.play();
        });
    }
}
