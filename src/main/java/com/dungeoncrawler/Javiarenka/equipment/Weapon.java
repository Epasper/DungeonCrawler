package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.HeroClass;

import java.util.List;

public class Weapon extends Equipment {
    private DamageType damageType;
    private int damageDealt;

    public Weapon(String name, List<HeroClass> classRestriction, double weight, double price, DamageType damageType, int damageDealt) {
        super(name, classRestriction, weight, price);
        this.damageType = damageType;
        this.damageDealt = damageDealt;
    }

    public Weapon() {

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
