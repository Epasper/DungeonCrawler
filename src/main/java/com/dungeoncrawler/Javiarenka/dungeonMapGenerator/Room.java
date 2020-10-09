package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class Room extends Stage
{
    private int xPos;
    private int yPos;
    private Wall[] walls = new Wall[Direction.SIZE];
    private Tile door;

    Room(int xCoord, int yCoord, int roomWidth, int roomHeight)
    {
        super(roomWidth, roomHeight);
        xPos = xCoord;
        yPos = yCoord;
    }

    Room(Tile seedTile, int roomWidth, int roomHeight)
    {
        super(roomWidth, roomHeight);
        xPos = seedTile.getX();
        yPos = seedTile.getY();
    }

    Room(Tile[][] roomTiles)
    {
        super(roomTiles);

        xPos = roomTiles[0][0].getX();
        yPos = roomTiles[0][0].getY();

        for (int i = 0; i < getWidth(); i++)
        {
            setTileTypes(roomTiles[i], TileType.ROOM);
        }

        createPeripheralWall();

        for (Direction dir : Direction.values())
        {
            switch (dir)
            {
                case UP:
                    walls[dir.ordinal()] = new Wall(dir, this.getRow(0));
                    break;

                case LEFT:
                    walls[dir.ordinal()] = new Wall(dir, this.getColumn(0));
                    break;

                case DOWN:
                    walls[dir.ordinal()] = new Wall(dir, this.getRow(this.getHeight() - 1));
                    break;

                case RIGHT:
                    walls[dir.ordinal()] = new Wall(dir, this.getColumn(this.getWidth() - 1));
                    break;
            }
        }

    }

    public int getxPos()
    {
        return xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public Wall[] getWalls()
    {
        return walls;
    }

    public Tile[] getWallTiles (Direction dir)
    {
        return walls[dir.ordinal()].getWallTiles();
    }

    public Tile getDoor()
    {
        return door;
    }

    public void setDoor(Tile door)
    {
        this.door = door;
    }
}
