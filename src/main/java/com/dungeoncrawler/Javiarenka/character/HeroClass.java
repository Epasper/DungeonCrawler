package com.dungeoncrawler.Javiarenka.character;

public enum HeroClass {
    ARCHER ("ARCHER"),
    ROGUE ("ROGUE"),
    WARRIOR ("WARRIOR"),
    WIZARD ("WIZARD"),
    HEALER ("HEALER"),
    KNIGHT ("KNIGHT");

    private final String heroClassString;

    HeroClass(String s) {
        heroClassString = s;
    }

    public boolean equalsName(String inputName) {
        return heroClassString.equals(inputName);
    }

    public String toString() {
        return this.heroClassString;
    }
}
