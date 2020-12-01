package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MapRestController
{
    @Autowired
    MapGeneratorService service;

    @GetMapping("/map")
    public Stage test()
    {
        return service.getStage();
    }

    @GetMapping("/checkSpawnability")
    public boolean isPartySpawnable(@RequestParam int coordX, @RequestParam int coordY)
    {
        return service.getPartyManager().isPartySpawnable(coordX, coordY);
    }

    @GetMapping("/spawnParty")
    public PartyAvatar spawnParty(@RequestParam int coordX, @RequestParam int coordY)
    {
        PartyAvatar party = service.getPartyManager().spawnParty(coordX, coordY);
        service.getFogManager().setParty(party);
        System.out.println("party spawned on tile: " + coordX + "/" + coordY);
        return party;
    }

    @GetMapping("/getMap")
    public Stage getMap()
    {
        return service.stage;
    }

    @GetMapping("/checkWalkability")
    public boolean isTileWalkable(@RequestParam int coordX, @RequestParam int coordY)
    {
        return service.getStage().getTile(coordX, coordY).isWalkable();
    }

    @GetMapping("/moveParty")
    public void moveParty(@RequestParam int coordX, @RequestParam int coordY)
    {
        PartyManager pm = service.getPartyManager();
        pm.teleportParty(coordX, coordY);
        //////service.getFogManager().updateVisibility();
    }

    @GetMapping("/stepParty")
    public void stepParty(@RequestParam Direction dir)
    {
        PartyManager pm = service.getPartyManager();
        if (pm.movePartyOneStep(dir))
        {
            System.out.println("Party moved: " + dir);
        }
        else
        {
            System.out.println("Party turned: " + dir);
        }
        //////service.getFogManager().updateVisibility();
    }

    @GetMapping("/getPartyData")
//    public PartyAvatar getPartyData()
    public Map<String, Object> getPartyData()
    {
        Map<String, Object> outputMap = new TreeMap<>();
        EncounterStatus encounterStatus = service.getPartyManager().checkEncounterStatus();

        outputMap.put("partyData", service.getPartyManager().getParty());
        outputMap.put("encounterStatus", encounterStatus);

        return outputMap;
    }

    @GetMapping("/getClickedTile")
    public Tile clickedTile(@RequestParam int coordX, @RequestParam int coordY, @RequestParam String message)
    {
        Tile clickedTile = service.getStage().getTile(coordX, coordY);
        if (!message.equals("")) System.out.println(message);
        System.out.println(clickedTile.getInfoString());
        return clickedTile;
    }

    @GetMapping("/movability")
    public Map<Direction, Boolean> checkMovability()
    {
        return service.getPartyManager().evaluateMovability();
    }

    @GetMapping("/getPointedTile")
    public Tile getPointedTile(@RequestParam Direction dir)
    {
        TileNavigator tn = new TileNavigator(service.getStage());
        return tn.getNextTile(service.getPartyManager().getParty().getOccupiedTile(), dir);
    }

    @GetMapping("/activateDoor")
    public Object[] updateTile(@RequestParam int coordX, @RequestParam int coordY, @RequestParam TileType newType)
    {

        Object[] outputArray = new Object[2];
        Tile targetTile = service.getStage().getTile(coordX, coordY);
        boolean willRoomChangeState = ((newType.isClosedDoor() && !targetTile.getType().isClosedDoor()) || (!newType.isClosedDoor() && targetTile.getType().isClosedDoor()));
        if (targetTile.getType().isDoor() && willRoomChangeState)
        {
            Room room = service.getStage().getRoomByDoor(targetTile);
            TileNavigator tn = new TileNavigator(service.getStage());
            if (newType == TileType.DOOR_OPENED)
            {
                room.unlockRoomTiles();
                outputArray[1] = tn.getTouchingTilesCascade(targetTile, TileType.ROOM);
            }
            else
            {
                room.lockRoomTiles();
                Map<Integer, Set<Tile>> tmpCascade = tn.getTouchingTilesCascade(targetTile, TileType.ROOM_LOCKED);
                Map<Integer, Set<Tile>> reversedKeyesOrder = new TreeMap<>();

                tmpCascade.keySet().forEach(key -> reversedKeyesOrder.put(tmpCascade.size() - 1 - key, tmpCascade.get(key)));
                outputArray[1] = reversedKeyesOrder;
            }
        }
        targetTile.setType(newType);
        outputArray[0] = targetTile;

        return outputArray;
    }

    @GetMapping("/getVisibilityData")
    public Map<String, Object> getVisibleTiles()
    {
        Map<String, Object> outputMap = new TreeMap<>();
        Map<Tile, Double> tilesWithDistancesSorted = service.getFogManager().currentlyVisibleTilesByDistance;

        service.getFogManager().updateVisibility();

        outputMap.put("previouslyVisibleTiles", service.getFogManager().previouslyVisibleTiles);
        outputMap.put("currentlyVisibleTiles", service.getFogManager().currentlyVisibleTiles);
        outputMap.put("tilesSortedByDistance", tilesWithDistancesSorted.keySet());
        outputMap.put("distancesSorted", tilesWithDistancesSorted.values());
        outputMap.put("newlySeenTiles", service.getFogManager().getNewlySeenTiles());

        return outputMap;
    }

    @GetMapping("/getSeenTiles")
    public Set<Tile> getSeenTiles()
    {
        return service.getFogManager().getAlreadySeenTiles();
    }

    @GetMapping("/loadEncountersStates")
    public Map<String, EncounterStatus> loadEncountersStates()
    {
        Map<String, EncounterStatus> outputMap = new HashMap<>();
        service.getStage().getRooms().forEach(room -> {
            EncounterStatus roomStatus = room.getEncounterStatus();
            List<Tile> innerTiles = room.getRoomInnerTiles();
            innerTiles.forEach(tile -> outputMap.put(tile.toStringForJsonMap(), roomStatus));
        });

        return outputMap;
    }

    @GetMapping("/wasRoomVisited")
    public boolean wasRoomVisited(@RequestParam int coordX, @RequestParam int coordY)
    {
        Tile roomTile = service.getStage().getTile(coordX, coordY);
        Room room = service.getStage().getRoomByTile(roomTile);
        return room.isAlreadyVisited();
    }

    @GetMapping("/visitRoom")
    public List<Tile> visitRoom(@RequestParam int coordX, @RequestParam int coordY)
    {
        Tile roomTile = service.getStage().getTile(coordX, coordY);
        Room room = service.getStage().getRoomByTile(roomTile);
        room.setEncounterStatus(EncounterStatus.AFTER);
        //room.setAlreadyVisited(true);

        System.out.println();
        System.out.println("Room visited.");
        System.out.println(room.asString());
        return room.getRoomInnerTiles();
    }

    @GetMapping("/roomEncounter")
    public List<Tile> roomEncounter (@RequestParam int coordX, @RequestParam int coordY, @RequestParam EncounterStatus newEncounterState)
    {
        Tile roomTile = service.getStage().getTile(coordX, coordY);
        Room room = service.getStage().getRoomByTile(roomTile);
        room.setEncounterStatus(newEncounterState);
        //room.setAlreadyVisited(true);
        return room.getRoomInnerTiles();
    }

    @GetMapping("/saveMap")
    public void saveMap(@RequestParam String saveSlotIdentifier)
    {
        service.save(saveSlotIdentifier);
    }

    @GetMapping("/checkIfSaveExists")
    public boolean checkIfSaveExists(@RequestParam int slotNumber)
    {
        String fileLocation = "src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/";
        String stageSaveName = "save-" + Integer.toString(slotNumber) + "_stage.txt";
        String partySaveName = "save-" + Integer.toString(slotNumber) + "_party.txt";

        File checkedSavedStage = new File(fileLocation + stageSaveName);
        File checkedSavedParty = new File(fileLocation + partySaveName);

        return (checkedSavedStage.exists() && checkedSavedParty.exists());
    }

    @GetMapping("/deleteSave")
    public void deleteSave(@RequestParam int saveSlotIdentifier)
    {
        String fileLocation = "src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/";
        String stageSaveName = "save-" + Integer.toString(saveSlotIdentifier) + "_stage.txt";
        String partySaveName = "save-" + Integer.toString(saveSlotIdentifier) + "_party.txt";

        File savedStageToDelete = new File(fileLocation + stageSaveName);
        File savedPartyToDelete = new File(fileLocation + partySaveName);

        savedStageToDelete.delete();
        savedPartyToDelete.delete();
    }

    @GetMapping("/getSaveInfo")
    public String getSaveInfo(@RequestParam int saveSlotIdentifier)
    {
        String fileLocation = "src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/";
        String stageSaveName = "save-" + Integer.toString(saveSlotIdentifier) + "_stage.txt";
        StringBuilder saveInfo = new StringBuilder();

        try
        {
            //Reader reader = Files.newBufferedReader(Paths.get(fileLocation + stageSaveName));
            BufferedReader br = new BufferedReader(new FileReader(fileLocation + stageSaveName));
            String sCurrentLine;
            String lastLine = "";
            boolean foundWidth = false;
            boolean foundHeight = false;
            int width = 0;
            int height = 0;

            while ((sCurrentLine = br.readLine()) != null)
            {
//                System.out.println(sCurrentLine);
                if (sCurrentLine.contains("width") && !foundWidth)
                {
                    String[] widthLineSplit = sCurrentLine.split(":");
                    String strWidth = widthLineSplit[widthLineSplit.length - 1]
                            .replace(",","")
                            .replace(" ", "");
                    width = Integer.parseInt(strWidth);
                    foundWidth = true;
                }

                if (sCurrentLine.contains("height") && !foundHeight)
                {
                    String[] heightLineSplit = sCurrentLine.split(":");
                    String strHeight = heightLineSplit[heightLineSplit.length - 1]
                            .replace(",","")
                            .replace(" ", "");
                    height = Integer.parseInt(strHeight);
                    foundHeight = true;
                }

                lastLine = sCurrentLine;
            }

            saveInfo.append("Map ").append(width).append("x").append(height).append(" | Saved at: ").append(lastLine);
            br.close();

        } catch (IOException e)
        {
            //e.printStackTrace();
            saveInfo.append("Empty slot");
        }

        return saveInfo.toString();
    }
}
