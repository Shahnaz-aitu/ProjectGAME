package com.game.herobattle.heroes;

import com.game.herobattle.attacks.Effect;
import com.game.herobattle.attacks.MagicAttack;
import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.engine.GameEvent;
import com.game.herobattle.observer.*;

public class Mage extends Hero {
    private boolean canFly = true;

    public Mage(String ownerName) {
        super("Mage", 80, 12, new MagicAttack(), ownerName);
    }

    @Override
    public void useSpecial(GameEngine engine) {
        if (!canFly) {
            notifyObservers(new GameEvent(name, "can't fly right now."));
            return;
        }
        canFly = false;
        engine.applyEffectToHero(this, new Effect("fly", 1));
        notifyObservers(new GameEvent(name, "uses Fly — will avoid next attack."));
    }
}
