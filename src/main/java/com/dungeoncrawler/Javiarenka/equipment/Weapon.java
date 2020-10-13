package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.HeroClass;

import java.util.List;

public class Weapon extends Equipment {
    private DamageType damageType;
    private int damageDealt;
    private EquipmentSlots occupyingSlot;

    public Weapon(String name, List<HeroClass> classRestriction, double weight, double price, DamageType damageType, int damageDealt) {
        super(name, classRestriction, weight, price);
        this.damageType = damageType;
        this.damageDealt = damageDealt;
        this.occupyingSlot = EquipmentSlots.RIGHT_HAND;
    }

    public Weapon() {

    }

    public EquipmentSlots getOccupyingSlot() {
        return occupyingSlot;
    }

    public void setOccupyingSlot(EquipmentSlots occupyingSlot) {
        this.occupyingSlot = occupyingSlot;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
}
