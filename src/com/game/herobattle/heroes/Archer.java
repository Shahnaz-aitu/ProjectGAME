package com.game.herobattle.heroes;

import com.game.herobattle.attacks.Effect;
import com.game.herobattle.attacks.RangedAttack;
import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.engine.GameEvent;

public class Archer extends Hero {
    private boolean canVanish = true;

    public Archer(String ownerName) {
        super("Archer", 90, 11, new RangedAttack(), ownerName);
    }

    @Override
    public void useSpecial(GameEngine engine) {
        if (!canVanish) {
            notifyObservers(new GameEvent(name, "can't vanish now."));
            return;
        }
        canVanish = false;
        engine.applyEffectToHero(this, new Effect("vanish", 1));
        notifyObservers(new GameEvent(name, "uses Vanish — becomes untargetable for 1 turn."));
    }
}
