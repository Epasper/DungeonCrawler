package com.dungeoncrawler.Javiarenka.character;

public enum MonsterRace {
    GOBLIN("Goblin"),
    UNDEAD("Undead"),
    ANIMAL("Animal"),
    ELEMENTAL("Elemental"),
    FIEND("Fiend"),
    DRAGON("Dragon"),
    MONSTROSITY("Monstrosity");

    private final String monsterRaceName;

    MonsterRace(String monsterRaceName) {
        this.monsterRaceName = monsterRaceName;
    }

    public boolean equalsName(String inputName) {
        return monsterRaceName.equalsIgnoreCase(inputName);
    }

    public String getMonsterRaceName() {
        return monsterRaceName;
    }

    public static MonsterRace getMonsterRaceByName(String name) {
        name = name.toUpperCase();
        for (MonsterRace v : values()) {
            if (v.monsterRaceName.equals(name)) {
                return v;
            }
        }
        return null;
    }
}
