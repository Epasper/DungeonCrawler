package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import javax.xml.validation.Validator;
import java.util.Arrays;

public enum Direction
{
    RIGHT,
    DOWN,
    LEFT,
    UP;

    public static final Direction[] VALUES = values();
    public static final int SIZE = VALUES.length;

    public static Direction nextDir(Direction dir)
    {
        int index = Arrays.asList(VALUES).indexOf(dir);
        if (index == SIZE - 1)
        {
            return VALUES[0];
        }
        else
        {
            return VALUES[index + 1];
        }
    }
}
