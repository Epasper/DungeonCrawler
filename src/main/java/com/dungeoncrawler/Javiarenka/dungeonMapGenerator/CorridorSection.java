package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class CorridorSection
{
    private int length;
    private LinkedList<Tile> section = new LinkedList<>();
    private Stage stage;
    private TileNavigator tileNav;

    public CorridorSection(Set<Tile> entrySet, Stage inputStage)
    {
        stage = inputStage;
        tileNav = new TileNavigator(stage);
        Tile[] neighboringTiles;
        Set<Tile> set = new HashSet<>();

        Tile firstTile;

        for (Tile tile : entrySet)
        {
            neighboringTiles = tileNav.getNeighboringTiles(tile);
            if (tileNav.numberOfTilesOfTypeIn(TileType.CORRIDOR, neighboringTiles) == 1)
            {
                firstTile = tile;
                section.add(firstTile);
                set.add(firstTile);
                break;
            }
        }

        do
        {
            neighboringTiles = tileNav.getNeighboringTiles(section.getLast());
            for (Tile tile : neighboringTiles)
            {
                if (tile.getType() == TileType.CORRIDOR)
                {
                    set.add(tile);
                }
                if (set.size() > section.size())
                {
                    section.add(tile);
                }
            }
        } while (entrySet.size() > section.size());

        length = section.size();
    }

    public CorridorSection (Tile seedTile, Stage inputStage)
    {
        stage = inputStage;
        tileNav = new TileNavigator(stage);

        Set<Tile> set = new HashSet<>();
        Tile[] neighboringTiles;

        section.add(seedTile);
        set.add(seedTile);
        boolean tileWasAdded;

        do
        {
            neighboringTiles = tileNav.getNeighboringTiles(section.getLast());
            tileWasAdded = false;
            for (Tile tile : neighboringTiles)
            {
                if (tile.getType() == TileType.CORRIDOR)
                {
                    set.add(tile);
                }
                if (set.size() > section.size())
                {
                    section.add(tile);
                    tileWasAdded = true;
                }
            }
        } while (tileWasAdded);

        length = section.size();
    }

    public int getLength()
    {
        return length;
    }

    public LinkedList<Tile> getSection()
    {
        return section;
    }

    public void reverse()
    {

    }
}
