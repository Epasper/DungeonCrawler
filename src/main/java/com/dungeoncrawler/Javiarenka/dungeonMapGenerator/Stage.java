package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.fogOfWar.FogManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Scope("singleton")
@Component("stage")
public class Stage
{
    private int width;
    private int height;
    private Tile[][] tiles;
    private List<Room> rooms = new ArrayList<>();
    private PartyManager partyManager = new PartyManager(this);
    private FogManager fogManager = new FogManager(this);

    Stage(){}

    Stage(int stageWidth, int stageHeight)
    {
        width = stageWidth;
        height = stageHeight;

        tiles = new Tile[width][height];

        for (int coordY = 0; coordY < height; coordY++)
        {
            for (int coordX = 0; coordX < width; coordX++)
            {
                tiles[coordX][coordY] = new Tile(coordX, coordY);
            }
        }
    }

    Stage(Tile[][] stageTiles)
    {
        width = stageTiles.length;
        height = stageTiles[0].length;

        tiles = new Tile[width][height];

        for (int coordY = 0; coordY < height; coordY++)
        {
            for (int coordX = 0; coordX < width; coordX++)
            {
                tiles[coordX][coordY] = stageTiles[coordX][coordY];
            }
        }
    }

    Stage(String filePath) throws IOException
    {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StageTxtParser stp = new StageTxtParser(filePath);


        width = stp.getWidth();
        height = stp.getHeight();
        tiles = new Tile[width][height];

        for (int coordY = 0; coordY < height; coordY++)
        {
            for (int coordX = 0; coordX < width; coordX++)
            {
                tiles[coordX][coordY] = new Tile(coordX, coordY);
                tiles[coordX][coordY].setType(stp.getTileTypeValue(coordX, coordY));
            }
        }
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Tile[][] getTiles()
    {
        return tiles;
    }

    public PartyManager getPartyManager()
    {
        return partyManager;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setTiles(Tile[][] tiles)
    {
        this.tiles = tiles;
    }

    public FogManager getFogManager()
    {
        return fogManager;
    }



    //======================================================================================================
    //======================================================================================================
    //======================================================================================================

    public Tile[] getTilesAsOneDimensionalArray()
    {
        List<Tile> tilesList = new ArrayList<>();
        for (int rowNo = 0; rowNo < height; rowNo++)
        {
            Tile[] row = getRow(rowNo);
            tilesList.addAll(Arrays.asList(row));
        }
        Tile[] outputArray = new Tile[tilesList.size()];
        tilesList.toArray(outputArray);
        return outputArray;
    }

    public Tile getTile(int coordX, int coordY)
    {
        return tiles[coordX][coordY];
    }

    public Tile[] getTilesOfType(TileType searchedType)
    {
        List<Tile> outputTiles = new ArrayList<>();
        for (Tile[] column : tiles)
        {
            for (Tile tile : column)
            {
                if (tile.getType() == searchedType)
                {
                    outputTiles.add(tile);
                }
            }
        }
        return outputTiles.toArray(new Tile[outputTiles.size()]);
    }

    public Tile[] getColumn(int colNumber)
    {
        return tiles[colNumber];
    }

    public Tile[] getColumn(int colNumber, int trimLength)
    {
        int trimmedColLength = height - 2 * trimLength;

        Tile[] outputCol = new Tile[trimmedColLength];
        Tile[] fullCol = getColumn(colNumber);

        for (int i = 0; i < trimmedColLength; i++)
        {
            outputCol[i] = fullCol[i + trimLength];
        }

        return outputCol;
    }

    public Tile[] getRow(int rowNumber)
    {
        Tile[] outputRow = new Tile[width];
        for (int col = 0; col < width; col++)
        {
            outputRow[col] = getTile(col, rowNumber);
        }
        return outputRow;
    }

    public Tile[] getRow(int rowNumber, int trimLength)
    {
        int trimmedRowLength = width - 2 * trimLength;

        Tile[] outputRow = new Tile[trimmedRowLength];
        Tile[] fullRow = getRow(rowNumber);

        for (int i = 0; i < trimmedRowLength; i++)
        {
            outputRow[i] = fullRow[i + trimLength];
        }

        return outputRow;
    }

    public String getRowString(int rowNumber)
    {
        StringBuilder sb = new StringBuilder();
        Tile[] row = getRow(rowNumber);

        for (int i = 0; i < width; i++)
        {
            sb.append(row[i].toString());
        }
        return sb.toString();
    }

    public String getColumnString(int colNumber)
    {
        StringBuilder sb = new StringBuilder();
        Tile[] column = getColumn(colNumber);

        for (int i = 0; i < height; i++)
        {
            sb.append(column[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public List<Room> getRooms()
    {
        return rooms;
    }

    public Room getRoomByDoor(Tile givenDoorTile)
    {
        return rooms.stream()
                .filter(room -> room.getDoor().equals(givenDoorTile))
                .collect(Collectors.toList()).get(0);
    }

    public void printRow(int rowNumber)
    {
        System.out.println(getRowString(rowNumber));
    }

    public void printCol(int colNumber)
    {
        System.out.println(getColumnString(colNumber));
    }

    public String asString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("\t");
        for (int i = 0; i < width; i++)
        {
            sb.append(" ").append(i);
            if (i < 10)
            {
                sb.append(" ");
            }

        }
        sb.append("\n");

        for (int row = 0; row < height; row++)
        {
            sb.append(row).append("\t").append(getRowString(row)).append("\n");
        }
        return sb.toString();
    }

    public void saveToTxt(String path) throws IOException
    {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.append(this.asString());
        writer.close();
    }

    public void saveToTxt() throws IOException
    {
        saveToTxt("./src/main/java/com/dungeoncrawler/Javiarenka/dungeonMapGenerator/txt/stage.txt");
    }

    public void setTileTypes(Tile[] tiles, TileType targetType)
    {
        for (Tile tile : tiles)
        {
            tile.setType(targetType);
        }
    }

    public void setTileTypes(TileType targetType)
    {
        Arrays.stream(getTilesAsOneDimensionalArray())
                .forEach(tile -> tile.setType(targetType));
    }

    public void createPeripheralWall()
    {
        setTileTypes(getRow(0), TileType.WALL);
        setTileTypes(getRow(height - 1), TileType.WALL);
        setTileTypes(getColumn(0), TileType.WALL);
        setTileTypes(getColumn(width - 1), TileType.WALL);
    }

    public void createPeripheralCorridor()
    {
        setTileTypes(getRow(1, 1), TileType.CORRIDOR);
        setTileTypes(getRow(height - 2, 1), TileType.CORRIDOR);
        setTileTypes(getColumn(1, 1), TileType.CORRIDOR);
        setTileTypes(getColumn(width - 2, 1), TileType.CORRIDOR);
    }

    public Tile[][] getSurroundingTiles(Tile[][] range)
    {
        int newRangeWidth = range.length + 2;
        int newRangeHeight = range[0].length + 2;

        Tile[][] outputRange = new Tile[newRangeWidth][newRangeHeight];

        for (int row = 0; row < newRangeHeight; row++)
        {
            for (int col = 0; col < newRangeWidth; col++)
            {
                if ((col == 0) || (col == newRangeWidth - 1) || (row == 0) || (row == newRangeHeight - 1))
                {
                    outputRange[col][row] = getTile(range[0][0].getX() - 1 + col, range[0][0].getY() - 1 + row);
                }
                else
                {
                    outputRange[col][row] = new Tile(col, row);
                }
            }
        }
        return outputRange;
    }

    boolean areEmptyTiles()
    {
        for (Tile[] row : tiles)
        {
            for (Tile tile : row)
            {
                if (tile.getType() == TileType.EMPTY)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateRoomsSet(Room room)
    {
        rooms.add(room);
    }

    public Tile getFirstTileOfType(TileType type)
    {
        for (Tile[] column : tiles)
        {
            for (Tile tile : column)
            {
                if (tile.getType() == type)
                {
                    return tile;
                }
            }
        }
        return null;
    }

    public Tile getRandomTileOfType(TileType type)
    {
        Tile[] tiles = getTilesOfType(type);
        Random rand = new Random();

        if (tiles.length > 1)
        {
            return tiles[rand.nextInt(tiles.length - 1)];
        }
        else
        {
            return tiles[0];
        }
    }
}
