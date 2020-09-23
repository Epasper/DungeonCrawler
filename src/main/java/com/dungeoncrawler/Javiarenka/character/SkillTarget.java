package com.dungeoncrawler.Javiarenka.character;

public enum SkillTarget {
    SELF("Self"),
    ENEMY("One enemy"),
    ALL_ENEMIES("All enemies"),
    ALLY("Ally"),
    ALL_ALLIES("All allies"),
    ALLY_AND_ENEMY("Ally and enemy"),
    EVERYBODY("Everybody");

    private final String SkillTargetString;

    SkillTarget(String SkillTargetString) {
        this.SkillTargetString = SkillTargetString;
    }

    public boolean equalsName(String inputName) {
        return SkillTargetString.equalsIgnoreCase(inputName);
    }

    public String toString() {
        return this.SkillTargetString;
    }

    public static SkillTarget geSkillTargetByName(String name) {
        name = name.toUpperCase();
        for (SkillTarget v : values()) {
            if (v.SkillTargetString.equals(name)) {
                return v;
            }
        }
        return null;
    }
}
