package com.game.herobattle.attacks;

public class MeleeAttack implements AttackStrategy {
    @Override public String getName() { return "Melee"; }
    @Override public int computeDamage(int basePower) {
        return basePower + 5;
    }
}