package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class PartyAvatar
{
    //int id;
    Tile occupiedTile;

    //public PartyAvatar(int id, Tile occupiedTile)
    public PartyAvatar(Tile occupiedTile)
    {
       // this.id = id;
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

//    public int getId()
//    {
//        return id;
//    }

//    public void setId(int id)
//    {
//        this.id = id;
//    }
}
