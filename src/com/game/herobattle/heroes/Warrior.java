package com.game.herobattle.heroes;

import com.game.herobattle.attacks.MeleeAttack;
import com.game.herobattle.singleton.GameEngine;
import com.game.herobattle.observer.GameEvent;

public class Warrior extends Hero {
    public Warrior(String ownerName) {
        super("Warrior", 120, 14, new MeleeAttack(), ownerName);
    }

    @Override
    public void useSpecial(GameEngine engine) {
        notifyObservers(new GameEvent(name, "uses Power Strike!"));
        Hero target = engine.getFirstAliveEnemy();
        if (target != null) {
            int dmg = basePower + 12;
            target.takeDamage(dmg);
            if (Math.random() < 0.25) {
                target.applyStatus("stunned", 1);
                notifyObservers(new GameEvent(name, target.getName() + " is stunned!"));
            }
        }
    }
}