package com.dungeoncrawler.Javiarenka.load;

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
public class LoadGameService {


    public void load(String numberToLoad) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/saves/Saved Game " + numberToLoad + ".txt"));
            List<Hero> listOfHeroes = gson.fromJson(reader, new TypeToken<List<Hero>>() {
            }.getType());
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/currentGame.txt");
            gson.toJson(listOfHeroes, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Hero> load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();
        List<Hero> listOfHeroes = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/currentGame.txt"));
            listOfHeroes = gson.fromJson(reader, new TypeToken<List<Hero>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfHeroes;
    }

    public List<String> getAllSaves() {
            File folder = new File("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/saves/");
            File[] listOfFiles = folder.listFiles();
            List<String> allSaves = new ArrayList<>();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        String toAdd = file.getName().substring(0,file.getName().indexOf('.'));
                        allSaves.add(toAdd);
                    }
                }
            }
            return allSaves;
        }
    }
