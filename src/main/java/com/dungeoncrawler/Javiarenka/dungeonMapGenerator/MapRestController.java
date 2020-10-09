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

    //@GetMapping("/isSpawnable")
    @GetMapping("/checkSpawnability")
    public boolean isHeroSpawnable(@RequestParam int coordX, @RequestParam int coordY)
    {
        return service.getStage().getHeroesManager().isHeroSpawnable(coordX, coordY);
    }

    @GetMapping("/spawnHero")
    public HeroAvatar spawnHero(@RequestParam int coordX, @RequestParam int coordY)
    {
        return service.getStage().getHeroesManager().spawnHero(coordX, coordY);
    }

    @GetMapping("/moveHero")
    public void movwHero(@RequestParam int coordX, @RequestParam int coordY, @RequestParam int id)
    {
        HeroesManager hm = service.getStage().getHeroesManager();
        hm.moveHero(hm.getHeroById(id), coordX, coordY);
    }

    @GetMapping("/getHeroes")
    public List<HeroAvatar> getHeroesList()
    {
        return service.getStage().getHeroesManager().getHeroes();
    }

    @PostMapping("/clickTile")
    public void clickedTile(@RequestBody String message)
    {
        System.out.println(message);
    }

    @PostMapping("/getClickedTile")
    public Tile clickedTile2(@RequestBody Map<String, Integer> input)
    {
        return service.getStage().getTile(input.get("x"), input.get("y"));
    }
}
