package com.game.herobattle.observer;

import com.game.herobattle.engine.GameEvent;

public interface ISubject {
    void registerObserver(IObserver o);
    void unregisterObserver(IObserver o);
    void notifyObservers(GameEvent event);
}