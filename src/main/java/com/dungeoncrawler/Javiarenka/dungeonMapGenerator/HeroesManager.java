package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import java.util.ArrayList;
import java.util.List;

public class HeroesManager
{
    private Stage stage;
    private final int partyLimit = 2;
    private List<HeroAvatar> heroes = new ArrayList();

    public HeroesManager(Stage stage)
    {
        this.stage = stage;
    }

    public List<HeroAvatar> getHeroes()
    {
        return heroes;
    }

    public void setHeroes(List<HeroAvatar> heroes)
    {
        this.heroes = heroes;
    }

    private int getNextId()
    {
        return heroes.size() + 1;
    }

    public HeroAvatar getHeroById(int targetId)
    {
        return heroes.stream()
                .filter(heroAvatar -> heroAvatar.getId() == targetId)
                .findFirst()
                .orElse(null);
    }

    public boolean isHeroSpawnable(Tile targetTile)
    {
        if (!targetTile.isWalkable()) return false;
        if (targetTile.isOccupied()) return false;
        if (heroes.size() >= partyLimit) return false;
        return true;
    }

    public boolean isHeroSpawnable(int xCoord, int yCoord)
    {
        return isHeroSpawnable(stage.getTile(xCoord, yCoord));
    }

    public HeroAvatar spawnHero(int xCoord, int yCoord)
    {
        return spawnHero(stage.getTile(xCoord, yCoord));
    }

    public HeroAvatar spawnHero(Tile targetTile)
    {
        HeroAvatar heroAv = new HeroAvatar(getNextId(), targetTile);
        heroes.add(heroAv);
        targetTile.setOccupied(true);
        return heroAv;
    }

    public void moveHero(HeroAvatar hero, Tile targetTile)
    {
        if(targetTile.isWalkable() && !targetTile.isOccupied())
        hero.setOccupiedTile(targetTile);
    }

    public void moveHero(HeroAvatar hero, int xCoord, int yCoord)
    {
        moveHero(hero, stage.getTile(xCoord, yCoord));
    }
}
