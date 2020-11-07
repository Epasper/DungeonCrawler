package com.dungeoncrawler.Javiarenka.creature;

public enum Attribute {
    ARCHER("Archer"),
    ROGUE("Rogue"),
    WARRIOR("Warrior"),
    WIZARD("Wizard"),
    HEALER("Healer"),
    KNIGHT("Knight");

    private final String attributeString;

    Attribute(String attributeString) {
        this.attributeString = attributeString;
    }

    public boolean equalsName(String inputName) {
        return attributeString.equalsIgnoreCase(inputName);
    }

    public String toString() {
        return this.attributeString;
    }

    public static Attribute getHeroClassByName(String name) {
        for(Attribute v : values()){
            if( v.attributeString.equalsIgnoreCase(name)){
                return v;
            }
        }
        return null;
    }
}
