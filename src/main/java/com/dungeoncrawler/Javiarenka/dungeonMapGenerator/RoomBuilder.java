package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.io.IOException;
import java.util.*;

public class RoomBuilder
{
    private Stage stage;
    private TileNavigator tileNav;
    private Tile[] seeds = new Tile[BuildDirection.SIZE];

    public RoomBuilder(Stage stage)
    {
        this.stage = stage;
        this.tileNav = new TileNavigator(stage);

        seeds[0] = stage.getTile(2, 2); //RD
        seeds[1] = stage.getTile(stage.getWidth() - 3, 2); //LD
        seeds[2] = stage.getTile(2, stage.getHeight() - 3); //RU
        seeds[3] = stage.getTile(stage.getWidth() - 3, stage.getHeight() - 3); //LU
    }

    private Tile getSeedByDirection(BuildDirection buildDirection)
    {
        return seeds[buildDirection.ordinal()];
    }

    private void setSeedByDirection(Tile newSeedTile, BuildDirection buildDirection)
    {
        seeds[buildDirection.ordinal()] = newSeedTile;
    }

    public int getMaxRoomWidth()
    {
        int maxRoomWidth = (StageSettings.roomWidthPercentage * stage.getWidth()) / 100;
        if (maxRoomWidth < StageSettings.minRoomWidth)
        {
            maxRoomWidth = StageSettings.minRoomWidth;
        }
        return maxRoomWidth;
    }

    public int getMaxRoomHeight()
    {
        int maxRoomHeight = (StageSettings.roomHeightPercentage * stage.getHeight()) / 100;
        if (maxRoomHeight < StageSettings.minRoomHeight)
        {
            maxRoomHeight = StageSettings.minRoomHeight;
        }
        return maxRoomHeight;
    }

    public int getAvailableSpace(Tile tile, Direction dir)
    {
        int emptyTileCounter = 0;
        Tile nextTile = new Tile();

        do
        {
            try
            {
                switch (dir)
                {
                    case DOWN:
                        nextTile = stage.getNextTile(tile, Direction.DOWN);
                        break;

                    case RIGHT:
                        nextTile = stage.getNextTile(tile, Direction.RIGHT);
                        break;

                    case UP:
                        nextTile = stage.getNextTile(tile, Direction.UP);
                        break;

                    case LEFT:
                        nextTile = stage.getNextTile(tile, Direction.LEFT);
                        break;
                }

            } catch (Exception e)
            {
                //e.printStackTrace();
                return emptyTileCounter;
            }

            if (nextTile.getType() == TileType.EMPTY)
            {
                emptyTileCounter++;
                tile = nextTile;
            }
            else
            {
                return emptyTileCounter;
            }
        } while (tile.getType() == TileType.EMPTY);

        return emptyTileCounter;
    }

    Tile[][] getTilesRectangle(Tile seedTile, int rangeWidth, int rangeHeight, BuildDirection bDir)
    {
        Tile[][] outputRange = new Tile[rangeWidth][rangeHeight];

        switch (bDir)
        {
            case RD:
                for (int row = 0; row < rangeHeight; row++)
                {
                    for (int col = 0; col < rangeWidth; col++)
                    {
                        try
                        {
                            outputRange[col][row] = stage.getTile(seedTile.getX() + col, seedTile.getY() + row);
                        } catch (Exception e)
                        {
                            //e.printStackTrace();
                            outputRange[col][row] = new Tile(0, 0, TileType.ERROR);
                        }
                    }
                }
                break;

            case LD:
                for (int row = 0; row < rangeHeight; row++)
                {
                    for (int col = 0; col < rangeWidth; col++)
                    {
                        try
                        {
                            outputRange[col][row] = stage.getTile(seedTile.getX() - (rangeWidth - 1) + col, seedTile.getY() + row);
                        } catch (Exception e)
                        {
                            //e.printStackTrace();
                            outputRange[col][row] = new Tile(0, 0, TileType.ERROR);
                        }
                    }
                }
                break;

            case RU:
                for (int row = 0; row < rangeHeight; row++)
                {
                    for (int col = 0; col < rangeWidth; col++)
                    {
                        try
                        {
                            outputRange[col][row] = stage.getTile(seedTile.getX() + col, seedTile.getY() - (rangeHeight - 1) + row);
                        } catch (Exception e)
                        {
                            //e.printStackTrace();
                            outputRange[col][row] = new Tile(0, 0, TileType.ERROR);
                        }
                    }
                }
                break;

            case LU:
                for (int row = 0; row < rangeHeight; row++)
                {
                    for (int col = 0; col < rangeWidth; col++)
                    {
                        try
                        {
                            outputRange[col][row] = stage.getTile(seedTile.getX() - (rangeWidth - 1) + col, seedTile.getY() - (rangeHeight - 1) + row);
                        } catch (Exception e)
                        {
                            //e.printStackTrace();
                            outputRange[col][row] = new Tile(0, 0, TileType.ERROR);
                        }
                    }
                }
                break;
        }

        return outputRange;
    }

