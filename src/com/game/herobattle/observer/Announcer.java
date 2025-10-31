package com.game.herobattle.observer;

import com.game.herobattle.engine.GameEvent;

public class Announcer implements IObserver {
    @Override
    public void onEvent(GameEvent event) {
        System.out.println("[SPEAKER] " + event.getSource() + ": " + event.getMessage());
    }
}