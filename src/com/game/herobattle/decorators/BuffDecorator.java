package com.game.herobattle.decorators;

import com.game.herobattle.enemies.Enemy;
import com.game.herobattle.engine.GameEvent;
import com.game.herobattle.heroes.Hero;

public class BuffDecorator extends HeroDecorator {
    private final double multiplier;

    public BuffDecorator(Hero inner, double multiplier) {
        super(inner);
        this.multiplier = multiplier;
        notifyObservers(new GameEvent(inner.getName(), "got buff damage *" + multiplier));
    }

    @Override
    public void attack(Enemy target) {
        int dmg = (int) (inner.strategy.computeDamage(inner.basePower) * multiplier);
        notifyObservers(new GameEvent(inner.getName(), "attacks (buffed) " + target.getName() + " for " + dmg));
        target.takeDamage(dmg);
    }
}
