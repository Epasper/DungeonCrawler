package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import com.dungeoncrawler.Javiarenka.dungeonMapGenerator.fogOfWar.FogManager;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class MapGeneratorService
{

    @Autowired
    Stage stage;
    PartyManager partyManager;
    FogManager fogManager;

    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public PartyManager getPartyManager()
    {
        return partyManager;
    }

    public void setPartyManager(PartyManager partyManager)
    {
        this.partyManager = partyManager;
    }

    public FogManager getFogManager()
    {
        return fogManager;
    }

    public void setFogManager(FogManager fogManager)
    {
        this.fogManager = fogManager;
    }

    //======================================================================================
    //======================================================================================
    //======================================================================================

    public static void main(String[] args) throws IOException
    {
        Stage stage = new Stage(50, 50);
        RoomBuilder rb = new RoomBuilder(stage);
        stage.createPeripheralCorridor();
        stage.createPeripheralWall();
        rb.fillStageWithRooms();
//        rb.obstructPercentageOfCorridors(10);
//
//        stage.saveToTxt();
//        Stage stageTxt = new Stage("./src/main/java/com/dungeoncrawler/Javiarenka/dungeonMapGenerator/txt/rooms_backup.txt");
//        RoomBuilder rb = new RoomBuilder(stageTxt);
        //rb.scanTilesForRooms();
        rb.obstructPercentageOfCorridors(60);
        rb.addDoorToAllRooms();
        rb.removeShortBranches(10);
        rb.removeCorridorClusters();
        rb.lockSomeDoor();
        buildDebugSite(stage);
        stage.saveToTxt();
    }

    public Stage generateMap(int width, int height) throws IOException
    {
        RoomBuilder rb;

        stage = new Stage(width, height);
        rb = new RoomBuilder(stage);
        stage.createPeripheralCorridor();
        stage.createPeripheralWall();
        rb.fillStageWithRooms();
        stage.saveToTxt("./src/main/java/com/dungeoncrawler/Javiarenka/dungeonMapGenerator/txt/rooms_backup.txt");
        rb.removeCorridorClusters();
        rb.obstructPercentageOfCorridors(60);
        rb.addDoorToAllRooms();
        rb.removeShortBranches(10);
        rb.lockSomeDoor();
        rb.hideRooms();
//        rb.closeRooms();

        setPartyManager(new PartyManager(stage));
        setFogManager(new FogManager(stage));
        stage.saveToTxt();
        //buildHtml(stage);
        //buildCSS(stage);

        return stage;
    }

    public static void buildDebugSite(Stage stage)
    {
        try
        {
            buildDebugHtml(stage);
            buildDebugCSS(stage);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void buildDebugHtml(Stage stage) throws IOException
    {
        File file = new File("./src/main/java/com/dungeoncrawler/Javiarenka/dungeonMapGenerator/webStuff/debug/stage.html");
        //File file = new File("./src/main/resources/templates/dungeonMap.html");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/com/dungeoncrawler/Javiarenka/dungeonMapGenerator/webStuff/debug/stageTemplate.html"));
        String s;

        while ((s = reader.readLine()) != null)
        {
            writer.write(s);
            writer.newLine();
        }


        writer.append("<div class=\"stage-container\">");
        writer.newLine();
        writer.append("\t").append("<div class=\"stage-grid\">");
        writer.newLine();

        for (int row = 0; row < stage.getHeight(); row++)
        {
            writer.append("\t\t");
            for (int col = 0; col < stage.getWidth(); col++)
            {
                writer.append(getDivForTile(stage.getTile(col, row)));
            }
            writer.newLine();
        }

        writer.append("\t").append("</div>");
        writer.newLine();
        writer.append("</div>");
        writer.newLine();

        writer.append("</body>").append("\n").append("</html>");
        reader.close();
        writer.close();
    }

    public static void buildDebugCSS(Stage stage) throws IOException
    {
        buildCSS(stage, "./src/main/java/com/dungeoncrawler/Javiarenka/dungeonMapGenerator/webStuff/debug/mapStyle.css");

    }

    public static void buildCSS(Stage stage) throws IOException
    {
        buildCSS(stage, "./src/main/resources/static/css/mapStyle.css");
    }

    public static void buildCSS(Stage stage, String pathname) throws IOException
    {
        File file = new File(pathname);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/com/dungeoncrawler/Javiarenka/dungeonMapGenerator/webStuff/stylesTemplate.css"));
        String s;

        while ((s = reader.readLine()) != null)
        {
            writer.write(s);
            writer.newLine();
        }

        writer.newLine();
        writer.newLine();

        writer.append(getGridCSSString(stage));
        //writer.append(getAllTilesCSSString(stage));

        reader.close();
        writer.close();
    }

    public static String getDivForTile(Tile tile)
    {
        StringBuilder sb = new StringBuilder();
        String type = tile.getType().name();

        sb.append("<div id=\"").append(tile.getIdString()).append("\" ");
        sb.append("class=\"tile ").append(type).append("\">");
        sb.append(tile.getIdString());  //wstawi koordynaty diva
        sb.append("</div>");

        return sb.toString();
    }

    public static String getGridCSSString(Stage stage)
    {
        StringBuilder sb = new StringBuilder();
        int columnsNo = stage.getWidth();
        int rowsNo = stage.getHeight();
        int mapHeightPercentage = 90;
        double widthRatio = columnsNo / rowsNo;

        sb.append(".stage-grid").append("{").append("\n");
        sb.append("\t").append("display: ").append("grid;").append("\n");
        sb.append("\t").append("grid-template-columns: ").append("repeat(").append(columnsNo).append(", 1fr);").append("\n");
        sb.append("\t").append("grid-template-rows: ").append("repeat(").append(rowsNo).append(", 1fr);").append("\n");
        sb.append("\t").append("grid-column-gap: ").append("0").append(";").append("\n");
        sb.append("\t").append("grid-row-gap: ").append("0").append(";").append("\n");
        sb.append("\t").append("height: ").append(mapHeightPercentage).append("vh").append(";").append("\n");
        sb.append("\t").append("width: ").append(mapHeightPercentage * widthRatio).append("vh").append(";").append("\n");
        sb.append("}").append("\n");

        sb.append("\n");

        //sb.append(".stage-grid div").append("{").append("\n");
        //sb.append("\t").append("border: ").append("rgb(0,0,0) ").append("solid").append(";").append("\n");
        //sb.append("\t").append("border-width: ").append("1px").append(";").append("\n");
        //sb.append("}").append("\n");

        return sb.toString();
    }

    public static String getAllTilesCSSString(Stage stage)
    {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < stage.getHeight(); row++)
        {
            for (int col = 0; col < stage.getWidth(); col++)
            {
                sb.append(getSingleTileCSSString(stage.getTile(col, row)));
                sb.append("\n");
            }

        }

        return sb.toString();
    }

    public static String getSingleTileCSSString(Tile tile)
    {
        StringBuilder sb = new StringBuilder();
        int column = tile.getX() + 1;
        int row = tile.getY() + 1;

        sb.append("#").append(tile.getIdString()).append(" {").append("\n");
        sb.append("\t").append("grid-area: ").append(column).append(" / ").append(row).append(" / ").append(column + 1).append(" / ").append(row + 1).append(";").append("\n");
        sb.append("}");

        sb.append("\n");

        //sb.append("#").append(tile.getIdString()).append(":hover ").append(" {").append("\n");

        return sb.toString();
    }

    public void save()
    {
        stage.saveThisStage();
        partyManager.saveThisPartyManager();
        fogManager.saveThisFogManager();
    }

    public void load()
    {
        String fileLocation = "src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/";
        String fileName = "stage.txt";
        Gson gson = new Gson();
        Stage loadedStage = new Stage();
        PartyManager loadedPartyManager = new PartyManager();

        Reader reader;

        try
        {
            reader = Files.newBufferedReader(Paths.get(fileLocation + fileName));
            loadedStage = gson.fromJson(reader, Stage.class);
            reader.close();

            fileName = "partyManager.txt";

            reader = Files.newBufferedReader(Paths.get(fileLocation + fileName));
            loadedPartyManager = gson.fromJson(reader, PartyManager.class);
            reader.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        PartyManager referencedPartyManager = new PartyManager(loadedStage);
        FogManager referencedFogManager = new FogManager(loadedStage);

        if (!Objects.isNull(loadedPartyManager.getParty()))
        {
            referencedPartyManager.spawnParty(loadedPartyManager.getParty());
            referencedFogManager.setParty(referencedPartyManager.getParty());
        }

        this.stage = loadedStage;
        //stage.getRooms().clear();
        stage.linkRoomTilesReferencesToStageTiles();
//        RoomBuilder rb = new RoomBuilder(stage);
//        rb.scanTilesForRooms();

        this.partyManager = referencedPartyManager;
        this.fogManager = referencedFogManager;
    }

}
