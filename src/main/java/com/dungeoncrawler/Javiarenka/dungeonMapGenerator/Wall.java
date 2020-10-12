package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class Wall
{
    private Direction side;
    private Tile[] wallTiles;

    Wall(Direction side, Tile[] wallTiles)
    {
        this.side = side;
        this.wallTiles = wallTiles;
    }

    public void setWallTiles(Tile[] wallTiles)
    {
        this.wallTiles = wallTiles;
    }

    public void setSide(Direction side)
    {
        this.side = side;
    }

    public Direction getSide()
    {
        return side;
    }

    public Tile[] getWallTiles()
    {
        return wallTiles;
    }
}
