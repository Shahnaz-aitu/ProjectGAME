package com.game.herobattle.observer;

public class Announcer implements IObserver {
    @Override
    public void onEvent(GameEvent event) {
        System.out.println("[Announcer] " + event.getSource() + ": " + event.getMessage());
    }
}