package com.game.herobattle.heroes;

public class HeroFactory {
    public static Hero create(String type, String ownerName) {
        switch (type.toLowerCase()) {
            case "mage": return new Mage(ownerName);
            case "warrior": return new Warrior(ownerName);
            case "archer": return new Archer(ownerName);
            case "princess": return new Princess(ownerName);
            default: throw new IllegalArgumentException("Unknown hero type: " + type);
        }
    }
}
