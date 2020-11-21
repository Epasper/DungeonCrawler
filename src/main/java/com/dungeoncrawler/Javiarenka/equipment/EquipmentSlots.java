package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.HeroClass;

public enum EquipmentSlots {
    CHEST("Chest"),
    LEFT_HAND("Left Hand"),
    RIGHT_HAND("Right Hand"),
    FEET("Feet"),
    ARMS("Arms"),
    BAGGAGE("Baggage");

    //todo head and legs

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
