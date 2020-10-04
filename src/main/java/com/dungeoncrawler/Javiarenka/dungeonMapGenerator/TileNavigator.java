package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.*;

public class TileNavigator
{
    private Stage stage;

    public TileNavigator(Stage stage)
    {
        this.stage = stage;
    }

    private int numberOfTilesOfTypeIn(TileType type, Tile[] tileArray)
    {
        int i = 0;
        for (Tile tile : tileArray)
        {
            if (tile.getType() == type) i++;
        }
        return i;
    }

    boolean isIntersection(Tile checkedTile)
    {
        Tile[] neighboringTiles = stage.getNeighboringTiles(checkedTile);
        return numberOfTilesOfTypeIn(TileType.CORRIDOR, neighboringTiles) >= 3;
    }

    Tile[] getCorridorIntersectionTiles()
    {
        List<Tile> corridorTiles = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CORRIDOR)));
        List<Tile> intersectionTiles = new ArrayList<>();
        Tile[] neighboringTiles;

        for (Tile tile : corridorTiles)
        {
            neighboringTiles = stage.getNeighboringTiles(tile);
            if (numberOfTilesOfTypeIn(TileType.CORRIDOR, neighboringTiles) >= 3)
            {
                intersectionTiles.add(tile);
            }
        }

        Tile[] outputArray = new Tile[intersectionTiles.size()];
        intersectionTiles.toArray(outputArray);
        return outputArray;
    }

    private Set<Tile> getTouchingTilesOfType(Set<Tile> entrySet, TileType targetType)
    {
        Set<Tile> outputSet = new HashSet<>();
        Set<Tile> sameTouchingTiles = new HashSet<>();
        outputSet.addAll(entrySet);


        for (Tile tile : outputSet)
        {
            Tile[] neighboringTiles = stage.getNeighboringTiles(tile);
            for (Tile neighboringTile : neighboringTiles)
            {
                if (neighboringTile.getType() == targetType)
                {
                    sameTouchingTiles.add(neighboringTile);
                }
            }
        }

        outputSet.addAll(sameTouchingTiles);

        if (outputSet.size() == entrySet.size())
        {
            return outputSet;
        }
        else
        {
            return getTouchingTilesOfType(outputSet, targetType);
        }
    }

    Set<Tile> getTouchingTilesOfType(Tile sourceTile, TileType targetType)
    {
        Set<Tile> touchingTilesOfType = new HashSet<>();
        touchingTilesOfType.add(sourceTile);

        touchingTilesOfType = getTouchingTilesOfType(touchingTilesOfType, targetType);

        return touchingTilesOfType;
    }

    Set<Tile> getTouchingTilesOfSameType(Tile sourceTile)
    {
        return getTouchingTilesOfType(sourceTile, sourceTile.getType());
    }

    public Tile getNextTileLooped(Tile tile, BuildDirection buildDir)
    {
        Tile nextTile = new Tile();
        try
        {
            switch (buildDir)
            {

                case RD:
                    if (tile.getX() == stage.getWidth() - 1) //jeśli jest na prawym skraju planszy
                    {
                        if (tile.getY() == stage.getHeight() - 1) //jeśli jest też na samym dole planszy
                        {
                            nextTile = stage.getTile(0, 0); //resetuj w lewy górny róg
                        }
                        else
                        {
                            nextTile = stage.getTile(0, tile.getY() + 1);
                        }
                    }
                    else
                    {
                        nextTile = stage.getTile(tile.getX() + 1, tile.getY());
                    }
                    break;

                case LD:
                    if (tile.getX() == 0) //jeśli jest na lewym skraju planszy
                    {
                        if (tile.getY() == stage.getHeight() - 1) //jeśli jest też na samym dole planszy
                        {
                            nextTile = stage.getTile(stage.getWidth() - 1, 0); //resetuj w prawy górny róg
                        }
                        else
                        {
                            nextTile = stage.getTile(stage.getWidth() - 1, tile.getY() + 1);
                        }
                    }
                    else
                    {
                        nextTile = stage.getTile(tile.getX() - 1, tile.getY());
                    }
                    break;

                case RU:
                    if (tile.getX() == stage.getWidth() - 1) //jeśli jest na prawym skraju planszy
                    {
                        if (tile.getY() == 0) //jeśli jest też na samej górze planszy
                        {
                            nextTile = stage.getTile(0, stage.getHeight() - 1); //resetuj w lewy dolny róg
                        }
                        else
                        {
                            nextTile = stage.getTile(0, tile.getY() - 1);
                        }
                    }
                    else
                    {
                        nextTile = stage.getTile(tile.getX() + 1, tile.getY());
                    }
                    break;

                case LU:
                    if (tile.getX() == 0) //jeśli jest na lewym skraju planszy
                    {
                        if (tile.getY() == 0) //jeśli jest też na samej górze planszy
                        {
                            nextTile = stage.getTile(stage.getWidth() - 1, stage.getHeight() - 1); //resetuj w prawy dolny róg
                        }
                        else
                        {
                            nextTile = stage.getTile(stage.getWidth() - 1, tile.getY() - 1);
                        }
                    }
                    else
                    {
                        nextTile = stage.getTile(tile.getX() - 1, tile.getY());
                    }
                    break;

            }
        } catch (Exception e)
        {
            System.out.println("Next tile unavailable. Stage Limit reached.");
            //e.printStackTrace();
        }
        return nextTile;
    }

    public Tile getRandomTileFromList(ArrayList<Tile> list)
    {
        Random rand = new Random();
        int randomIndex = 0;
        try
        {
            randomIndex = rand.nextInt(list.size());
        } catch (Exception e)
        {
            //e.printStackTrace();
            return null;
        }
        return list.get(randomIndex);
    }

    private Map<Direction, Tile> getNeighboringTilesWithDirections(Tile tile)
    {
        Map<Direction, Tile> outputMap = new HashMap<>();

        for (Direction dir : Direction.values())
        {
            outputMap.put(dir, stage.getNextTile(tile, dir));
        }
        return outputMap;
    }

    private boolean evaluatePick(Tile chosenTile)
    {
        Set<Tile> connectedTiles = getTouchingTilesOfSameType(chosenTile);
        connectedTiles.forEach(tile -> tile.setType(TileType.OBSTRUCTION));
        List<Tile> unreachableTiles = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CORRIDOR)));

        MapGeneratorService.buildDebugSite(stage);
        MapGeneratorService.buildDebugSite(stage);
        connectedTiles.forEach(tile -> tile.setType(TileType.CORRIDOR));
        return unreachableTiles.size() > connectedTiles.size();
    }

    public Tile[] getUnreachableCorridorTiles() //throws IOException
    {
        //Tile currentTile = stage.getFirstTileOfType(TileType.CORRIDOR);
        Tile currentTile;
        do
        {
            currentTile = stage.getRandomTileOfType(TileType.CORRIDOR);
        } while (evaluatePick(currentTile));

        Tile scannedTile;
        List<Tile> walkedTiles = new ArrayList<>();
        List<Tile> possiblePathTiles = new ArrayList<>();
        int possiblePathsNumber = 0;

        //TODO: wygląda na to, że na bardzo dużych mapach może powstać pokój z korytarzem naokoło niepołączonym z resztą labiryntu
        // co zawsze będzie zwracało ślepy zaułek

        do
        {
            walkedTiles.add(currentTile);
            if (possiblePathTiles.size() > 0)
            {
                possiblePathTiles.remove(possiblePathTiles.size() - 1);
            }

            currentTile.setType(TileType.WALKED);
            //MapGeneratorService.buildDebugSite(stage);
            //MapGeneratorService.buildDebugSite(stage);

            Map<Direction, Tile> neighboringTiles = getNeighboringTilesWithDirections(currentTile);
            possiblePathsNumber = 0;
            for (Direction dir : neighboringTiles.keySet())
            {
                scannedTile = neighboringTiles.get(dir);
                if (scannedTile.getType() == TileType.CORRIDOR)
                {
                    possiblePathTiles.add(scannedTile);
                    scannedTile.setType(TileType.BREADCRUMB);
                    possiblePathsNumber++;
                }
                else if (scannedTile.getType() == TileType.BREADCRUMB)
                {
                    possiblePathTiles.remove(scannedTile);
                    possiblePathTiles.add(scannedTile);
                    //niczego tu nie zmienia, tylko wstawia zeskanowany breadcrumb na koniec listy
                }
            }

            if (possiblePathTiles.size() > 0)
            {
                currentTile = possiblePathTiles.get(possiblePathTiles.size() - 1);
                currentTile.setType(TileType.CURRENT_PATH);

                if (possiblePathsNumber > 1)    //w razie dojścia do skrzyżowania
                {
                    //MapGeneratorService.buildDebugSite(stage);
                    //MapGeneratorService.buildDebugSite(stage);
                }
                //possiblePathTiles.remove(possiblePathTiles.size() - 1);
            }
            //MapGeneratorService.buildDebugSite(stage);
            //MapGeneratorService.buildDebugSite(stage);

        } while (possiblePathTiles.size() > 0);

        walkedTiles.add(currentTile);
        currentTile.setType(TileType.WALKED);

        //MapGeneratorService.buildDebugSite(stage);
        //MapGeneratorService.buildDebugSite(stage);

        Tile[] leftoverCorridorTiles = stage.getTilesOfType(TileType.CORRIDOR);

        for (Tile walkedTile : walkedTiles)
        {
            walkedTile.setType(TileType.CORRIDOR);
        }
        //MapGeneratorService.buildDebugSite(stage);
        //MapGeneratorService.buildDebugSite(stage);

        //System.out.println("Nie każde pole korytarza jest dostępne dla zwiedzających xD");
        return leftoverCorridorTiles;

        //TODO: może dojść do sytuacji, gdzie analizowane pole będzie tak odgrodzone od reszty mapy, że będzie bardzo malutkie - w tym przypadku
        // przydałoby się analizować pozostałe, większe pole
        // Ewentualnie, zamiast zawsze pierwszego wolneg pola do analizy, wybierać randomowe pole

    }
}
