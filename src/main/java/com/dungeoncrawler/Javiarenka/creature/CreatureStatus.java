package com.dungeoncrawler.Javiarenka.creature;

public enum CreatureStatus {
    DEFAULT("DEFAULT"),
    FOCUSED("FOCUSED"),
    UNFOCUSED("UNFOCUSED"),
    EXPOSED("EXPOSED"),
    PROTECTED("PROTECTED"),
    WEAKENED("WEAKENED"),
    SLOWED("SLOWED"),
    HASTED("HASTED"),
    STUNNED("STUNNED"),
    CHILLED("CHILLED"),
    FROZEN("FROZEN"),
    BURNED("BURNED"),
    BLEEDING("BLEEDING"),
    POISONED("POISONED"),
    BLINDED("BLINDED"),
    CHARMED("CHARMED"),
    FRIGHTENED("FRIGHTENED"),
    ROOTED("ROOTED"),
    INVISIBLE("INVISIBLE"),
    UNCONSCIOUS("UNCONSCIOUS");

    private final String characterStatusString;

    CreatureStatus(String characterStatusString) {
        this.characterStatusString = characterStatusString;
    }

    public boolean equalsCharacterStatus(String input) {
        return characterStatusString.equalsIgnoreCase(input);
    }

    @Override
    public String toString() {
        return this.characterStatusString;
    }

    public static CreatureStatus getCharacterStatusByName(String name) {
        for (CreatureStatus status : values()) {
            if (status.characterStatusString.equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
