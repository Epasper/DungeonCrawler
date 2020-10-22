package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class Tile
{
    private int x;
    private int y;
    private double visibility = 0;
    private boolean occupied = false;
    private TileType type;
    //private HashMap<String,mapGenerator.Tile> surroundingTiles;

    //TODO: dodać pole 'visited' które będzie true, jeśli dane pole było w zasięgu widoczności
    // chodzi o to, żeby po obuszczeniu danego miejsca, nie znikało ono całkowicie w fog-of-war

    Tile()
    {

    }

    Tile(int coordX, int coordY)
    {
        x = coordX;
        y = coordY;
        type = TileType.EMPTY;
    }

    Tile(int coordX, int coordY, TileType targetType)
    {
        x = coordX;
        y = coordY;
        type = targetType;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public double getVisibility()
    {
        return visibility;
    }

    public boolean isOccupied()
    {
        return occupied;
    }

    public void setOccupied(boolean occupied)
    {
        this.occupied = occupied;
    }

    public String getIdString()
    {
        return (x + "-" + y);
    }

    @Override
    public String toString()
    {
        return (this.type.strVal);
    }

    public void printInfo()
    {
        System.out.println("(" + x + "," + y + "): " + toString()) ;
    }

    public String toString(boolean complex)
    {
        return (x + this.type.strVal + y + " ");
    }

    public void setType(TileType type)
    {
        this.type = type;
    }

    public TileType getType()
    {
        return type;
    }

    public void setVisibility(double visibility)
    {
//        this.visibility = visibility;
        this.visibility = Math.min(1, visibility);
    }

    public boolean isWalkable()
    {
        return type.isWalkable() && !isOccupied();
    }
}
