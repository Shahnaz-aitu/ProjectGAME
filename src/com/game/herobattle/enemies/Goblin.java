package com.game.herobattle.enemies;

import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.engine.GameEvent;
import com.game.herobattle.heroes.Hero;

public class Goblin extends Enemy {
    public Goblin() { super("Goblin", 20); }

    @Override
    public void performAction(Hero hero) {
        if (!isAlive()) return;
        if (hasStatus("stunned")) { GameEngine.getInstance().broadcast(new GameEvent(name, "is stunned and skips turn.")); return; }
        int dmg = 6;
        if (GameEngine.getInstance().heroHasEffect(hero, "fly")) {
            GameEngine.getInstance().broadcast(new GameEvent(name, "misses because hero is flying."));
            return;
        }
        if (GameEngine.getInstance().heroHasEffect(hero, "vanish")) {
            GameEngine.getInstance().broadcast(new GameEvent(name, "can't find the vanished hero."));
            return;
        }
        GameEngine.getInstance().broadcast(new GameEvent(name, "attacks " + hero.getName() + " for " + dmg));
        hero.takeDamage(dmg);
    }
}