    private int getRectangleWidth(Tile[][] rectangle)
    {
        return rectangle.length;
    }

    private int getRectangleHeight(Tile[][] rectangle)
    {
        return rectangle[0].length;
    }

    private Tile[] getColumnOfRectangle(Tile[][] rectangle, int colNumber)
    {
        return rectangle[colNumber];
    }

    private Tile[] getRowOfRectangle(Tile[][] rectangle, int rowNumber)
    {
        int rectangleWidth = getRectangleWidth(rectangle);

        Tile[] outputRow = new Tile[rectangleWidth];
        for (int col = 0; col < rectangleWidth; col++)
        {
            outputRow[col] = rectangle[col][rowNumber];
        }
        return outputRow;
    }

    private int getAvailableWidth(Tile[] column, BuildDirection buildDir)
    {
        int availableWidth = stage.getWidth();
        for (Tile tile : column)
        {
            if (tile.getType() == TileType.EMPTY)
            {
                switch (buildDir)
                {
                    case RD:
                    case RU:
                        availableWidth = Math.min(availableWidth, getAvailableSpace(tile, Direction.RIGHT) + 1);
                        break;

                    case LD:
                    case LU:
                        availableWidth = Math.min(availableWidth, getAvailableSpace(tile, Direction.LEFT) + 1);
                        break;
                }
            }
        }

        return availableWidth;
    }

    private int getAvailableHeight(Tile[] row, BuildDirection buildDir)
    {
        int availableHeight = stage.getWidth();
        for (Tile tile : row)
        {
            switch (buildDir)
            {
                case RD:
                case LD:
                    availableHeight = Math.min(availableHeight, getAvailableSpace(tile, Direction.DOWN) + 1);
                    break;

                case RU:
                case LU:
                    availableHeight = Math.min(availableHeight, getAvailableSpace(tile, Direction.UP) + 1);
                    break;
            }
        }

        return availableHeight;
    }

    void surroundWithCorridor(Room room) //throws IOException
    {
        Tile[][] surroundingTiles = stage.getSurroundingTiles(room.getTiles());
        for (int i = 0; i < surroundingTiles.length; i++)
        {
            stage.setTileTypes(surroundingTiles[i], TileType.CORRIDOR);
        }
    }

    public boolean insertRoom(Tile seedTile, int roomWidth, int roomHeight, BuildDirection buildDirection) //throws IOException
    {
        int availableWidth;
        int availableHeight;

        Tile[][] checkedRectangle = getTilesRectangle(seedTile, roomWidth, roomHeight, buildDirection);
        Tile[] checkedColumn = new Tile[getRectangleHeight(checkedRectangle)];
        Tile[] checkedRow = new Tile[getRectangleWidth(checkedRectangle)];

        switch (buildDirection)
        {
            case RD:
                checkedColumn = getColumnOfRectangle(checkedRectangle, 0); //left side of rectangle
                checkedRow = getRowOfRectangle(checkedRectangle, 0); //top row of rectangle
                break;

            case LD:
                checkedColumn = getColumnOfRectangle(checkedRectangle, checkedRectangle.length - 1); //right side of rectangle
                checkedRow = getRowOfRectangle(checkedRectangle, 0); //top row of rectangle
                break;

            case RU:
                checkedColumn = getColumnOfRectangle(checkedRectangle, 0); //left side of rectangle
                checkedRow = getRowOfRectangle(checkedRectangle, checkedRectangle[0].length - 1); //bottom row of rectangle
                break;

            case LU:
                checkedColumn = getColumnOfRectangle(checkedRectangle, checkedRectangle.length - 1); //right side of rectangle
                checkedRow = getRowOfRectangle(checkedRectangle, checkedRectangle[0].length - 1); //bottom row of rectangle
                break;
        }

        availableWidth = getAvailableWidth(checkedColumn, buildDirection);
        availableHeight = getAvailableHeight(checkedRow, buildDirection);

        boolean roomPlaceable = false;

        if (!((availableWidth >= StageSettings.minRoomWidth) && (availableHeight >= StageSettings.minRoomHeight)))
        {
            System.out.println("Not enough space to fit any room. Tile locked in wall");
            seedTile.setType(TileType.WALL);
            return false;
        }

        int maxRoomWidth = getMaxRoomWidth();
        int maxRoomHeight = getMaxRoomHeight();

        maxRoomWidth = Math.min(maxRoomWidth, availableWidth);
        maxRoomHeight = Math.min(maxRoomHeight, availableHeight);

        maxRoomWidth = Math.min(maxRoomWidth, roomWidth);
        maxRoomHeight = Math.min(maxRoomHeight, roomHeight);

        Room room = new Room(getTilesRectangle(seedTile, maxRoomWidth, maxRoomHeight, buildDirection));
        System.out.println(room.toString());

        surroundWithCorridor(room);
        squeezeSurroundingDoubleCorridors(room);
        stage.updateRoomsSet(room);
        //stage.saveToTxt();

        return true;
    }

