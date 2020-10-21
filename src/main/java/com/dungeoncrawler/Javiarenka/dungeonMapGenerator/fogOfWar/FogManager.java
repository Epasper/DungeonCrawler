package com.dungeoncrawler.Javiarenka.dungeonMapGenerator.fogOfWar;

import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.PartyAvatar;
import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.Stage;
import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.Tile;
import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.TileNavigator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO: tworzenie fog of war w htmlu poprzez nowe Divy mocno spowalnia html. Spróbować zarządzać opacity istniejących divów poprzez dodawanie/usuwanie im dodatkowej klasy.
// pod grid trzeba dodać duży div z czarnym backgroundem

public class FogManager
{
    private transient Stage stage;
    private transient TileNavigator tileNav;
    private transient PartyAvatar party;
    private List<Tile> visibleTiles = new ArrayList<>();

    public FogManager(Stage stage)
    {
        this.stage = stage;
        this.tileNav = new TileNavigator(stage);
    }
//
//    public TileNavigator getTileNav()
//    {
//        return tileNav;
//    }
//
//    public void setTileNav(TileNavigator tileNav)
//    {
//        this.tileNav = tileNav;
//    }

    public PartyAvatar getParty()
    {
        return party;
    }

    public void setParty(PartyAvatar party)
    {
        this.party = party;
        updateVisibility();
    }

    //==============================================================================================
    //==============================================================================================
    //==============================================================================================

    private double getDistanceToParty(Tile tile)
    {
        int horizontalDistance = Math.abs(party.getX() - tile.getX());
        int verticalDistance = Math.abs(party.getY() - tile.getY());

        double distance = Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);
        return distance;
    }

    public void updateVisibility()
    {
        List<Tile> previouslyVisibleTiles = new ArrayList<>(visibleTiles);
        visibleTiles = evaluatePartyVisibilityCircle();

        previouslyVisibleTiles = previouslyVisibleTiles.stream().filter(tile -> visibleTiles.indexOf(tile) == -1).collect(Collectors.toList());
        previouslyVisibleTiles.forEach(tile -> tile.setVisibility(0));

        visibleTiles.addAll(previouslyVisibleTiles);
    }

    public List<Tile> evaluatePartyVisibilityCircle()
    {
        List<Tile> reachedTiles = tileNav.getSurroundingTilesFullSquare(party.getOccupiedTile(), FogSettings.BORDER_VISIBILITY_RADIUS);

        double x1 = FogSettings.FULL_VISIBILITY_RADIUS;
        double y1 = 1;

        double x2 = FogSettings.BORDER_VISIBILITY_RADIUS;
        double y2 = 0;

        double a;
        double b;

        a = (y2 - y1) / (x2 - x1);
        b = y1 - (a * x1);

        reachedTiles.forEach(tile -> {
            double distance = getDistanceToParty(tile);
            if (distance <= FogSettings.FULL_VISIBILITY_RADIUS)
            {
                tile.setVisibility(1);
            }
            else if (distance > FogSettings.BORDER_VISIBILITY_RADIUS)
            {
                tile.setVisibility(0);
            }
            else
            {
                tile.setVisibility(a * distance + b);
                //System.out.println("Visibility of tile " + tile.getX() + "/" + tile.getY() + " is: " + (a * distance + b));
            }
        });

        return reachedTiles.stream().filter(tile -> tile.getVisibility() > 0).collect(Collectors.toList());
    }

    public List<Tile> getVisibleTiles()
    {
//        return Arrays.stream(stage.getTilesAsOneDimensionalArray()).filter(tile -> tile.getVisibility() > 0).collect(Collectors.toList());
        return visibleTiles;
    }

    public void saveThisFogManager()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try
        {
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/" + "fogManager.txt");
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
