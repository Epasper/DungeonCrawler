package com.dungeoncrawler.Javiarenka.creature;

public enum Attribute {
    STRENGTH("Strength"),
    DEXTERITY("Dexterity"),
    STAMINA("Stamina"),
    INTELLIGENCE("Intelligence"),
    WILLPOWER("Willpower"),
    RESILIENCE("Resilience");

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
