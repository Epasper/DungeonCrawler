package com.dungeoncrawler.Javiarenka.board;

public enum EncounterTileType {

    GRASS("Grass"),
    DIRT("Dirt"),
    WET("Wet"),
    WATER("Water"),
    WALL("Wall"),
    STONE("Stone");

    private final String encounterTileTypeString;

    EncounterTileType(String itemTypeString) {
        this.encounterTileTypeString = itemTypeString;
    }

    @Override
    public String toString() {
        return this.encounterTileTypeString;
    }
}
