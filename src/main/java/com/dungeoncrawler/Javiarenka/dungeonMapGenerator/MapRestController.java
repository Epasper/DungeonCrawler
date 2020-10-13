package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return service.getStage().getPartyManager().isPartySpawnable(coordX, coordY);
    }

    @GetMapping("/spawnParty")
    public PartyAvatar spawnParty(@RequestParam int coordX, @RequestParam int coordY)
    {
        PartyAvatar party = service.getStage().getPartyManager().spawnParty(coordX, coordY);
        System.out.println("party spawned on tile: " + coordX + "/" + coordY);
        return party;
    }

    @GetMapping("/checkWalkability")
    public boolean isTileWalkable(@RequestParam int coordX, @RequestParam int coordY)
    {
        return service.getStage().getTile(coordX, coordY).isWalkable();
    }

    @GetMapping("/moveParty")
    public void moveParty(@RequestParam int coordX, @RequestParam int coordY)
    {
        PartyManager pm = service.getStage().getPartyManager();
        pm.teleportParty(coordX, coordY);
    }

    @GetMapping("/stepParty")
    public void stepParty(@RequestParam Direction dir)
    {
        //TODO: nie można umieścić drużyny na polu, na którym wstępnie ją zespawnowano...

        PartyManager pm = service.getStage().getPartyManager();
        pm.movePartyOneStep(dir);
        System.out.println("Party moved: " + dir);
    }

    @GetMapping("/getParty")
    public PartyAvatar getParty()
    {
        return service.getStage().getPartyManager().getParty();
    }

    @GetMapping("/getClickedTile")
    public Tile clickedTile3(@RequestParam int coordX, @RequestParam int coordY, @RequestParam String message)
    {

        Tile clickedTile = service.getStage().getTile(coordX, coordY);

        if (!message.equals("")) System.out.println(message);

        return clickedTile;
    }

    @GetMapping("/movability")
    public Map<Direction, Boolean> checkMovability()
    {
        return service.getStage().getPartyManager().evaluateMovability();
    }
}
