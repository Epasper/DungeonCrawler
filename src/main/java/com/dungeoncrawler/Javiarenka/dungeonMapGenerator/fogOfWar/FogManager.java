package com.dungeoncrawler.Javiarenka.dungeonMapGenerator.fogOfWar;

import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.PartyAvatar;
import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.Stage;
import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.Tile;
import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.TileNavigator;

import java.util.List;

public class FogManager
{
    private Stage stage;
    private TileNavigator tileNav;
    private PartyAvatar party;

    public FogManager(Stage stage)
    {
        this.stage = stage;
        this.tileNav = new TileNavigator(stage);
    }

    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public TileNavigator getTileNav()
    {
        return tileNav;
    }

    public void setTileNav(TileNavigator tileNav)
    {
        this.tileNav = tileNav;
    }

    public PartyAvatar getParty()
    {
        return party;
    }

    public void setParty(PartyAvatar party)
    {
        this.party = party;
        evaluatePartyVisibilityCircle();
    }

    //==============================================================================================
    //==============================================================================================
    //==============================================================================================

    private double getDistanceToParty(Tile tile)
    {
        int horizontalDistance = Math.abs(party.getX() - tile.getX());
        int verticalDistance = Math.abs(party.getY() - tile.getY());

        double distance = Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);
        System.out.println("Distance to tile " + tile.getX() + "/" + tile.getY() + " is: " + distance);
        return distance;
    }

    public void evaluatePartyVisibilityCircle()
    {
        List<Tile> reachedTiles = tileNav.getSurroundingTilesFullSquare(party.getOccupiedTile(), FogSettings.PARTY_RADIUS);
        reachedTiles.forEach(tile -> {
            getDistanceToParty(tile);
        });
    }

}
