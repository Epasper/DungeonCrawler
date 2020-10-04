package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.HashMap;

public enum TileType
{
    WALL("#"),
    ROOM("_"),
    ROOM_SCANNED("-"),
    CORRIDOR(" "),
    INTERSECTION("+"),
    DOOR("."),
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
    public static final int strLen = 3;
    public static final TileType[] VALUES = values();

    TileType(String value)
    {
        strVal = " " + value + " ";
    }

    public static TileType fromStrVal(String strVal)
    {
        HashMap<String, TileType> tileTypesMap = new HashMap<>();
        for(TileType type : VALUES)
        {
            tileTypesMap.put(type.strVal, type);
        }
        return tileTypesMap.get(strVal);
    }

}
