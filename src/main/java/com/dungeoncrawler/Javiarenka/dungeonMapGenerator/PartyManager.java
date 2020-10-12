package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

public class PartyManager
{
    private Stage stage;
    private final int partyLimit = 2;
    private PartyAvatar party;

    public PartyManager(Stage stage)
    {
        this.stage = stage;
    }

    public PartyAvatar getParty()
    {
        return party;
    }

    public void setParty(PartyAvatar party)
    {
        this.party = party;
    }

//    private int getNextId()
//    {
//        return party.size() + 1;
//    }

//    public PartyAvatar getHeroById(int targetId)
//    {
//        return heroes.stream()
//                .filter(partyAvatar -> partyAvatar.getId() == targetId)
//                .findFirst()
//                .orElse(null);
//    }
    private boolean isPartySpawned()
    {
        return party != null;
    }

    public boolean isPartySpawnable(Tile targetTile)
    {
        if (!targetTile.isWalkable()) return false;
        if (targetTile.isOccupied()) return false;
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

    public void moveParty(Tile targetTile)
    {
        if(targetTile.isWalkable() && !targetTile.isOccupied())
        party.setOccupiedTile(targetTile);
    }

    public void moveParty(int xCoord, int yCoord)
    {
        moveParty(stage.getTile(xCoord, yCoord));
    }
}
