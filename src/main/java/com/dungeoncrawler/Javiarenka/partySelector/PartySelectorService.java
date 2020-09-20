package com.dungeoncrawler.Javiarenka.partySelector;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartySelectorService {

    private List<Hero>allAvailableHeroes;

    public PartySelectorService() {
        this.allAvailableHeroes = loadAllHeroes();
    }

    public List<Hero> getAllAvailableHeroes() {
        return allAvailableHeroes;
    }

    public void setAllAvailableHeroes(List<Hero> allAvailableHeroes) {
        this.allAvailableHeroes = allAvailableHeroes;
    }

    public List<Hero> loadAllHeroes() {
        List<Hero> allHeroes = new ArrayList<>();
        Gson gson = new Gson();
        for (String nameAndSurname : getAllHeroNamesAndSurnames()) {
            try {
                Reader reader = Files.newBufferedReader(Paths.get("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/" + nameAndSurname));
                Hero hero = gson.fromJson(reader, Hero.class);
                allHeroes.add(hero);
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return allHeroes;
    }

    private List<String> getAllHeroNamesAndSurnames() {
        File folder = new File("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/");
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
}
