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

    public static Direction opositeDir(Direction dir)
    {
        switch (dir)
        {
            case RIGHT:
                return LEFT;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case UP:
                return DOWN;
        }
        return dir;
    }

    public static Direction getLeftPerpendicularDir(Direction dir)
    {
        switch (dir)
        {
            case RIGHT:
                return UP;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case UP:
                return LEFT;
        }
        return dir;
    }

    public static Direction getRightPerpendicularDir(Direction dir)
    {
        return opositeDir(getLeftPerpendicularDir(dir));
    }
}
