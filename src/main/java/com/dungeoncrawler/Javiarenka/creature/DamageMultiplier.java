package com.dungeoncrawler.Javiarenka.creature;

public class DamageMultiplier {
    Attribute attribute;
    double multiplier;

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
