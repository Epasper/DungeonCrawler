package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.HeroClass;

import java.util.List;

public class Armor extends Equipment{
    private int additionalHp;

    public Armor(String name, List<HeroClass> classRestriction, double weight, double price, int additionalHp) {
        super(name, classRestriction, weight, price);
        this.additionalHp = additionalHp;
    }

    public int getAdditionalHp() {
        return additionalHp;
    }

    public void setAdditionalHp(int additionalHp) {
        this.additionalHp = additionalHp;
    }
}
