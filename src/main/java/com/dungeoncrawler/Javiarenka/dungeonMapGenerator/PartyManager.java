package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.HashMap;
import java.util.Map;

public class PartyManager
{
    private Stage stage;
    private TileNavigator tileNav;
    private PartyAvatar party;

    public PartyManager(Stage stage)
    {
        this.stage = stage;
        this.tileNav = new TileNavigator(stage);
    }

    public PartyAvatar getParty()
    {
        return party;
    }

    public void setParty(PartyAvatar party)
    {
        this.party = party;
    }

    private boolean isPartySpawned()
    {
        return party != null;
    }

    public boolean isPartySpawnable(Tile targetTile)
    {
        if (!targetTile.isWalkable()) return false;
        if (isPartySpawned()) return false;
        return true;
    }

    public boolean isPartySpawnable(int xCoord, int yCoord)
    {
        return isPartySpawnable(stage.getTile(xCoord, yCoord));
    }

    public PartyAvatar spawnParty(int xCoord, int yCoord)
    {
        return spawnParty(stage.getTile(xCoord, yCoord));
    }

    public PartyAvatar spawnParty(Tile targetTile)
    {
        party = new PartyAvatar(targetTile);
        targetTile.setOccupied(true);
        return party;
    }

    public boolean teleportParty(Tile targetTile)
    {
        if(targetTile.isWalkable())
        {
            party.occupiedTile.setOccupied(false);
            party.setOccupiedTile(targetTile);
            return true;
        }
        return false;
    }


    public boolean teleportParty(int xCoord, int yCoord)
    {
        return teleportParty(stage.getTile(xCoord, yCoord));
    }

    public boolean movePartyOneStep(Direction dir)
    {
        if(party.direction != dir) {
            party.setDirection(dir);
            return false;
        }

        Tile targetTile = tileNav.getNextTile(party.occupiedTile, dir);
        party.setDirection(dir);
        if (!targetTile.isWalkable()) return false;
        return teleportParty(targetTile);
    }

    public Map<Direction, Boolean> evaluateMovability()
    {
        Map<Direction, Boolean> outputMap = new HashMap<>();
        Tile targetTile;

        for(Direction dir : Direction.VALUES)
        {
            targetTile = tileNav.getNextTile(party.occupiedTile, dir);
            outputMap.put(dir, targetTile.isWalkable());
        }
        return outputMap;
    }
}
