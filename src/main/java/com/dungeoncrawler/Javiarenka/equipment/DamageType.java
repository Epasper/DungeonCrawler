package com.dungeoncrawler.Javiarenka.equipment;

public enum DamageType {
    DMG_SLASHING ("SLASHING"),
    DMG_BLUDGEONING ("BLUDGEONING"),
    DMG_PIERCING ("PIERCING"),
    DMG_ICE ("ICE"),
    DMG_FIRE ("FIRE"),
    DMG_POISON ("POISON");

    private final String damageTypeString;

    DamageType(String s) {
        damageTypeString = s;
    }

    public boolean equalsName(String inputName) {
        return damageTypeString.equals(inputName);
    }

    public String toString() {
        return this.damageTypeString;
    }
}
