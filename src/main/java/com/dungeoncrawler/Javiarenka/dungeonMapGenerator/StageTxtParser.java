package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.io.*;

public class StageTxtParser
{
    private String filePath;

    public StageTxtParser(String filePath)
    {
        this.filePath = filePath;
    }

    public int getWidth() throws IOException
    {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String firstRow = reader.readLine();
        String[] columnsInfo = firstRow.split("\\s+");

        int colNo = 0;

        for (int i = 1; i < columnsInfo.length; i++)
        {
            colNo = Math.max(colNo, Integer.parseInt(columnsInfo[i]));
        }

        reader.close();
        return colNo + 1;
    }

    public int getHeight() throws IOException
    {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        Object[] lines = reader.lines().toArray();

        reader.close();
        return lines.length - 1;
    }

    private String getTileTypeStringValue(int colNo, int rowNo) throws IOException
    {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String[] fullLines = reader.lines().toArray(size -> new String[size]);
        String[] lines = new String[fullLines.length - 1];

        for (int i = 1; i < fullLines.length; i++)
        {
            lines[i - 1] = fullLines[i].split("\t")[1];
        }

        String theLine = lines[rowNo];

        reader.close();
        return theLine.substring(colNo * TileType.strLen, colNo * TileType.strLen + TileType.strLen);
    }

    public TileType getTileTypeValue(int colNo, int rowNo) throws IOException
    {
        String tileTypeString = getTileTypeStringValue(colNo, rowNo);
        return TileType.fromStrVal(tileTypeString);
    }
}
