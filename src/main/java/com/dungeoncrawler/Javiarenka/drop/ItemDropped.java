package com.dungeoncrawler.Javiarenka.drop;

public class ItemDropped {
    private ItemType droppedItemType; // todo can more than one item by acquired at once?
    private int itemsDropped;

    public ItemDropped(){
        this.droppedItemType = null;
        this.itemsDropped = 0;
    }

    public ItemDropped(ItemType droppedItemType, int itemsDropped) {
        this.droppedItemType = droppedItemType;
        this.itemsDropped = itemsDropped;
    }

    public ItemType getDroppedItemTypeType() {
        return droppedItemType;
    }

    public int getItemsDropped() {
        return itemsDropped;
    }

    public void setDroppedItemTypeType(ItemType droppedItemTypeType) {
        this.droppedItemType = droppedItemTypeType;
    }

    public void setItemsDropped(int itemsDropped) {
        this.itemsDropped = itemsDropped;
    }
}
