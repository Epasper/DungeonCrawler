package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.Random;

public enum BuildDirection
{
    RD,
    LD,
    RU,
    LU;

    public static final BuildDirection[] VALUES = values();
    public static final int SIZE = VALUES.length;
    public static final Random RANDOM = new Random();

    public static BuildDirection getRandomBuildDirection()
    {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}
