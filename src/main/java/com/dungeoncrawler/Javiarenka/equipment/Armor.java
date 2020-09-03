package com.dungeoncrawler.Javiarenka.equipment;

public class Armor extends Equipment{
    private int additionalHp;

    public Armor(String name, double weight, double price, int additionalHp) {
        super(name, weight, price);
        this.additionalHp = additionalHp;
    }

    public int getAdditionalHp() {
        return additionalHp;
    }

    public void setAdditionalHp(int additionalHp) {
        this.additionalHp = additionalHp;
    }
}
