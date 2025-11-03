package com.game.herobattle.decorators;

import com.game.herobattle.observer.IObserver;
import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.observer.GameEvent;
import com.game.herobattle.heroes.Hero;

public abstract class HeroDecorator extends Hero {
    protected final Hero inner;

    protected HeroDecorator(Hero inner) {
        super(inner.getName(), inner.getMaxHp(), inner.basePower, inner.strategy, inner.getOwnerName());
        this.inner = inner;
    }

    @Override public void useSpecial(GameEngine engine) { inner.useSpecial(engine); }
    @Override public void registerObserver(IObserver o) { inner.registerObserver(o); }
    @Override public void unregisterObserver(IObserver o) { inner.unregisterObserver(o); }
    @Override public void notifyObservers(GameEvent event) { inner.notifyObservers(event); }
}
