package com.game.herobattle.enemies;

import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.engine.GameEvent;
import com.game.herobattle.heroes.Hero;

import java.util.HashMap;
import java.util.Map;

public abstract class Enemy {
    protected final String name;
    protected int hp;
    protected final int maxHp;
    protected final Map<String, Integer> statuses = new HashMap<>();

    public Enemy(String name, int hp) {
        this.name = name; this.hp = hp; this.maxHp = hp;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
        GameEngine.getInstance().broadcast(new GameEvent(name, "took " + dmg + " damage. HP=" + hp + "/" + maxHp));
        if (hp == 0) GameEngine.getInstance().broadcast(new GameEvent(name, "was defeated."));
    }

    public void applyStatus(String key, int turns) {
        statuses.put(key, turns);
    }
    public boolean hasStatus(String key) { return statuses.getOrDefault(key, 0) > 0; }
    public void tickStatuses() {
        statuses.entrySet().removeIf(e -> {
            int v = e.getValue() - 1;
            if (v <= 0) return true;
            statuses.put(e.getKey(), v);
            return false;
        });
    }

    public abstract void performAction(Hero hero);
}
