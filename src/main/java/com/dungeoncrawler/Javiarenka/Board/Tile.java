package com.dungeoncrawler.Javiarenka.Board;

public class Tile {
    private boolean isRoomTile;
    private boolean isOccupied;
    private boolean isDifficultTerrain;
    private boolean isDoor;
    private boolean isTrap;
    private int occupyingCharacterId;
    private int occupyingObstacleId;
    private String tileType;

    private final String TILE_TYPE_GRASS = "grass";
    private final String TILE_TYPE_DIRT = "dirt";
    private final String TILE_TYPE_STONE = "stone";
    private final String TILE_TYPE_WET = "wet";
    private final String TILE_TYPE_WATER = "water";
    private final String TILE_TYPE_WALL = "wall";

    public boolean isRoomTile() {
        return isRoomTile;
    }

    public void setRoomTile(boolean roomTile) {
        isRoomTile = roomTile;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isDifficultTerrain() {
        return isDifficultTerrain;
    }

    public void setDifficultTerrain(boolean difficultTerrain) {
        isDifficultTerrain = difficultTerrain;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public void setDoor(boolean door) {
        isDoor = door;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public void setTrap(boolean trap) {
        isTrap = trap;
    }

    public int getOccupyingCharacterId() {
        return occupyingCharacterId;
    }

    public void setOccupyingCharacterId(int occupyingCharacterId) {
        this.occupyingCharacterId = occupyingCharacterId;
    }

    public int getOccupyingObstacleId() {
        return occupyingObstacleId;
    }

    public void setOccupyingObstacleId(int occupyingObstacleId) {
        this.occupyingObstacleId = occupyingObstacleId;
    }

    public String getTileType() {
        return tileType;
    }

    public void setTileType(String tileType) {
        this.tileType = tileType;
    }

    public String getTILE_TYPE_GRASS() {
        return TILE_TYPE_GRASS;
    }

    public String getTILE_TYPE_DIRT() {
        return TILE_TYPE_DIRT;
    }

    public String getTILE_TYPE_STONE() {
        return TILE_TYPE_STONE;
    }

    public String getTILE_TYPE_WET() {
        return TILE_TYPE_WET;
    }

    public String getTILE_TYPE_WATER() {
        return TILE_TYPE_WATER;
    }

    public String getTILE_TYPE_WALL() {
        return TILE_TYPE_WALL;
    }
}
