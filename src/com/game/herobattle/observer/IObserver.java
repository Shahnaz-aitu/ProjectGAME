package com.game.herobattle.observer;

import com.game.herobattle.engine.GameEvent;

public interface IObserver {
    void onEvent(GameEvent event);
}