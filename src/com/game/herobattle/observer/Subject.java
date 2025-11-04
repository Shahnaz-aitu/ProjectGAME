package com.game.herobattle.observer;

public interface Subject {
    void registerObserver(Observer o);
    void unregisterObserver(Observer o);
    void notifyObservers(GameEvent event);
}