package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Stage
{
    private int width;
    private int height;
    private Tile[][] tiles;
    private List<Room> rooms = new ArrayList<>();

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

    public void printRow(int rowNumber)
    {
        System.out.println(getRowString(rowNumber));
    }

    public void printCol(int colNumber)
    {
        System.out.println(getColumnString(colNumber));
    }

    @Override
    public String toString()
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

        writer.append(this.toString());
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

    public Tile getNextTile(Tile tile, Direction dir)
    {
        Tile nextTile = new Tile();
        try
        {
            switch (dir)
            {
                case DOWN:
                    nextTile = getTile(tile.getX(), tile.getY() + 1);
                    break;

                case RIGHT:
                    nextTile = getTile(tile.getX() + 1, tile.getY());
                    break;

                case UP:
                    nextTile = getTile(tile.getX(), tile.getY() - 1);
                    break;

                case LEFT:
                    nextTile = getTile(tile.getX() - 1, tile.getY());
                    break;
            }
        } catch (Exception e)
        {
            System.out.println("Next tile unavailable. mapGenerator.Stage Limit reached.");
            //e.printStackTrace();
        }
        return nextTile;
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

//    void surroundWithCorridor(Room room) //throws IOException
//    {
//        Tile[][] surroundingTiles = getSurroundingTiles(room.getTiles());
//        for (int i = 0; i < surroundingTiles.length; i++)
//        {
//            setTileTypes(surroundingTiles[i], TileType.CORRIDOR);
//        }
//    }

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
