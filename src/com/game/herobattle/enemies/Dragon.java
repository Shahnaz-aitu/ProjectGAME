package com.game.herobattle.enemies;

import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.engine.GameEvent;
import com.game.herobattle.heroes.Hero;

public class Dragon extends Enemy {
    public Dragon() { super("Dragon", 120); }

    @Override
    public void performAction(Hero hero) {
        if (!isAlive()) return;
        if (Math.random() < 0.4) {
            int dmg = 25;
            GameEngine.getInstance().broadcast(new GameEvent(name, "breathes fire for " + dmg));
            hero.takeDamage(dmg);
        } else {
            int dmg = 18;
            GameEngine.getInstance().broadcast(new GameEvent(name, "slashes with claws for " + dmg));
            hero.takeDamage(dmg);
        }
    }
}
