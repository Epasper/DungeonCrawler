package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class HeroAvatar
{
    int id;
    Tile occupiedTile;

    public HeroAvatar(int id, Tile occupiedTile)
    {
        this.id = id;
        this.occupiedTile = occupiedTile;
    }

    public Tile getOccupiedTile()
    {
        return occupiedTile;
    }

    public void setOccupiedTile(Tile occupiedTile)
    {
        this.occupiedTile = occupiedTile;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
