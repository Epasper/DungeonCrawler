package com.dungeoncrawler.Javiarenka.equipment;

public enum EquipmentSlots {
    CHEST("Chest"),
    LEFT_HAND("Left Hand"),
    RIGHT_HAND("Right Hand"),
    FEET("Feet"),
    ARMS("Arms"),
    BAGGAGE("Baggage");

    private final String equipmentSlotsString;

    EquipmentSlots(String heroClassString) {
        this.equipmentSlotsString = heroClassString;
    }

    public boolean equalsName(String inputName) {
        return equipmentSlotsString.equalsIgnoreCase(inputName);
    }

    public String toString() {
        return this.equipmentSlotsString;
    }

}
