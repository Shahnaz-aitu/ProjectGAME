package com.game.herobattle.heroes;

import com.game.herobattle.attacks.RangedAttack;
import com.game.herobattle.observer.EventLogger;
import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.observer.GameEvent;
import com.game.herobattle.observer.*;
import java.util.Scanner;

public class Princess extends Hero {
    private Hero summoned = null;

    public Princess(String ownerName) {
        super("Princess", 100, 8, new RangedAttack(), ownerName);
    }

    @Override
    public void useSpecial(GameEngine engine) {
        if (summoned != null && summoned.getHp() > 0) {
            notifyObservers(new GameEvent(name, "already has a summoned helper: " + summoned.getName()));
            return;
        }
        Scanner sc = engine.getScanner();
        System.out.println("Who to summon? (mage/warrior/archer): ");
        String choice = sc.nextLine().trim().toLowerCase();
        Hero helper;
        switch (choice) {
            case "mage": helper = new Mage(ownerName); break;
            case "warrior": helper = new Warrior(ownerName); break;
            case "archer": helper = new Archer(ownerName); break;
            default:
                notifyObservers(new GameEvent(name, "invalid summon choice."));
                return;
        }
        summoned = helper;
        engine.addAlly(summoned);

        summoned.registerObserver(EventLogger.getInstance());
        summoned.registerObserver(new Announcer());
        notifyObservers(new GameEvent(name, "summoned helper: " + summoned.getName()));
    }
}
