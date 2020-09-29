package com.dungeoncrawler.Javiarenka.equipment;

public class InventorySlotsFullException extends Throwable {
    @Override
    public String getMessage() {
        return "Inventory slots are full";
    }
}
