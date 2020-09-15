package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.HeroClass;

import java.util.List;

public class Armor extends Equipment{
    private int damageReduction;
    private int chanceToHitReduction;
    private int additionalHp;

    public int getDamageReduction() {
        return damageReduction;
    }

    public void setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    public int getChanceToHitReduction() {
        return chanceToHitReduction;
    }

    public void setChanceToHitReduction(int chanceToHitReduction) {
        this.chanceToHitReduction = chanceToHitReduction;
    }

    public int getAdditionalHp() {
        return additionalHp;
    }

    public void setAdditionalHp(int additionalHp) {
        this.additionalHp = additionalHp;
    }

    public Armor(String name, List<HeroClass> classRestriction, double weight, double price, int damageReduction, int chanceToHitReduction) {
        super(name, classRestriction, weight, price);
        this.damageReduction = damageReduction;
        this.chanceToHitReduction = chanceToHitReduction;


    }
}
