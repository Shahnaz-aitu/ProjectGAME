package com.game.herobattle.attacks;

public class RangedAttack implements AttackStrategy {
    @Override
    public String getName() {
        return "Ranged"; }
    @Override
    public int computeDamage(int basePower) {
        return basePower + 2;
    }
}