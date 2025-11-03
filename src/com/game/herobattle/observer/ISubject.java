package com.game.herobattle.observer;

public interface ISubject {
    void registerObserver(IObserver o);
    void unregisterObserver(IObserver o);
    void notifyObservers(GameEvent event);
}