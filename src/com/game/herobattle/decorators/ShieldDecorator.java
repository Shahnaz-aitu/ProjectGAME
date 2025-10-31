package com.game.herobattle.decorators;

import com.game.herobattle.engine.GameEvent;
import com.game.herobattle.heroes.Hero;

public class ShieldDecorator extends HeroDecorator {
    private int shieldHp;

    public ShieldDecorator(Hero inner, int shieldHp) {
        super(inner);
        this.shieldHp = shieldHp;
        notifyObservers(new GameEvent(inner.getName(), "gained shield of " + shieldHp + " HP"));
    }

    @Override
    public void takeDamage(int dmg) {
        if (shieldHp > 0) {
            int absorbed = Math.min(shieldHp, dmg);
            shieldHp -= absorbed;
            dmg -= absorbed;
            notifyObservers(new GameEvent(inner.getName(), "shield absorbed " + absorbed + " damage. Shield left=" + shieldHp));
        }
        if (dmg > 0) inner.takeDamage(dmg);
    }
}
