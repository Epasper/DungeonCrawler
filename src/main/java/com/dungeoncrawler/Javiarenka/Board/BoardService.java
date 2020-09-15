package com.dungeoncrawler.Javiarenka.Board;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.character.Monster;
import com.dungeoncrawler.Javiarenka.equipment.Armor;
import com.dungeoncrawler.Javiarenka.equipment.StartingArmor;
import com.dungeoncrawler.Javiarenka.equipment.StartingWeapon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class BoardService {
    private List<Hero> heroes = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();
    private Monster selectedMonster;
    private Hero selectedHero;

    public BoardService() {
        heroes.add(new Hero("Joe", 70, "Jenkins", HeroClass.ARCHER, StartingArmor.CLOTH_ARMOR, StartingWeapon.SHORT_BOW, 20));
        heroes.add(new Hero("Mike", 120, "Trashold", HeroClass.KNIGHT, StartingArmor.RUSTED_CHAIN_ARMOR, StartingWeapon.SHORT_SWORD, 12));
        heroes.add(new Hero("Chase", 50, "Kingsman", HeroClass.WIZARD, StartingArmor.LEATHER_ARMOR, StartingWeapon.STAFF_OF_FIRE, 15));
        monsters.add(new Monster("Arrgard", 80, "Orc", 9));
        monsters.add(new Monster("Grinch", 30, "Goblin", 4));
        monsters.add(new Monster("Ragnar", 200, "Dragon", 15));
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public Monster getSelectedMonster() {
        return selectedMonster;
    }

    public void setSelectedMonster(Monster selectedMonster) {
        this.selectedMonster = selectedMonster;
    }

    public Hero getSelectedHero() {
        return selectedHero;
    }

    public void setSelectedHero(Hero selectedHero) {
        this.selectedHero = selectedHero;
    }

    public Hero getCurrentHeroByName(String heroName) {
        return heroes
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(heroName))
                .findFirst()
                .orElseThrow();
    }

    public Monster getCurrentMonsterByName(String monsterName) {
        return monsters
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(monsterName))
                .findFirst()
                .orElseThrow();
    }
}
