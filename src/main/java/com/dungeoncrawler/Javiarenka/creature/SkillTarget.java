package com.dungeoncrawler.Javiarenka.creature;

public enum SkillTarget {
    ALLY("Ally"),
    ALL_ALLIES("All allies"),
    ENEMY("One enemy"),
    ALL_ENEMIES("All enemies"),
    ALLY_AND_ENEMY("Ally and enemy"),
    SELF("Self"),
    SELF_AND_ALLY("Self and ally"),
    SELF_AND_ENEMY("Self and enemy"),
    SELF_AND_ALL_ALLIES("Self and all allies"),
    SELF_AND_ALL_ENEMIES("Self and all enemies"),
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
        for (SkillTarget v : values()) {
            if (v.SkillTargetString.equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }
}
