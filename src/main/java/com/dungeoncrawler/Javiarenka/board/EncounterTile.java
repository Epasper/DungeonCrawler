package com.dungeoncrawler.Javiarenka.board;

public class EncounterTile {
    private boolean isRoomTile;
    private boolean isOccupied;
    private boolean isDifficultTerrain;
    private boolean isDoor;
    private boolean isTrap;
    private int occupyingCharacterId;
    private int occupyingObstacleId;
    private EncounterTileType tileType;

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

    public EncounterTileType getTileType() {
        return tileType;
    }

    public void setTileType(EncounterTileType tileType) {
        this.tileType = tileType;
    }

}
