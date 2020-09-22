package com.dungeoncrawler.Javiarenka.character;

public enum CharacterStatus {
    DEFAULT("DEFAULT"), // TODO:
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

    CharacterStatus(String characterStatusString) {
        this.characterStatusString = characterStatusString;
    }

    public boolean equalsCharacterStatus(String input) {
        return characterStatusString.equalsIgnoreCase(input);
    }

    @Override
    public String toString() {
        return this.characterStatusString;
    }

    public static CharacterStatus getCharacterStatusByName(String name) {
        name = name.toUpperCase();
        for (CharacterStatus status : values()) {
            if (status.characterStatusString.equals(name)) {
                return status;
            }
        }
        return null;
    }
}
