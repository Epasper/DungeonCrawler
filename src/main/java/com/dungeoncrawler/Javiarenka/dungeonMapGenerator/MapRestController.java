package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        service.getFogManager().updateVisibility();
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
        service.getFogManager().updateVisibility();
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

    @GetMapping("/openDoor")
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
                outputArray[1] = tn.getTouchingTilesCascade(targetTile, TileType.ROOM_LOCKED);
            }
        }
        targetTile.setType(newType);
        outputArray[0] = targetTile;

        return outputArray;
    }

    @GetMapping("/getVisibilityData")
    public List<Tile> getVisibleTiles()
    {
        return service.getFogManager().getVisibleTiles();
    }

    @GetMapping("saveMap")
    public void saveMap()
    {
        service.save();
    }

}
