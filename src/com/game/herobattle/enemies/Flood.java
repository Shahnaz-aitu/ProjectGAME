package com.game.herobattle.enemies;

import com.game.herobattle.heroes.*;
import com.game.herobattle.singleton.GameEngine;
import com.game.herobattle.observer.GameEvent;
import com.game.herobattle.heroes.Hero;

public class Flood extends Enemy {
    public Flood() { super("Elf Flood", 35); }

    @Override
    public void performAction(Hero hero) {
        if (!isAlive()) return;
        int dmg = 10;
        GameEngine.getInstance().broadcast(new GameEvent(name, "creates a flood — deals " + dmg + " damage."));
        if (hero instanceof Warrior) {
            hero.takeDamage(dmg / 2);
        } else {
            hero.takeDamage(dmg);
        }
    }
}
