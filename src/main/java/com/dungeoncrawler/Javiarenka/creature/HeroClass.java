package com.dungeoncrawler.Javiarenka.creature;

public enum HeroClass {
    ARCHER("Archer"),
    ROGUE("Rogue"),
    WARRIOR("Warrior"),
    WIZARD("Wizard"),
    HEALER("Healer"),
    KNIGHT("Knight");

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
        for(HeroClass v : values()){
            if( v.heroClassString.equalsIgnoreCase(name)){
                return v;
            }
        }
        return null;
    }
}
