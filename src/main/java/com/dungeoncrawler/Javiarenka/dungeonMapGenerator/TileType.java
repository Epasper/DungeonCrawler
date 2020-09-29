package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public enum TileType
{
    WALL(" # "),
    ROOM("   "),
    CORRIDOR("   "),
    DOOR(" . "),
    EMPTY(" o "),
    WALKED(" x "),
    BREADCRUMB(" ^ "),
    OBSTRUCTION(" % "),
    ERROR(" @ ");

    String strVal;

    TileType(String value)
    {
        strVal = value;
    }


}
