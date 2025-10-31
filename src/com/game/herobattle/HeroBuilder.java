package com.game.herobattle;

import com.game.herobattle.heroes.*;

public class HeroBuilder {
    private String type;
    private String ownerName;
    private int extraHp = 0;
    private int extraPower = 0;

    public HeroBuilder setType(String type) { this.type = type; return this; }
    public HeroBuilder setOwnerName(String owner) { this.ownerName = owner; return this; }
    public HeroBuilder addHp(int hp) { this.extraHp = hp; return this; }
    public HeroBuilder addPower(int p) { this.extraPower = p; return this; }

    public Hero build() {
        Hero base;
        switch (type.toLowerCase()) {
            case "mage": base = new Mage(ownerName); break;
            case "warrior": base = new Warrior(ownerName); break;
            case "archer": base = new Archer(ownerName); break;
            case "princess": base = new Princess(ownerName); break;
            default: throw new IllegalArgumentException("Unknown hero type");
        }
        if (extraHp > 0) base.heal(extraHp);
        if (extraPower > 0) base.basePower += extraPower;
        return base;
    }
}
