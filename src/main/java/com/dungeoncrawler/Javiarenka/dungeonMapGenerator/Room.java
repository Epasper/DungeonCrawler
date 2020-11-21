package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.*;
import java.util.stream.Collectors;

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

    Room(Tile[][] roomTiles, TileType targetType)
    {
        super(roomTiles);

        xPos = roomTiles[0][0].getX();
        yPos = roomTiles[0][0].getY();

        for (int i = 0; i < getWidth(); i++)
        {
            setTileTypes(roomTiles[i], targetType);
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

    public void lockRoomTiles()
    {
        getRoomInnerTiles().forEach(tile -> tile.setType(TileType.ROOM_LOCKED));
    }

    public void unlockRoomTiles()
    {
        getRoomInnerTiles().forEach(tile -> tile.setType(TileType.ROOM));
    }

    public List<Tile> getRoomInnerTiles()
    {
        Set<Tile> wallTiles = new HashSet<>();
        Arrays.stream(walls).forEach(wall -> wallTiles.addAll(Arrays.asList(wall.getWallTiles())));

        return getTilesList().stream()
                .filter(tile -> !wallTiles.contains(tile))
                .collect(Collectors.toList());
    }

    public List<Tile> getWallTiles()
    {
        List<Tile> wallTiles = new ArrayList<>();
        Arrays.stream(walls).forEach(wall -> wallTiles.addAll(Arrays.asList(wall.getWallTiles())));
        return wallTiles.stream().distinct().collect(Collectors.toList());
    }

    public void hideRoom()
    {
        getRoomInnerTiles().forEach(tile -> tile.setType(TileType.ROOM_HIDDEN));
    }

    public void linkToStage(Stage stage)
    {
        Arrays.stream(walls).forEach(wall -> wall.linkToStage(stage));
        this.setDoor(stage.getTile(this.getDoor()));

        replaceTiles(stage);
        updateTilesList();
    }

    public void replaceTiles(Stage stage)
    {
        RoomBuilder rb = new RoomBuilder(stage);
        Tile roomSeedInStage = stage.getTile(this.xPos, this.yPos);
        Tile[][] rectangleFromStage = rb.getTilesRectangle(roomSeedInStage, this.getWidth(), this.getHeight(), BuildDirection.RD);
        setTiles(rectangleFromStage);
    }
}
