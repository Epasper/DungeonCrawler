package com.dungeoncrawler.Javiarenka.drop;

import java.util.Random;

public class ItemDropped {
    private ItemType droppedItemType;
    private int itemsDropped;

    public ItemDropped() {
        this.droppedItemType = null;
        this.itemsDropped = 0;
    }

    public ItemType getDroppedItemType() {
        return droppedItemType;
    }

    public int getItemsDropped() {
        return itemsDropped;
    }

    public ItemDropped getRandomItems() { // todo Monster monster
        ItemDropped randomItems = new ItemDropped();
        if (true) { // todo monster.getHp() == 0
            randomItems.droppedItemType = getRandomItemType();
            randomItems.itemsDropped = getRandomItemNumber();
        }
        return randomItems;
    }

    private ItemType getRandomItemType() {
        int randomItemType = new Random().nextInt(ItemType.values().length);
        return ItemType.values()[randomItemType];
    }

    private int getRandomItemNumber() {
        return new Random().nextInt(100) + 1;
    }

    @Override
    public String toString() {
        return "ItemDropped{" +
                "droppedItemType=" + droppedItemType +
                ", itemsDropped=" + itemsDropped +
                '}';
    }
}
