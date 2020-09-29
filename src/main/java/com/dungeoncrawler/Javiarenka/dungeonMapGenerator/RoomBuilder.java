package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.io.IOException;
import java.util.*;

public class RoomBuilder
{
    private Stage stage;
    private Tile[] seeds = new Tile[BuildDirection.SIZE];

    public RoomBuilder(Stage stage)
    {
        this.stage = stage;

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
            System.out.println("Not enough space to fit any room. mapGenerator.Tile locked in wall");
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
            System.out.println("Next tile unavailable. mapGenerator.Stage Limit reached.");
            //e.printStackTrace();
        }
        return nextTile;
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
                    outputSeed = getNextTileLooped(outputSeed, BuildDirection.RD);
                    //outputSeed = getRandomEmptyTile();
                    setSeedByDirection(outputSeed, BuildDirection.RD);
                    break;

                case LD:
                    outputSeed = getNextTileLooped(outputSeed, BuildDirection.LD);
                    //outputSeed = getRandomEmptyTile();
                    setSeedByDirection(outputSeed, BuildDirection.LD);
                    break;

                case RU:
                    outputSeed = getNextTileLooped(outputSeed, BuildDirection.RU);//
                    //outputSeed = getRandomEmptyTile();
                    setSeedByDirection(outputSeed, BuildDirection.RU);
                    break;

                case LU:
                    outputSeed = getNextTileLooped(outputSeed, BuildDirection.LU);//
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

    private Map<Direction, Tile> getNeighboringTilesWithDirections(Tile tile)
    {
        Map<Direction, Tile> outputMap = new HashMap<>();

        for (Direction dir : Direction.values())
        {
            outputMap.put(dir, stage.getNextTile(tile, dir));
        }
        return outputMap;
    }

    public boolean allCorridorsAreReachable() //throws IOException
    {
        Tile currentTile = stage.getFirstTileOfType(TileType.CORRIDOR);
        Tile scannedTile;
        List<Tile> walkedTiles = new ArrayList<>();
        List<Tile> possiblePathTiles = new ArrayList<>();

        //TODO: jest bug - istnieje mała szansa, że pierwsze wylosowane pole będzie jednopolowym ślepym zaułkiem, wtedy possible path tiles = 0

        do
        {
            walkedTiles.add(currentTile);
            currentTile.setType(TileType.WALKED);
            Map<Direction, Tile> neighboringTiles = getNeighboringTilesWithDirections(currentTile);
            for (Direction dir : neighboringTiles.keySet())
            {
                scannedTile = neighboringTiles.get(dir);
                if (scannedTile.getType() == TileType.CORRIDOR)
                {
                    possiblePathTiles.add(scannedTile);
                    scannedTile.setType(TileType.BREADCRUMB);
                }
                else if (scannedTile.getType() == TileType.BREADCRUMB)
                {
                    possiblePathTiles.remove(scannedTile);
                    possiblePathTiles.add(scannedTile);
                }
            }

            if (possiblePathTiles.size() > 0)
            {
                currentTile = possiblePathTiles.get(possiblePathTiles.size() - 1);
                possiblePathTiles.remove(possiblePathTiles.size() - 1);
            }
            //stage.saveToTxt();
        } while (possiblePathTiles.size() > 0);

        walkedTiles.add(currentTile);
        currentTile.setType(TileType.WALKED);
        //stage.saveToTxt();

        Tile[] leftoverCorridorTiles = stage.getTilesOfType(TileType.CORRIDOR);

        for (Tile walkedTile : walkedTiles)
        {
            walkedTile.setType(TileType.CORRIDOR);
        }

        //System.out.println("Nie każde pole korytarza jest dostępne dla zwiedzających xD");
        return leftoverCorridorTiles.length <= 0;

    }

    private boolean allRoomsAreReachable()
    {
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
                if (roomReachable) break;
            }
            if (!roomReachable) return false;
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
        int possibleTries = 20 * targetObstructionsCount;
        createObstructionsInCorridors(targetObstructionsCount, possibleTries);
    }

    public void createObstructionsInCorridors(int targetObstructionsCount, int possibleTries) //throws IOException
    {
        int triesCounter = 0;
        int createdObstructionsNumber = 0;
        Tile randomCorridorTile;

        do
        {

            randomCorridorTile = getRandomTileOfType(TileType.CORRIDOR);
            randomCorridorTile.setType(TileType.OBSTRUCTION);
            if (allCorridorsAreReachable() && allRoomsAreReachable())
            {
                createdObstructionsNumber++;
            }
            else
            {
                triesCounter++;
                //System.out.println("Liczba prób: " + triesCounter + "/" + possibleTries);
                randomCorridorTile.setType(TileType.CORRIDOR);
            }
        } while (createdObstructionsNumber < targetObstructionsCount && triesCounter < possibleTries);

        System.out.println("Obstructions created: " + createdObstructionsNumber + "/" + targetObstructionsCount);

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
