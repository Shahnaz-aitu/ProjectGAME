package com.game.herobattle.attacks;

public class MagicAttack implements AttackStrategy {
    @Override public String getName() { return "Magic"; }
    @Override public int computeDamage(int basePower) {
        return basePower + 7;
    }
}