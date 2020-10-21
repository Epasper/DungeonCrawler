package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PartyManager
{
    private transient Stage stage;
    private transient TileNavigator tileNav;
    private PartyAvatar party;

    public PartyManager(){};

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

    public PartyAvatar spawnParty(Tile targetTile, Direction facingDirection)
    {
        party = new PartyAvatar(targetTile);
        targetTile.setOccupied(true);
        party.setDirection(facingDirection);
        return party;
    }

    public PartyAvatar spawnParty(PartyAvatar givenParty)
    {
        party = new PartyAvatar(stage.getTile(givenParty.getOccupiedTile().getX(), givenParty.getOccupiedTile().getY()));
        party.getOccupiedTile().setOccupied(true);
        party.setDirection(givenParty.getDirection());
        return party;
    }

    public boolean teleportParty(Tile targetTile)
    {
        if(targetTile.isWalkable())
        {
            party.getOccupiedTile().setOccupied(false);
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
        if(party.getDirection() != dir) {
            party.setDirection(dir);
            return false;
        }

        Tile targetTile = tileNav.getNextTile(party.getOccupiedTile(), dir);
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
            targetTile = tileNav.getNextTile(party.getOccupiedTile(), dir);
            outputMap.put(dir, targetTile.isWalkable());
        }
        return outputMap;
    }

    public void saveThisPartyManager()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try
        {
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/" + "partyManager.txt");
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
