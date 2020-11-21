package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.Arrays;

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

    public void replaceWallTile(Tile tile)
    {
        int foundIndex = TileNavigator.containsTileByCoordinates(Arrays.asList(wallTiles), tile);
        if (foundIndex >= 0)
        {
            wallTiles[foundIndex] = tile;
        }
    }

    public void replaceWallTile(int index,Tile replacingTile)
    {
        wallTiles[index] = replacingTile;
    }

    public Direction getSide()
    {
        return side;
    }

    public Tile[] getWallTiles()
    {
        return wallTiles;
    }

    public void linkToStage(Stage stage)
    {
        for (int i = 0; i < wallTiles.length; i++)
        {
            Tile stageTile = stage.getTile(wallTiles[i]);
            replaceWallTile(i, stageTile);
        }
    }
}
