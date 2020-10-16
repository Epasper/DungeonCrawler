package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class TileNavigator
{
    private Stage stage;

    public TileNavigator(Stage stage)
    {
        this.stage = stage;
    }

    public Tile getNextTile(Tile tile, Direction dir)
    {
        Tile nextTile = new Tile();
        try
        {
            switch (dir)
            {
                case DOWN:
                    nextTile = stage.getTile(tile.getX(), tile.getY() + 1);
                    break;

                case RIGHT:
                    nextTile = stage.getTile(tile.getX() + 1, tile.getY());
                    break;

                case UP:
                    nextTile = stage.getTile(tile.getX(), tile.getY() - 1);
                    break;

                case LEFT:
                    nextTile = stage.getTile(tile.getX() - 1, tile.getY());
                    break;
            }
        } catch (Exception e)
        {
            System.out.println("Next tile unavailable. mapGenerator.Stage Limit reached.");
            //e.printStackTrace();
        }
        return nextTile;
    }

    public Tile[] getNeighboringTiles(Tile middleTile)
    {
        Tile[] outputArray = new Tile[Direction.SIZE];
        int i = 0;
        for (Direction dir : Direction.VALUES)
        {
            outputArray[i] = getNextTile(middleTile, dir);
            i++;
        }
        return outputArray;
    }

    public List<Tile> getSurroundingTiles(Tile middleTile)
    {
        List<Tile> outputList = new ArrayList<>();
        Map<Direction, Tile> crossTiles = getNeighboringTilesWithDirections(middleTile);

        outputList.addAll(crossTiles.values());
        outputList.add(getNextTile(crossTiles.get(Direction.UP), Direction.LEFT));
        outputList.add(getNextTile(crossTiles.get(Direction.UP), Direction.RIGHT));
        outputList.add(getNextTile(crossTiles.get(Direction.DOWN), Direction.LEFT));
        outputList.add(getNextTile(crossTiles.get(Direction.DOWN), Direction.RIGHT));

        return outputList;
    }

    public List<Tile> getNeighboringTiles(Tile middleTile, TileType type)
    {
        List<Tile> neighboursList = new ArrayList<>(Arrays.asList(getNeighboringTiles(middleTile)));
        List<Tile> neighboursOfType = neighboursList.stream().filter(tile -> tile.getType() == type).collect(Collectors.toList());

        return neighboursOfType;
    }

    public int numberOfTilesOfTypeIn(TileType type, Tile[] tileArray)
    {
        int i = 0;
        for (Tile tile : tileArray)
        {
            if (tile.getType() == type) i++;
        }
        return i;
    }

    public int numberOfNeighborsOfType(TileType type, Tile checkedTile)
    {
        Tile[] neighboringTiles = getNeighboringTiles(checkedTile);
        return numberOfTilesOfTypeIn(type, neighboringTiles);
    }

    public int numberOfSurroundingOfType(TileType type, Tile checkedTile)
    {
        Tile[] surroundingTiles = new Tile[8];
        getSurroundingTiles(checkedTile).toArray(surroundingTiles);

        return numberOfTilesOfTypeIn(type, surroundingTiles);
    }

    public int numberOfNeighborsOfType(TileType type, Tile[] checkedTiles)
    {
        int counter = 0;
        for (Tile tile : checkedTiles)
        {
            Tile[] neighboringTiles = getNeighboringTiles(tile);
            counter += numberOfTilesOfTypeIn(type, neighboringTiles);
        }

        return counter;
    }

    boolean isIntersection(Tile checkedTile)
    {
        Tile[] neighboringTiles = getNeighboringTiles(checkedTile);
        return numberOfTilesOfTypeIn(TileType.CORRIDOR, neighboringTiles) >= 3;
    }

    Tile[] getCorridorIntersectionTiles()
    {
        List<Tile> corridorTiles = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CORRIDOR)));
        List<Tile> intersectionTiles = new ArrayList<>();
        Tile[] neighboringTiles;

        for (Tile tile : corridorTiles)
        {
            neighboringTiles = getNeighboringTiles(tile);
            if (numberOfTilesOfTypeIn(TileType.CORRIDOR, neighboringTiles) >= 3)
            {
                intersectionTiles.add(tile);
            }
        }

        Tile[] outputArray = new Tile[intersectionTiles.size()];
        intersectionTiles.toArray(outputArray);
        return outputArray;
    }

    Set<Tile> getTouchingTilesOfType(Tile sourceTile, TileType targetType)
    {

        Set<Tile> touchingTiles = new HashSet<>();
        touchingTiles.add(sourceTile);
        Set<Tile> outputSet = new HashSet<>(touchingTiles);
        Set<Tile> neighboringTilesOfSameType = new HashSet<>();

        int touchingTilesNo = 1;
        int prevTouchingTilesNo;

        do
        {
            prevTouchingTilesNo = touchingTilesNo;
            touchingTiles.forEach(tile -> tile.setType(TileType.BREADCRUMB));

            for (Tile tile : touchingTiles)
            {
                neighboringTilesOfSameType.addAll(getNeighboringTiles(tile, targetType));
            }

            neighboringTilesOfSameType.forEach(tile -> tile.setType(TileType.CUTOFF));
            outputSet.addAll(neighboringTilesOfSameType);
            neighboringTilesOfSameType.removeAll(touchingTiles);
            touchingTilesNo = outputSet.size();
            touchingTiles = new HashSet<>(neighboringTilesOfSameType);
            neighboringTilesOfSameType.clear();
        } while (touchingTilesNo > prevTouchingTilesNo);

        outputSet.forEach(tile -> tile.setType(targetType));
        return outputSet;
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
            outputMap.put(dir, getNextTile(tile, dir));
        }
        return outputMap;
    }

    private boolean evaluatePick(Tile chosenTile)
    {
        Set<Tile> connectedTiles = getTouchingTilesOfSameType(chosenTile);
        connectedTiles.forEach(tile -> tile.setType(TileType.CUTOFF));
        List<Tile> unreachableTiles = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CORRIDOR)));

        //uwaga: generowanie HTMLa tutaj może powodować się zawieszanie programu
        //MapGeneratorService.buildDebugSite(stage);
        //MapGeneratorService.buildDebugSite(stage);
        //System.out.println("evaluatePick");

        if (unreachableTiles.size() > connectedTiles.size())
        {
            return true;
        }
        else
        {
            //connectedTiles.forEach(tile -> tile.setType(TileType.CORRIDOR));
            new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CUTOFF))).forEach(tile -> tile.setType(TileType.CORRIDOR));
            return false;
        }
        //return unreachableTiles.size() > connectedTiles.size();
    }

    public Tile[] getUnreachableCorridorTiles() //throws IOException
    {
        //Tile currentTile = stage.getFirstTileOfType(TileType.CORRIDOR);
        Tile currentTile;
        //int tries = 0;

        do
        {
            currentTile = stage.getRandomTileOfType(TileType.CORRIDOR);
            //tries++;
//            if (tries > 10)
//            {
//                System.out.println("break!");
//            }
        } while (evaluatePick(currentTile));
        //MapGeneratorService.buildDebugSite(stage);
        //MapGeneratorService.buildDebugSite(stage);

        Tile scannedTile;
        List<Tile> walkedTiles = new ArrayList<>();
        List<Tile> possiblePathTiles = new ArrayList<>();
        int possiblePathsNumber = 0;

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
    }

    public Map<Integer, Set<Tile>> getTouchingTilesCascade(Tile sourceTile, TileType targetType)
    {
        Map<Integer, Set<Tile>> outputMap = new HashMap<>();
        int cascadeCounter = 0;
        Set<Tile> currentCascade;
        Set<Tile> nextCascade = new HashSet<>();
        Set<Tile> tempCascade = new HashSet<>();
        Set<Tile> alreadyDone = new HashSet<>();

        currentCascade = new HashSet<>(getNeighboringTiles(sourceTile, targetType));

        outputMap.put(cascadeCounter, currentCascade);
        alreadyDone.addAll(currentCascade);
        cascadeCounter++;

        while (currentCascade.size() > 0)
        {
            currentCascade.forEach(tile -> tempCascade.addAll(new HashSet<>(getNeighboringTiles(tile, targetType))));
            nextCascade = tempCascade.stream().filter(tile -> !alreadyDone.contains(tile)).collect(Collectors.toSet());
            outputMap.put(cascadeCounter, nextCascade);
            currentCascade = nextCascade;
            alreadyDone.addAll(currentCascade);
            cascadeCounter++;
        }

        if (outputMap.get(cascadeCounter - 1).size() == 0) outputMap.remove(cascadeCounter - 1);

        return outputMap;
    }

    Set<Tile> getTouchingTilesOfTypeX(Tile sourceTile, TileType targetType)
    {

        Set<Tile> touchingTiles = new HashSet<>();
        touchingTiles.add(sourceTile);
        Set<Tile> outputSet = new HashSet<>(touchingTiles);
        Set<Tile> neighboringTilesOfSameType = new HashSet<>();

        int touchingTilesNo = 1;
        int prevTouchingTilesNo;

        do
        {
            prevTouchingTilesNo = touchingTilesNo;
            touchingTiles.forEach(tile -> tile.setType(TileType.BREADCRUMB));

            for (Tile tile : touchingTiles)
            {
                neighboringTilesOfSameType.addAll(getNeighboringTiles(tile, targetType));
            }

            neighboringTilesOfSameType.forEach(tile -> tile.setType(TileType.CUTOFF));
            outputSet.addAll(neighboringTilesOfSameType);
            neighboringTilesOfSameType.removeAll(touchingTiles);
            touchingTilesNo = outputSet.size();
            touchingTiles = new HashSet<>(neighboringTilesOfSameType);
            neighboringTilesOfSameType.clear();
        } while (touchingTilesNo > prevTouchingTilesNo);

        outputSet.forEach(tile -> tile.setType(targetType));
        return outputSet;
    }
}
