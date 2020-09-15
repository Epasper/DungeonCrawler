package com.dungeoncrawler.Javiarenka.character;

public enum HeroClass {
    ARCHER("ARCHER"),
    ROGUE("ROGUE"),
    WARRIOR("WARRIOR"),
    WIZARD("WIZARD"),
    HEALER("HEALER"),
    KNIGHT("KNIGHT");

    private final String heroClassString;

    HeroClass(String heroClassString) {
        this.heroClassString = heroClassString;
    }

    public boolean equalsName(String inputName) {
        return heroClassString.equalsIgnoreCase(inputName);
    }

    public String toString() {
        return this.heroClassString;
    }

    public static HeroClass getHeroClassByName(String name) {
        name = name.toUpperCase();
        for(HeroClass v : values()){
            if( v.heroClassString.equals(name)){
                return v;
            }
        }
        return null;
    }
}
