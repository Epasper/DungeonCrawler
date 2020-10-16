package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class PartyAvatar
{
    Tile occupiedTile;
    Direction direction;

    public PartyAvatar(Tile occupiedTile)
    {
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

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

}
