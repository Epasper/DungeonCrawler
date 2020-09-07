package com.dungeoncrawler.Javiarenka.drop;

public enum ItemType {
    GEMS("GEMS"),
    COINS("COINS"),
    GOLD("GOLD");

    private String itemTypeString;

    ItemType(String itemTypeString) {
        this.itemTypeString = itemTypeString;
    }

    @Override
    public String toString() {
        return this.itemTypeString;
    }
}
