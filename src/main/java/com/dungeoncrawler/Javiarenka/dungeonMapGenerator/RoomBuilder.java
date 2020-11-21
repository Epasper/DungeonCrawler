package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

//        seeds[0] = randomizeSeed(seeds[0], BuildDirection.RD);
//        seeds[1] = randomizeSeed(seeds[1], BuildDirection.LD);
//        seeds[2] = randomizeSeed(seeds[2], BuildDirection.RU);
//        seeds[3] = randomizeSeed(seeds[3], BuildDirection.LU);
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
        int maxRoomWidth = (StageSettings.ROOM_WIDTH_PERCENTAGE * stage.getWidth()) / 100;
        if (maxRoomWidth < StageSettings.MIN_ROOM_WIDTH)
        {
            maxRoomWidth = StageSettings.MIN_ROOM_WIDTH;
        }
        return maxRoomWidth;
    }

    public int getMaxRoomHeight()
    {
        int maxRoomHeight = (StageSettings.ROOM_HEIGHT_PERCENTAGE * stage.getHeight()) / 100;
        if (maxRoomHeight < StageSettings.MIN_ROOM_HEIGHT)
        {
            maxRoomHeight = StageSettings.MIN_ROOM_HEIGHT;
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
                        nextTile = tileNav.getNextTile(tile, Direction.DOWN);
                        break;

                    case RIGHT:
                        nextTile = tileNav.getNextTile(tile, Direction.RIGHT);
                        break;

                    case UP:
                        nextTile = tileNav.getNextTile(tile, Direction.UP);
                        break;

                    case LEFT:
                        nextTile = tileNav.getNextTile(tile, Direction.LEFT);
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

    private double getRatio(int width, int height)
    {
        return (width >= height) ? (double) width / (double) height : (double) height / (double) width;
    }

    private void reduceTheRatio(AtomicInteger width, AtomicInteger height)
    {
        AtomicInteger biggerSize = (height.intValue() > width.intValue()) ? height : width;
        AtomicInteger smallerSize = (height.intValue() > width.intValue()) ? width : height;

        biggerSize.set((int) (smallerSize.intValue() * StageSettings.MAX_ROOM_RATIO));
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

        //boolean roomPlaceable = false;

        if (!((availableWidth >= StageSettings.MIN_ROOM_WIDTH) && (availableHeight >= StageSettings.MIN_ROOM_HEIGHT)))
        {
            //System.out.println("Not enough space to fit any room. Tile locked in wall");
            seedTile.setType(TileType.WALL);
            return false;
        }

        int maxRoomWidth = getMaxRoomWidth();
        int maxRoomHeight = getMaxRoomHeight();

        maxRoomWidth = Math.min(maxRoomWidth, availableWidth);
        maxRoomHeight = Math.min(maxRoomHeight, availableHeight);

        maxRoomWidth = Math.min(maxRoomWidth, roomWidth);
        maxRoomHeight = Math.min(maxRoomHeight, roomHeight);

        AtomicInteger setWidth = new AtomicInteger(maxRoomWidth);
        AtomicInteger setHeight = new AtomicInteger(maxRoomHeight);

        double ratio = getRatio(maxRoomWidth, maxRoomHeight);
        if (ratio > StageSettings.MAX_ROOM_RATIO)
        {
            reduceTheRatio(setWidth, setHeight);
        }

        System.out.println("Room size: " + setWidth.intValue() + "/" + setHeight.intValue());

        Room room = new Room(getTilesRectangle(seedTile, setWidth.intValue(), setHeight.intValue(), buildDirection), TileType.ROOM);
        System.out.println(room.asString());

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
        List<Tile> roomTilesList = new ArrayList<>(Arrays.asList(stage.getTilesOfType(new TileType[]{TileType.ROOM, TileType.ROOM_LOCKED})));
        Map<Tile, TileType> tilesWithTypes = new HashMap<>();

        do
        {
            Tile currentTile = stage.getFirstTileOfType(new TileType[]{TileType.ROOM, TileType.ROOM_LOCKED});
            TileType currentType = currentTile.getType();
            seedTile = stage.getTile(currentTile.getX() - 1, currentTile.getY() - 1);

            Set<Tile> innerRoomTilesSet = tileNav.getTouchingTilesOfSameType(currentTile);

            Tile[][] innerRoomArray = getRangeFromSet(innerRoomTilesSet);
            Tile[][] outerRoomArray = stage.getSurroundingTiles(innerRoomArray);

            Room room = new Room(getTilesRectangle(seedTile, outerRoomArray.length, outerRoomArray[0].length, BuildDirection.RD), currentType);
            stage.updateRoomsSet(room);

            for (Tile tile : innerRoomTilesSet)
            {
                tilesWithTypes.put(tile, tile.getType());
                tile.setType(TileType.ROOM_SCANNED);
                roomTilesList.remove(tile);
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
            tile.setType(tilesWithTypes.get(tile));
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

        int randRoomWidth = getRandomNumberInRange(StageSettings.MIN_ROOM_WIDTH, maxRoomWidth);
        int randRoomHeight = getRandomNumberInRange(StageSettings.MIN_ROOM_HEIGHT, maxRoomHeight);

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

    public Tile getNextAvailableSeedTile(BuildDirection buildDir) //throws IOException
    {
        Tile outputSeed = getSeedByDirection(buildDir);
        boolean areEmptyTilesLeft = stage.getTilesOfType(TileType.EMPTY).length > 0;

        while (outputSeed.getType() != TileType.EMPTY && areEmptyTilesLeft)
        {
            outputSeed = tileNav.getNextTileLooped(outputSeed, buildDir);
            //TODO: poniżej bug - można usunąć/wykomentować całego ifa, to bug zniknie. Bug jest powodowany faktem, że randomizer seedów może wybrać takiego seeda
            // że zostanie stwożony pokój niepołączony z głównym korytarzem - taki "zawieszony w przestrzeni"
//            if (outputSeed.getType() == TileType.EMPTY)
//            {
//                Tile randomizeSeed = randomizeSeed(outputSeed, buildDir);
//                if (randomizeSeed.getType() == TileType.EMPTY) outputSeed = randomizeSeed;
//            }
//            setSeedByDirection(outputSeed, buildDir);
        }
        //System.out.println("Seed tile: " + outputSeed.getX() + "/" + outputSeed.getY() + ", for directions: " + buildDir.name());
        //outputSeed.setType(TileType.DEBUG);
        MapGeneratorService.buildDebugSite(stage);
        return outputSeed;
    }

    private Tile randomizeSeed(Tile seedTile, BuildDirection buildDir)
    {
        int xRandomisation = getRandomNumberInRange(0, StageSettings.MIN_ROOM_WIDTH - 2);
        int yRandomisation = getRandomNumberInRange(0, StageSettings.MIN_ROOM_HEIGHT - 2);

        Tile outputTile = seedTile;
        Direction firstDir = Direction.RIGHT;
        Direction secondDir = Direction.DOWN;

        switch (buildDir)
        {
            case RD:
                firstDir = Direction.RIGHT;
                secondDir = Direction.DOWN;
                break;
            case LD:
                firstDir = Direction.LEFT;
                secondDir = Direction.DOWN;
                break;
            case RU:
                firstDir = Direction.RIGHT;
                secondDir = Direction.UP;
                break;
            case LU:
                firstDir = Direction.LEFT;
                secondDir = Direction.UP;
                break;
        }

        outputTile = tileNav.getNextTile(seedTile, new Direction[]{firstDir, secondDir}, new int[]{xRandomisation, yRandomisation});
        return outputTile;
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
            firstNeighbor = tileNav.getNextTile(wallTile, dir);
            secondNeighbor = tileNav.getNextTile(firstNeighbor, dir);
            if (firstNeighbor.getType() == TileType.CORRIDOR && secondNeighbor.getType() == TileType.CORRIDOR) //podwójny korytarz
            {
                thirdNeighbor = tileNav.getNextTile(secondNeighbor, dir);
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
                for (int i = 1; i < wall.getWallTiles().length - 1; i++)    //przeszukaj pola ścian poza pierwszym i ostatnim (naroża pokoju)
                {
                    currentTile = wall.getWallTiles()[i];
                    neighboringTile = tileNav.getNextTile(currentTile, wall.getSide());
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
        int possibleTries = 10 * targetObstructionsCount;
        createObstructionsInCorridors(targetObstructionsCount, possibleTries);
    }

    public void createObstructionsInCorridors(int targetObstructionsCount, int possibleTries) //throws IOException
    {
        int triesCounter = 0;
        int createdObstructionsNumber = 0;
        Tile randomCorridorTile;
        ArrayList<Tile> corridorTilesToObstruct;
        corridorTilesToObstruct = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CORRIDOR)));
        List<Tile> excludedTiles = new ArrayList<>();

        do
        {
            randomCorridorTile = tileNav.getRandomTileFromList(corridorTilesToObstruct);
            randomCorridorTile.setType(TileType.DEBUG);

//            for (Tile tile : excludedTiles)
//            {
//                tile.setType(TileType.EXCLUDED);
//            }
//            MapGeneratorService.buildDebugSite(stage);
//            MapGeneratorService.buildDebugSite(stage);
//            for (Tile tile : excludedTiles)
//            {
//                tile.setType(TileType.CORRIDOR);
//            }

            Tile[] unreachableCorridorTiles = tileNav.getUnreachableCorridorTiles();
            randomCorridorTile.setType(TileType.UNREACHABLE);
            stage.setTileTypes(unreachableCorridorTiles, TileType.UNREACHABLE);
//            MapGeneratorService.buildDebugSite(stage);
//            MapGeneratorService.buildDebugSite(stage);

            if (unreachableCorridorTiles.length <= 0 && allRoomsAreReachable())
            {
                createdObstructionsNumber++;
                randomCorridorTile.setType(TileType.OBSTRUCTION);
                corridorTilesToObstruct.remove(randomCorridorTile);
//                MapGeneratorService.buildDebugSite(stage);
//                MapGeneratorService.buildDebugSite(stage);
                //System.out.println("Obstructions created: " + createdObstructionsNumber + "/" + targetObstructionsCount);
            }
            else
            {
                stage.setTileTypes(unreachableCorridorTiles, TileType.CUTOFF);
//                MapGeneratorService.buildDebugSite(stage);
//                MapGeneratorService.buildDebugSite(stage);

                if (allRoomsAreReachable())
                {
                    createdObstructionsNumber++;
                    corridorTilesToObstruct.remove(randomCorridorTile);
                    for (Tile obstructedCorridorTile : unreachableCorridorTiles)
                    {
                        obstructedCorridorTile.setType(TileType.OBSTRUCTION);
                        corridorTilesToObstruct.remove(obstructedCorridorTile);
                    }
                    randomCorridorTile.setType(TileType.OBSTRUCTION);
//                    MapGeneratorService.buildDebugSite(stage);
//                    MapGeneratorService.buildDebugSite(stage);
                    createdObstructionsNumber = createdObstructionsNumber + unreachableCorridorTiles.length;
                    //System.out.println("Obstructions created: " + createdObstructionsNumber + "/" + targetObstructionsCount);
                }
                else
                {
                    stage.setTileTypes(unreachableCorridorTiles, TileType.CORRIDOR);
                    triesCounter++;
                    //System.out.println("Liczba prób: " + triesCounter + "/" + possibleTries);
                    randomCorridorTile.setType(TileType.CORRIDOR);

                    Tile[] intersections = tileNav.getCorridorIntersectionTiles();

                    for (Tile tile : excludedTiles)
                    {
                        tile.setType(TileType.EXCLUDED);
                    }
//                    MapGeneratorService.buildDebugSite(stage);
//                    MapGeneratorService.buildDebugSite(stage);

                    corridorTilesToObstruct.remove(randomCorridorTile);
                    excludedTiles.add(randomCorridorTile);

                    Set<Tile> neighboringAreaOfExcludedTile = tileNav.getTouchingTilesOfType(randomCorridorTile, TileType.CORRIDOR);
                    if (neighboringAreaOfExcludedTile.stream().noneMatch(this.tileNav::isIntersection) && neighboringAreaOfExcludedTile.size() > 1)
                    {
                        CorridorSection section = new CorridorSection(neighboringAreaOfExcludedTile, stage);
                        Tile first = section.getSection().getFirst();
                        Tile last = section.getSection().getLast();

                        if (tileNav.numberOfNeighborsOfType(TileType.EXCLUDED, first) == 1 && tileNav.numberOfNeighborsOfType(TileType.EXCLUDED, last) == 1)
                        {
                            section.getSection()
                                    .stream()
                                    .forEach(tile ->
                                    {
                                        tile.setType(TileType.EXCLUDED);
                                        corridorTilesToObstruct.remove(tile);
                                        excludedTiles.add(tile);
                                    });
//                            MapGeneratorService.buildDebugSite(stage);
//                            MapGeneratorService.buildDebugSite(stage);
                        }
                    }

                    for (Tile tile : excludedTiles)
                    {
                        tile.setType(TileType.CORRIDOR);
                    }
                }
            }
            //System.out.println("Corridors potentially obstructable: " + corridorTilesToObstruct.size());
            //System.out.println("createObstructionsInCorridors");
        } while ((createdObstructionsNumber < targetObstructionsCount && triesCounter < possibleTries)
                && (corridorTilesToObstruct.size() > 0));

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
                neighboringTile = tileNav.getNextTile(randomWallTile, wall.getSide());
                //System.out.println("checked neighboring tile: " + neighboringTile.getX() + "-" + neighboringTile.getY());

                if (neighboringTile.getType() == TileType.CORRIDOR)
                {
                    randomWallTile.setType(TileType.DOOR_CLOSED);
                    room.setDoor(randomWallTile);
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

    public void removeShortBranches(int branchLength)
    {
        List<Tile> intersections = new ArrayList<>(Arrays.asList(tileNav.getCorridorIntersectionTiles()));
        List<CorridorSection> sections = new ArrayList<>();
        List<CorridorSection> shortSections;
        intersections.forEach(tile -> tile.setType(TileType.INTERSECTION));
        CorridorSection currentSection;

        for (Tile intersectionTile : intersections)
        {
            List<Tile> branchesSeeds = tileNav.getNeighboringTiles(intersectionTile, TileType.CORRIDOR);
            for (Tile branchStart : branchesSeeds)
            {
                currentSection = new CorridorSection(branchStart, stage);

                if (currentSection.getLength() <= branchLength)
                {
                    sections.add(new CorridorSection(branchStart, stage));
                }
            }
        }   //dostaniemy wszystkie branche do zadanej długości

        shortSections = sections.stream()
                .filter(corridorSection -> corridorSection.getSection()
                        .stream().allMatch(tile -> tileNav.numberOfNeighborsOfType(TileType.DOOR_CLOSED, tile) == 0))
                .collect(Collectors.toList());

        for (CorridorSection section : shortSections)
        {
            Tile[] sectionTiles = new Tile[section.getSection().size()];
            section.getSection().toArray(sectionTiles);
            int numberOfNeighboringIntersections = tileNav.numberOfNeighborsOfType(TileType.INTERSECTION, sectionTiles);

            if (numberOfNeighboringIntersections <= 1)
            {
                section.getSection()
                        .stream().forEach(tile -> tile.setType(TileType.OBSTRUCTION));
            }
        }
        intersections.forEach(tile -> tile.setType(TileType.CORRIDOR));
    }

    private void removeBranchingTilesFromClusters(List<Tile> potentiallyClusteredCorridors, TileType targetType)
    {
        List<Tile> tilesToRemove = new ArrayList<>();
        potentiallyClusteredCorridors.forEach(tile -> tile.setType(TileType.INTERSECTION));

        do
        {
            tilesToRemove.clear();
            tilesToRemove = potentiallyClusteredCorridors.stream()
                    .filter(tile -> tileNav.getNeighboringTiles(tile, TileType.INTERSECTION).size() <= 1)
                    .collect(Collectors.toList());

            tilesToRemove.forEach(tile -> tile.setType(TileType.CORRIDOR));
            potentiallyClusteredCorridors.removeAll(tilesToRemove);

        } while (tilesToRemove.size() > 0);

        potentiallyClusteredCorridors.forEach(tile -> tile.setType(targetType));
    }

    public void removeCorridorClusters()
    {
        List<Tile> tempIntersect = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.INTERSECTION)));
        tempIntersect.forEach(tile -> tile.setType(TileType.CORRIDOR));

        List<Tile> allCorridors = new ArrayList<>(Arrays.asList(stage.getTilesOfType(TileType.CORRIDOR)));
        List<Tile> potentialClusteredCorridors = allCorridors.stream()
                .filter(tile -> tileNav.numberOfSurroundingOfType(TileType.CORRIDOR, tile) >= 4)
                .collect(Collectors.toList());

        potentialClusteredCorridors.forEach(tile -> tile.setType(TileType.INTERSECTION));
        removeBranchingTilesFromClusters(potentialClusteredCorridors, TileType.INTERSECTION);
        //powyższy kod sprawi, że w liście 'potentialClusteredCorridors' zostaną same Tile należące do prostokątnych clusterów

        //poniżej - usuwanie z clusterów pól, które są narożnikiem otoczonym przez mur
        List<Tile> cornerTiles;
        do
        {
            cornerTiles = potentialClusteredCorridors.stream()
                    .filter(tile -> tileNav.getNeighboringTiles(tile, new TileType[]{TileType.WALL, TileType.OBSTRUCTION}).size() == 2)
                    .collect(Collectors.toList());
            if (cornerTiles.size() == 0) break;
            Tile currentTile = cornerTiles.get(0);
            currentTile.setType(TileType.OBSTRUCTION);
            potentialClusteredCorridors.remove(currentTile);
            cornerTiles.remove(currentTile);
            removeBranchingTilesFromClusters(potentialClusteredCorridors, TileType.INTERSECTION);
        } while (cornerTiles.size() > 0);

        //poniżej wyszukanie clusterów większych niż kwadrat 2x2, następnie zidentyfikowanie ich pól wewnętrznych:
        List<Tile> bigClusters;
        List<Tile> insideBigClusters;
        do
        {
            bigClusters = potentialClusteredCorridors.stream()
                    .filter(tile -> tileNav.getTouchingTilesOfType(tile, TileType.INTERSECTION).size() > 4)
                    .collect(Collectors.toList());
            insideBigClusters = bigClusters.stream()
                    .filter(tile -> tileNav.getNeighboringTiles(tile, TileType.INTERSECTION).size() >= 3)
                    .collect(Collectors.toList());
            if (insideBigClusters.size() == 0) break;
            Tile currentTile = insideBigClusters.get(0);
            currentTile.setType(TileType.OBSTRUCTION);
            potentialClusteredCorridors.remove(currentTile);
            removeBranchingTilesFromClusters(potentialClusteredCorridors, TileType.INTERSECTION);
        } while (insideBigClusters.size() > 0);

        //Do tego miejsca powinny już zostać tylko sclusterowane czwórki 2x2, gdzie każde pole stanowi odnogę odrębnego korytarza
        potentialClusteredCorridors.forEach(tile -> tile.setType(TileType.CORRIDOR));
        do
        {
            if (potentialClusteredCorridors.size() == 0) break;
            Tile currentTile = potentialClusteredCorridors.get(0);
            currentTile.setType(TileType.OBSTRUCTION);
            if (tileNav.getUnreachableCorridorTiles().length > 0 || !allRoomsAreReachable())
            {
                currentTile.setType(TileType.CORRIDOR);
            }
            potentialClusteredCorridors.remove(currentTile);
            removeBranchingTilesFromClusters(potentialClusteredCorridors, TileType.CORRIDOR);
        } while (potentialClusteredCorridors.size() > 0);
    }

    private <E> List toList(E[] inputArray)
    {
        return new ArrayList(Arrays.asList(inputArray));
    }

    public void lockSomeDoor(int targetPercentage)
    {
        List<Tile> doorList = toList(stage.getTilesOfType(TileType.DOOR_CLOSED));
        int doorToLock = doorList.size() * targetPercentage / 100;

        while (doorToLock > 0)
        {
            Tile randomDoorTile = doorList.get(getRandomNumberInRange(0, doorList.size() - 1));
            randomDoorTile.setType(TileType.DOOR_LOCKED);
            Room room = stage.getRoomByDoor(randomDoorTile);
            room.lockRoomTiles();
            //room.setTileTypes(room.getTilesOfType(TileType.ROOM), TileType.ROOM_LOCKED);
            doorToLock--;
            doorList.remove(randomDoorTile);
        }
    }

    public void lockSomeDoor()
    {
        lockSomeDoor(StageSettings.PERCENT_OF_DOOR_LOCKED);
    }

    public void closeRooms()
    {
        stage.getRooms().forEach(Room::lockRoomTiles);
    }

    public void hideRooms()
    {
        stage.getRooms().forEach(Room::hideRoom);
    }
}
