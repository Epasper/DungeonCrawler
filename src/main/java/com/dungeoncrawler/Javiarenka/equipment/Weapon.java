package com.dungeoncrawler.Javiarenka.equipment;

public class Weapon extends Equipment {
    private String type;
    private int damageDealt;

    public Weapon(String name, double weight, double price, String type, int damageDealt) {
        super(name, weight, price);
        this.type = type;
        this.damageDealt = damageDealt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
}