    private Tile[][] getRangeFromSet(Set<Tile> tilesSet)
    {
        int minX, maxX, minY, maxY;

        minX = tilesSet.stream()
                .min(Comparator.comparing(Tile::getX)).get().getX();
        maxX = tilesSet.stream()
                .max(Comparator.comparing(Tile::getX)).get().getX();
        minY = tilesSet.stream()
                .min(Comparator.comparing(Tile::getY)).get().getY();
        maxY = tilesSet.stream()
                .max(Comparator.comparing(Tile::getY)).get().getY();

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        Tile[][] outputArray = new Tile[width][height];

        for (Tile tile : tilesSet)
        {
            outputArray[tile.getX() - minX][tile.getY() - minY] = tile;
        }

        return outputArray;
    }

    public void scanTilesForRooms()
    {
        Tile seedTile;
        List<Tile> roomTilesList = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.ROOM)));

        do
        {
            Tile currentTile = stage.getFirstTileOfType(TileType.ROOM);
            seedTile = stage.getTile(currentTile.getX() - 1, currentTile.getY() - 1);

            Set<Tile> innerRoomTilesSet = tileNav.getTouchingTilesOfSameType(currentTile);

            Tile[][] innerRoomArray = getRangeFromSet(innerRoomTilesSet);
            Tile[][] outerRoomArray = stage.getSurroundingTiles(innerRoomArray);

            Room room = new Room(getTilesRectangle(seedTile, outerRoomArray.length, outerRoomArray[0].length, BuildDirection.RD));
            stage.updateRoomsSet(room);

            for (Tile tile : innerRoomTilesSet)
            {
                roomTilesList.remove(tile);
                tile.setType(TileType.ROOM_SCANNED);
            }
            try
            {
                stage.saveToTxt();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } while (roomTilesList.size() > 0);

        Tile[] scannedTiles = stage.getTilesOfType(TileType.ROOM_SCANNED);
        for (Tile tile : scannedTiles)
        {
            tile.setType(TileType.ROOM);
        }
    }


    private HashMap<BuildDirection, Tile> getCorners(Tile[][] rectangle)
    {
        HashMap<BuildDirection, Tile> outputMap = new HashMap<>();

        outputMap.put(BuildDirection.LU, rectangle[0][0]);
        outputMap.put(BuildDirection.RU, rectangle[rectangle.length][0]);
        outputMap.put(BuildDirection.LD, rectangle[0][rectangle[0].length]);
        outputMap.put(BuildDirection.RD, rectangle[rectangle.length][rectangle[0].length]);

        return outputMap;
    }

    public int getRandomNumberInRange(int min, int max)
    {

        if (min > max)
        {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public boolean insertRandomRoom(Tile seedTile, BuildDirection buildDirection) //throws IOException
    {

        int maxRoomWidth = getMaxRoomWidth();
        int maxRoomHeight = getMaxRoomHeight();

        int randRoomWidth = getRandomNumberInRange(StageSettings.minRoomWidth, maxRoomWidth);
        int randRoomHeight = getRandomNumberInRange(StageSettings.minRoomHeight, maxRoomHeight);

        return insertRoom(seedTile, randRoomWidth, randRoomHeight, buildDirection);

    }

    public Tile getRandomEmptyTile()
    {
        List<Tile> emptyTiles = new ArrayList<>();

        for (Tile[] column : stage.getTiles())
        {
            for (Tile tile : column)
            {
                if (tile.getType() == TileType.EMPTY)
                {
                    emptyTiles.add(tile);
                }
            }
        }

        if (emptyTiles.size() > 1)
        {
            return emptyTiles.get(getRandomNumberInRange(0, emptyTiles.size() - 1));
        }
        else
        {
            return emptyTiles.get(0);
        }
    }

    public Tile getRandomTileOfType(TileType searchedType)
    {
        Tile[] foundTiles = stage.getTilesOfType(searchedType);
        if (foundTiles.length > 0)
        {
            return foundTiles[getRandomNumberInRange(0, foundTiles.length - 1)];
        }
        else
        {
            return null;
        }
    }

    public Tile randomizeSeed(BuildDirection buildDir, int randomReach)
    {
        Tile outputSeed;
        outputSeed = getNextAvailableSeedTile(buildDir);
        int randomNumberVertical = getRandomNumberInRange(0, randomReach);
        int randomNumberHorizontal = getRandomNumberInRange(0, randomReach);

        if (randomNumberHorizontal == 0 && randomNumberVertical == 0)
        {
            return outputSeed;
        }


        switch (buildDir)
        {
            case RD:
                //outputSeed = stage.getTile()
                break;
        }

        return outputSeed;
    }

    public Tile getNextAvailableSeedTile(BuildDirection buildDir) //throws IOException
    {
        int checkedTilesCounter = 0;

        Tile outputSeed;
        outputSeed = getSeedByDirection(buildDir);

        //mapGenerator.Tile outputSeed = currentSeed;

        while (outputSeed.getType() != TileType.EMPTY && checkedTilesCounter <= stage.getHeight() * stage.getWidth())
        {
            switch (buildDir)
            {
                case RD:
                    outputSeed = tileNav.getNextTileLooped(outputSeed, BuildDirection.RD);
                    //outputSeed = getRandomEmptyTile();
                    setSeedByDirection(outputSeed, BuildDirection.RD);
                    break;

                case LD:
                    outputSeed = tileNav.getNextTileLooped(outputSeed, BuildDirection.LD);
                    //outputSeed = getRandomEmptyTile();
                    setSeedByDirection(outputSeed, BuildDirection.LD);
                    break;

                case RU:
                    outputSeed = tileNav.getNextTileLooped(outputSeed, BuildDirection.RU);//
                    //outputSeed = getRandomEmptyTile();
                    setSeedByDirection(outputSeed, BuildDirection.RU);
                    break;

                case LU:
                    outputSeed = tileNav.getNextTileLooped(outputSeed, BuildDirection.LU);//
                    //outputSeed = getRandomEmptyTile();
                    setSeedByDirection(outputSeed, BuildDirection.LU);
                    break;
            }
            checkedTilesCounter++;
        }

        //mapGenerator.Tile outputSeed = currentSeed;
        return outputSeed;
    }

    public boolean insertRandomRoom() //throws IOException
    {
        BuildDirection randomBuildDir = BuildDirection.getRandomBuildDirection();

        return insertRandomRoom(getNextAvailableSeedTile(randomBuildDir), randomBuildDir);
    }

    public void fillStageWithRooms() //throws IOException
    {
        while (stage.areEmptyTiles())
        {
            insertRandomRoom();
        }
    }

    private void squeezeDoubleCorridor(Wall wall)
    {
        Direction dir = wall.getSide();
        Tile firstNeighbor;
        Tile secondNeighbor;
        Tile thirdNeighbor;

        for (Tile wallTile : wall.getWallTiles())
        {
            firstNeighbor = stage.getNextTile(wallTile, dir);
            secondNeighbor = stage.getNextTile(firstNeighbor, dir);
            if (firstNeighbor.getType() == TileType.CORRIDOR && secondNeighbor.getType() == TileType.CORRIDOR) //podwójny korytarz
            {
                thirdNeighbor = stage.getNextTile(secondNeighbor, dir);
                if (thirdNeighbor.getType() != TileType.CORRIDOR) //sprawdza czy przypadkiem nie analizuje wzdłuż korytarza
                {
                    firstNeighbor.setType(TileType.WALL);
                }
            }
        }
    }

    private void squeezeSurroundingDoubleCorridors(Room room)
    {

        //TODO: nie zawsze poprawnie squeezuje -> sprawdzić dokładnie
        for (Wall wall : room.getWalls())
        {
            squeezeDoubleCorridor(wall);
        }
    }

    public void squeezeDoubleCorridors()
    {
        for (Room room : stage.getRooms())
        {
            squeezeSurroundingDoubleCorridors(room);
        }
    }

    private boolean allRoomsAreReachable()
    {

        //TODO: coś nie tak - zwłaszcza dla narożnych pól pokoju(???)
        Tile currentTile;
        Tile neighboringTile;
        boolean roomReachable;

        for (Room currentRoom : stage.getRooms())
        {
            roomReachable = false;
            for (Wall wall : currentRoom.getWalls())
            {
                for (int i = 1; i < wall.getWallTiles().length - 2; i++)    //przeszukaj pola ścian poza pierwszym i ostatnim (naroża pokoju)
                {
                    currentTile = wall.getWallTiles()[i];
                    neighboringTile = stage.getNextTile(currentTile, wall.getSide());
                    if (neighboringTile.getType() == TileType.CORRIDOR)
                    {
                        roomReachable = true;
                        break;
                    }
                }
                if (roomReachable)
                {

                    break;
                }
            }
            if (!roomReachable)
            {

                return false;
            }
        }

        return true;

    }

    public void obstructPercentageOfCorridors(int percentToObstruct) //throws IOException
    {
        if (percentToObstruct < 0)
        {
            return;
        }
        else if (percentToObstruct > 90)
        {
            percentToObstruct = 90;
        }

        int corridorTilesCount = stage.getTilesOfType(TileType.CORRIDOR).length;
        int obstructionsCount = corridorTilesCount * percentToObstruct / 100;

        createObstructionsInCorridors(obstructionsCount);
    }

    public void createObstructionsInCorridors(int targetObstructionsCount) //throws IOException
    {
        //int possibleTries = 20 * targetObstructionsCount;
        int possibleTries = 15 * targetObstructionsCount;
        createObstructionsInCorridors(targetObstructionsCount, possibleTries);
    }

    public void createObstructionsInCorridors(int targetObstructionsCount, int possibleTries) //throws IOException
    {
        int triesCounter = 0;
        int createdObstructionsNumber = 0;
        Tile randomCorridorTile;
        ArrayList<Tile> corridorTiles;
        corridorTiles = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CORRIDOR)));
        List<Tile> excludedTiles = new ArrayList<>();

        do
        {
            //randomCorridorTile = getRandomTileOfType(TileType.CORRIDOR);
            //TODO: usunąć ifa poniżej po wyeliminowaniu buga
            if (corridorTiles.size() == 0)
            {
                int i = 0;
                i++;
                MapGeneratorService.buildDebugSite(stage);
                MapGeneratorService.buildDebugSite(stage);
            }

            randomCorridorTile = tileNav.getRandomTileFromList(corridorTiles);
            randomCorridorTile.setType(TileType.DEBUG);
            MapGeneratorService.buildDebugSite(stage);
            MapGeneratorService.buildDebugSite(stage);

            Tile[] unreachableCorridorTiles = tileNav.getUnreachableCorridorTiles();
            randomCorridorTile.setType(TileType.UNREACHABLE);
            stage.setTileTypes(unreachableCorridorTiles, TileType.UNREACHABLE);
            MapGeneratorService.buildDebugSite(stage);
            MapGeneratorService.buildDebugSite(stage);

            if (unreachableCorridorTiles.length <= 0 && allRoomsAreReachable())
            {
                createdObstructionsNumber++;
                randomCorridorTile.setType(TileType.OBSTRUCTION);
                corridorTiles.remove(randomCorridorTile);
                MapGeneratorService.buildDebugSite(stage);
                MapGeneratorService.buildDebugSite(stage);
                System.out.println("Obstructions created: " + createdObstructionsNumber + "/" + targetObstructionsCount);
            }
            else
            {
                //TODO: jeśli powstał ślepy odcinek korytarza, to spróbować go wypełnić

                stage.setTileTypes(unreachableCorridorTiles, TileType.CUTOFF);
                MapGeneratorService.buildDebugSite(stage);
                MapGeneratorService.buildDebugSite(stage);

                if (allRoomsAreReachable())
                {
                    createdObstructionsNumber++;
                    corridorTiles.remove(randomCorridorTile);
                    for (Tile obstructedCorridorTile : unreachableCorridorTiles)
                    {
                        obstructedCorridorTile.setType(TileType.OBSTRUCTION);
                        corridorTiles.remove(obstructedCorridorTile);
                    }
                    randomCorridorTile.setType(TileType.OBSTRUCTION);
                    MapGeneratorService.buildDebugSite(stage);
                    MapGeneratorService.buildDebugSite(stage);
                    createdObstructionsNumber = createdObstructionsNumber + unreachableCorridorTiles.length;
                    System.out.println("Obstructions created: " + createdObstructionsNumber + "/" + targetObstructionsCount);
                }
                else
                {
                    stage.setTileTypes(unreachableCorridorTiles, TileType.CORRIDOR);
                    triesCounter++;
                    System.out.println("Liczba prób: " + triesCounter + "/" + possibleTries);
                    randomCorridorTile.setType(TileType.CORRIDOR);
                    MapGeneratorService.buildDebugSite(stage);
                    MapGeneratorService.buildDebugSite(stage);
                    corridorTiles.remove(randomCorridorTile);
                    excludedTiles.add(randomCorridorTile);


                    //TODO: można spróbować excludować nie tylko to 1 pole, ale też wszystkie sąsiadujące aż do najbliższych skrzyżowań,
                    // ale po obu stronach musi być skrzyżowanie (trzeba przeanalizować bardziej)
                    // lub jeśli korytarz jest prosty (nie ma rozgałęzień) to można zblokować cały odcinek między dwoma excludami

                    Tile[] intersections = tileNav.getCorridorIntersectionTiles();

                    for (Tile tile : excludedTiles)
                    {
                        tile.setType(TileType.EXCLUDED);
                    }
                    MapGeneratorService.buildDebugSite(stage);
                    MapGeneratorService.buildDebugSite(stage);

                    Set<Tile> neighboringAreaOfExcludedTile = tileNav.getTouchingTilesOfType(randomCorridorTile,TileType.CORRIDOR);
//                    for(Tile tile : intersections)
//                    {
//                        tile.setType(TileType.INTERSECTION);
//                    }

                    if (neighboringAreaOfExcludedTile.stream().noneMatch(this.tileNav::isIntersection))
                    {

                    }


                    for (Tile tile : excludedTiles)
                    {
                        tile.setType(TileType.CORRIDOR);
                    }
                }
            }
        } while (createdObstructionsNumber < targetObstructionsCount && triesCounter < possibleTries);

    }

    private void addDoorToRoom(Room room)
    {
        Tile randomWallTile;
        Tile neighboringTile;
        boolean doorCreated = false;

        forLoop:
        for (Wall wall : room.getWalls())
        {
            doorCreated = false;
            List<Tile> wallTiles = new ArrayList<>(Arrays.asList(wall.getWallTiles()));
            //System.out.println("corner tile removed: " + wallTiles.get(wallTiles.size() - 1).getX() + "-" + wallTiles.get(wallTiles.size() - 1).getY());
            wallTiles.remove(wallTiles.size() - 1); //usuwa narożniki z listy
            //System.out.println("corner tile removed: " + wallTiles.get(0).getX() + "-" + wallTiles.get(0).getY());
            wallTiles.remove(0); //usuwa narożniki z listy
            do
            {
                //System.out.print("wall tiles with removed corners: ");
                for (Tile tile : wallTiles)
                {
                    //System.out.print("(" + tile.getX() + "-" + tile.getY() + ") ");
                }
                //System.out.println();

                if (wallTiles.size() > 1)
                {
                    randomWallTile = wallTiles.get(getRandomNumberInRange(0, wallTiles.size() - 1));
                }
                else
                {
                    randomWallTile = wallTiles.get(0);
                }

                //System.out.println("checked wall tile: " + randomWallTile.getX() + "-" + randomWallTile.getY());
                neighboringTile = stage.getNextTile(randomWallTile, wall.getSide());
                //System.out.println("checked neighboring tile: " + neighboringTile.getX() + "-" + neighboringTile.getY());

                if (neighboringTile.getType() == TileType.CORRIDOR)
                {
                    randomWallTile.setType(TileType.DOOR);
                    //System.out.println("door created (" + randomWallTile.getX() + "-" + randomWallTile.getY() + ")");
                    doorCreated = true;
                    break forLoop;
                }
                else
                {
                    wallTiles.remove(randomWallTile);
                }
            } while (wallTiles.size() >= 1);
        }
        if (!doorCreated)
        {
            System.out.println("Door failed");
        }
    }

    public void addDoorToAllRooms()
    {
        for (Room room : stage.getRooms())
        {
//            System.out.println("mapGenerator.Room will try to add door: (" + room.getxPos() + "-" + room.getyPos() + ")");
            addDoorToRoom(room);
        }
    }

}
