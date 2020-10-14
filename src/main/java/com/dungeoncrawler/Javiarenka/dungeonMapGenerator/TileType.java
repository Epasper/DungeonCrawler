package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.HashMap;

public enum TileType
{
    WALL("#"),
    ROOM("_", true),
    ROOM_LOCKED("="),
    ROOM_SCANNED("-", true),
    CORRIDOR(" ", true),
    INTERSECTION("+", true),
    DOOR_CLOSED(":"),
    DOOR_OPENED(".", true),
    DOOR_LOCKED(";"),
    EMPTY("o"),
    WALKED("x"),
    BREADCRUMB("^"),
    DEBUG("D"),
    CURRENT_PATH("H"),
    UNREACHABLE("X"),
    OBSTRUCTION("%"),
    CUTOFF("|"),
    EXCLUDED("/"),
    ERROR("@");

    String strVal;
    private boolean isWalkable = false;
    public static final int strLen = 3;
    public static final TileType[] VALUES = values();

    TileType(String value)
    {
        strVal = " " + value + " ";
    }

    TileType(String value, Boolean isWalkable)
    {
        strVal = " " + value + " ";
        this.isWalkable = isWalkable;
    }

    public static TileType fromStrVal(String strVal)
    {
        HashMap<String, TileType> tileTypesMap = new HashMap<>();
        for (TileType type : VALUES)
        {
            tileTypesMap.put(type.strVal, type);
        }
        return tileTypesMap.get(strVal);
    }

    public boolean isDoor()
    {
        return this.name().contains("DOOR");
    }

    public boolean isClosedDoor()
    {
        return (this.name().contains("CLOSED") || this.name().contains("LOCKED"));
    }

    public boolean isWalkable()
    {
        return isWalkable;
    }

}
