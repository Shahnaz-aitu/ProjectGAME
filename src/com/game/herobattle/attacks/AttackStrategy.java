package com.game.herobattle.attacks;

public interface AttackStrategy {
    String getName();
    int computeDamage(int basePower);
}
