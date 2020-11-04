package com.dungeoncrawler.Javiarenka.drop;

public enum ItemType {
    GEMS("Gems"),
    COINS("Coins"),
    GOLD("Gold"),
    DAMAGED_EQUIPMENT("Damaged equipment");

    private String itemTypeString;

    ItemType(String itemTypeString) {
        this.itemTypeString = itemTypeString;
    }

    @Override
    public String toString() {
        return this.itemTypeString;
    }
}
