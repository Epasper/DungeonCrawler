package com.dungeoncrawler.Javiarenka.partySelector;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartySelectorService {

    private List<Hero> allAvailableHeroes;
    private List<Hero> selectedHeroes;

    public PartySelectorService() {
        this.allAvailableHeroes = loadAllHeroes();
    }

    public List<Hero> getAllAvailableHeroes() {
        return allAvailableHeroes;
    }

    public void setAllAvailableHeroes(List<Hero> allAvailableHeroes) {
        this.allAvailableHeroes = allAvailableHeroes;
    }

    public List<Hero> loadSelectedHeroes() {
        Gson gson = new Gson();
        List<Hero> selectedHeroes = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/Selected Party.txt"));
            List<String> listOfNamesAndSurnames = gson.fromJson(reader, new TypeToken<List<String>>() {
            }.getType());
            for (String nameAndSurname : listOfNamesAndSurnames) {
                Hero hero = loadAHeroByNameAndSurname(nameAndSurname + ".txt");
                selectedHeroes.add(hero);
            }
        } catch (IOException e) {
            selectedHeroes = new ArrayList<>();
        }
        System.out.println(selectedHeroes);
        return selectedHeroes;
    }

    public Hero loadAHeroByNameAndSurname(String nameAndSurname) {
        Hero hero = new Hero();
        try {
            String fileLocation = "src/main/java/com/dungeoncrawler/Javiarenka/dataBase/heroes/" + nameAndSurname;
            fileLocation = fileLocation.endsWith(".txt") ? fileLocation : fileLocation + ".txt";
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(fileLocation));
            hero = gson.fromJson(reader, Hero.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hero;
    }

    public List<Hero> loadAllHeroes() {
        List<Hero> allHeroes = new ArrayList<>();
        for (String nameAndSurname : getAllHeroNamesAndSurnames()) {
            Hero hero = loadAHeroByNameAndSurname(nameAndSurname);
            allHeroes.add(hero);
        }
        return allHeroes;
    }

    private List<String> getAllHeroNamesAndSurnames() {
        File folder = new File("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/heroes/");
        File[] listOfFiles = folder.listFiles();
        List<String> allHeroNamesAndSurnames = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().contains("---")) {
                    allHeroNamesAndSurnames.add(file.getName());
                }
            }
        }
        return allHeroNamesAndSurnames;
    }

    public void saveTheParty(List<String> listOfChosenNames) {
        for (Hero hero : this.loadAllHeroes()) {
            hero.setSelectedForParty(false);
            hero.saveThisHero();
        }
        for (String heroName : listOfChosenNames) {
            Hero hero = loadAHeroByNameAndSurname(heroName);
            hero.setSelectedForParty(true);
            hero.saveThisHero();
        }
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/Selected Party.txt");
            gson.toJson(listOfChosenNames, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
