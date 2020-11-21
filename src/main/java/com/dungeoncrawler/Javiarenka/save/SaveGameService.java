package com.dungeoncrawler.Javiarenka.save;

import com.dungeoncrawler.Javiarenka.creature.Hero;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SaveGameService {

    PartySelectorService partySelectorService = new PartySelectorService();

    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/Selected Party.txt"));
            List<String> listOfNamesAndSurnames = gson.fromJson(reader, new TypeToken<List<String>>() {
            }.getType());
            List <Hero> heroList = new ArrayList<>();
            for (String name : listOfNamesAndSurnames) {
                heroList.add(partySelectorService.loadAHeroByNameAndSurname(name));
            }
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/saves/Saved Game " + getNextFreeSaveNumber() + ".txt");
            gson.toJson(heroList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNextFreeSaveNumber() {
        File folder = new File("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/saves");
        File[] listOfFiles = folder.listFiles();
        return listOfFiles.length + 1;
    }
}