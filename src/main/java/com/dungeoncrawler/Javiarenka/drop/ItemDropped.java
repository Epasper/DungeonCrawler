package com.dungeoncrawler.Javiarenka.drop;

public class itemDropped {
    private ItemType droppedItemType; // todo can more than one item by acquired at once?
    private int itemsDropped;

    public itemDropped(){
        this.droppedItemType = null;
        this.itemsDropped = 0;
    }

    public itemDropped(ItemType droppedItemType, int itemsDropped) {
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
