package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class Tile
{
    private int x;
    private int y;
    private double visibility = 0;
    private boolean occupied = false;
    private boolean alreadySeen = false;
    private TileType type;

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

    public boolean wasAlreadySeen()
    {
        return alreadySeen;
    }

    public void setAlreadySeen()
    {
        this.alreadySeen = true;
    }

    public String getIdString()
    {
        return (x + "-" + y);
    }

//    @Override
//    public String toString()
//    {
//        return (this.type.strVal);
//    }

    public String getStringVal()
    {
        return (this.type.strVal);
    }

    public void printInfo()
    {
        System.out.println("(" + x + "," + y + "): " + getStringVal());
    }

    public String getStringVal(boolean complex)
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
        this.visibility = Math.min(1, visibility);
    }

    public void setVisibility(double visibility, boolean onlyIfBigger)
    {
//        if (this.x == 13 && this.y == 14)
//        {
//            System.out.println("");
//        }
//
//        if (this.alreadySeen)
//        {
//            if (this.visibility < 0.15) this.visibility = 0.15;
//        }

        if (onlyIfBigger)
        {
            if (visibility > this.visibility) setVisibility(visibility);
        }
        else
        {
            setVisibility(visibility);
        }
    }

    public boolean isWalkable()
    {
        return type.isWalkable() && !isOccupied();
    }

    public void makeNotVisible()
    {
        this.visibility = 0;
    }

    public String getInfoString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("visibility: ").append(this.visibility).append("\n");
        sb.append("already seen: ").append(this.alreadySeen).append("\n");
        return sb.toString();
    }

    public String toStringForJsonMap()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.x).append("-").append(this.y);
        return sb.toString();
    }
}
