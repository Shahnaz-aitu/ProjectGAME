package com.game.herobattle.heroes;

import com.game.herobattle.attacks.AttackStrategy;
import com.game.herobattle.attacks.MagicAttack;
import com.game.herobattle.enemies.Enemy;
import com.game.herobattle.engine.GameEngine;
import com.game.herobattle.observer.GameEvent;
import com.game.herobattle.observer.Observer;
import com.game.herobattle.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public abstract class Hero implements Subject {
    protected final String name;
    protected int hp;
    protected final int maxHp;
    public int basePower;
    public AttackStrategy strategy;
    public final List<Observer> observers = new ArrayList<>();
    protected final String ownerName;

    public Hero(String name, int maxHp, int basePower, AttackStrategy defaultStrat, String ownerName) {
        this.name = name;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.basePower = basePower;
        this.strategy = defaultStrat;
        this.ownerName = ownerName;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public String getOwnerName() { return ownerName; }

    public void changeStrategy(AttackStrategy s) {
        this.strategy = s;
        notifyObservers(new GameEvent(name, "changed strategy to " + s.getName()));
    }

    public void attack(Enemy target) {
        int dmg = strategy.computeDamage(basePower);
        if (strategy instanceof MagicAttack && Math.random() < 0.2) {
            dmg += 5;
            notifyObservers(new GameEvent(name, "critical magic strike! +" + 5));
        }
        notifyObservers(new GameEvent(name, "attacks " + target.getName() + " with " + strategy.getName() + " for " + dmg));
        target.takeDamage(dmg);
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
        notifyObservers(new GameEvent(name, "took " + dmg + " damage. HP=" + hp + "/" + maxHp));
        if (hp == 0) notifyObservers(new GameEvent(name, "died."));
    }

    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) hp = maxHp;
        notifyObservers(new GameEvent(name, "healed " + amount + " HP. HP=" + hp + "/" + maxHp));
    }

    public abstract void useSpecial(GameEngine engine);
    @Override public void registerObserver(Observer o) {
        if (!observers.contains(o)) observers.add(o); }
    @Override public void unregisterObserver(Observer o) {
        observers.remove(o); }
    @Override public void notifyObservers(GameEvent event) {
        for (Observer o : new ArrayList<>(observers)) o.onEvent(event);
        GameEngine.getInstance().broadcast(event);
    }
    public void applyStatus(String stunned, int i) {
    }
}
