package com.dungeoncrawler.Javiarenka.board;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.character.Monster;
import com.dungeoncrawler.Javiarenka.equipment.StartingArmor;
import com.dungeoncrawler.Javiarenka.equipment.StartingWeapon;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BoardService {
    private List<Hero> heroes = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();
    private Monster selectedMonster;
    private Hero selectedHero;
    private List<String> messageOutput = new ArrayList<>();
    private PartySelectorService partySelectorService = new PartySelectorService();

    public BoardService() {
        //todo: remove this call and insert the call for selected hero list.
        heroes = partySelectorService.loadAllHeroes();
        monsters.add(new Monster("Arrgard", 80, "Orc", 9));
        monsters.add(new Monster("Grinch", 30, "Goblin", 4));
        monsters.add(new Monster("Ragnar", 200, "Dragon", 15));
    }

    public List<String> getAllHeroNamesAndSurnames() {
        File folder = new File("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/");
        File[] listOfFiles = folder.listFiles();
        List<String> allHeroNamesAndSurnames = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().contains("---")) {
                    allHeroNamesAndSurnames.add(file.getName());
                    System.out.println("File " + file.getName());
                }
            }
        }
        return allHeroNamesAndSurnames;
    }

    public void loadAllHeroes() {
        heroes.clear();
        Gson gson = new Gson();
        for (String nameAndSurname : getAllHeroNamesAndSurnames()) {
            try {
                Reader reader = Files.newBufferedReader(Paths.get("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/" + nameAndSurname));
                Hero hero = gson.fromJson(reader, Hero.class);
                System.out.println(hero);
                heroes.add(hero);
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<String> getMessageOutput() {
        return messageOutput;
    }

    public void setMessageOutput(String messageOutput) {
        this.messageOutput = Collections.singletonList(messageOutput);
    }

    public static void attackMonster(Hero heroFromForm, Monster monsterFromForm) {
        heroFromForm.attack(monsterFromForm);
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
