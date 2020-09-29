package com.dungeoncrawler.Javiarenka.equipment;

public class MundaneItem extends Equipment {
    int maxStackSize;
    int numberOfItems;
    boolean isQuestItem;

    public MundaneItem(String name, double weight, double price) {
        super(name, weight, price);
    }

    public MundaneItem(String name, double weight, double price, int amount) {
        this(name, weight, price);
        this.numberOfItems = amount;
    }
}
