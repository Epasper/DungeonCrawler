package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.drop.ItemType;

public class MundaneItem extends Equipment{
    private int maxStackSize;
    private boolean isQuestItem;
    private int selectPossibility;
    private int numberOfItems;
    private ItemType itemType;

    public int getNumberOfItems() {
        return numberOfItems;
    }
    public int getSelectPossibility() {
        return selectPossibility;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public boolean isQuestItem() {
        return isQuestItem;
    }

    public MundaneItem(String name, double weight, double price, int amount) {
        super(name, weight, price);
        this.numberOfItems = amount;
    }

    public MundaneItem(String name, double weight, double price, int maxStackSize, boolean isQuestItem, int selectPossibility, int numberOfItems, ItemType itemType) {
        super(name, weight, price);
        this.maxStackSize = maxStackSize;
        this.isQuestItem = isQuestItem;
        this.selectPossibility = selectPossibility;
        this.numberOfItems = numberOfItems;
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "MundaneItem{" +
                "maxStackSize=" + maxStackSize +
                ", isQuestItem=" + isQuestItem +
                ", selectPossibility=" + selectPossibility +
                ", numberOfItems=" + numberOfItems +
                ", itemType=" + itemType +
                '}';
    }
}