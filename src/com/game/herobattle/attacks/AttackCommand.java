package com.game.herobattle.attacks;

import com.game.herobattle.enemies.Enemy;
import com.game.herobattle.heroes.Hero;

public class AttackCommand implements GameCommand {
    private final Hero hero;
    private final Enemy target;

    public AttackCommand(Hero hero, Enemy target) {
        this.hero = hero;
        this.target = target; }

    @Override
    public void execute() {
        if (hero.getHp() <= 0) {
            System.out.println(hero.getName() + " is dead and can't attack.");
            return;
        }
        hero.attack(target);
    }
}