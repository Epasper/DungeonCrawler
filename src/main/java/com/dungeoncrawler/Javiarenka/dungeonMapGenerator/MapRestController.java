package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

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

    @GetMapping("/getParty")
    public PartyAvatar getParty()
    {
        return service.getPartyManager().getParty();
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

    @GetMapping("/saveMap")
    public void saveMap(@RequestParam int saveSlotNumber)
    {
        service.save(saveSlotNumber);
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
    public void deleteSave(@RequestParam int saveSlotNumber)
    {
        String fileLocation = "src/main/java/com/dungeoncrawler/Javiarenka/dataBase/dungeonMap/";
        String stageSaveName = "save-" + Integer.toString(saveSlotNumber) + "_stage.txt";
        String partySaveName = "save-" + Integer.toString(saveSlotNumber) + "_party.txt";

        File savedStageToDelete = new File(fileLocation + stageSaveName);
        File savedPartyToDelete = new File(fileLocation + partySaveName);

        savedStageToDelete.delete();
        savedPartyToDelete.delete();
    }
}
