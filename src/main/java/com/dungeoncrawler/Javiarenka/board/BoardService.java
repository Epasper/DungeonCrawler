package com.dungeoncrawler.Javiarenka.board;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.Monster;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BoardService {
    private List<Hero> heroes;
    private List<Monster> monsters = new ArrayList<>();
    private Monster selectedMonster;
    private Hero selectedHero;
    private List<String> messageOutput = new ArrayList<>();
    private PartySelectorService partySelectorService = new PartySelectorService();
    private int boardWidth;
    private int boardHeight;
    private EncounterTile[][] tiles;
    private Map<String, String> imageSources = new HashMap<>();
    Random random = new Random();
    private final String grassImageSource = "../images/encounterTiles/GrassTile1.png";
    private final String stoneImageSource = "../images/encounterTiles/StoneFloor";
    private final String rubbleImageSource = "../images/encounterTiles/rubble";

    public BoardService() {
        heroes = new ArrayList<>();
        messageOutput.add("Fight log:");
        prepareTheBoard();
        clearSelectedHeroes();
        heroes = partySelectorService.loadSelectedHeroes();
        rollForInitialYCoordinates();
        monsters.add(new Monster("Arrgard", 80, "Orc", 9));
        monsters.add(new Monster("Grinch", 30, "Goblin", 4));
        monsters.add(new Monster("Ragnar", 200, "Dragon", 15));
    }

    private void prepareTheBoard() {
        //todo: replace the hardcoded values with values from the entered room upon entering
        boardHeight = 8;
        boardWidth = 16;
        tiles = new EncounterTile[boardWidth][boardHeight];
        rollForBoardTiles();
    }

    private void rollForInitialYCoordinates() {
        List<Integer> YPositions = new ArrayList<>();
        while (YPositions.size() < heroes.size()) {
            int rollForInitialPosition = random.nextInt(boardHeight - 1) + 1;
            int rollUpOrDown = Math.random() > 0.5 ? 1 : -1;
            if (rollUpOrDown > 0 && rollForInitialPosition + heroes.size() > boardHeight) {
                continue;
            } else if (rollForInitialPosition - heroes.size() < 1) {
                continue;
            }
            for (int i = 0; i < heroes.size(); i++) {
                YPositions.add(rollForInitialPosition + rollUpOrDown);
                rollForInitialPosition += rollUpOrDown;
            }
        }
        System.out.println(heroes.size());
        for (Hero hero : heroes) {
            int randomListElement = random.nextInt(YPositions.size());
            hero.setEncounterYPosition(YPositions.get(randomListElement));
            YPositions.remove(randomListElement);
            hero.setEncounterXPosition(1);
            System.out.println(hero.getEncounterXPosition() + "-" + hero.getEncounterYPosition());
        }
    }

    private void rollForBoardTiles() {
        int typeOfTile;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                EncounterTile currentTile = new EncounterTile();
                typeOfTile = random.nextInt(100);
                if (typeOfTile < 5) {
                    currentTile.setTileType(EncounterTileType.GRASS);
                    imageSources.put((i + 1) + "---" + (j + 1), grassImageSource);
                } else if (typeOfTile < 15) {
                    currentTile.setTileType(EncounterTileType.RUBBLE);
                    int randomElement = random.nextInt(3) + 1;
                    imageSources.put((i + 1) + "---" + (j + 1), rubbleImageSource + randomElement + ".png");
                } else {
                    currentTile.setTileType(EncounterTileType.STONE);
                    int randomElement = random.nextInt(7) + 1;
                    imageSources.put((i + 1) + "---" + (j + 1), stoneImageSource + randomElement + ".png");
                }
                tiles[i][j] = currentTile;
            }
        }
    }

    public Map<String, String> getImageSources() {
        return imageSources;
    }

    public void setImageSources(Map<String, String> imageSources) {
        this.imageSources = imageSources;
    }

    public EncounterTile[][] getTiles() {
        return tiles;
    }

    public void setTiles(EncounterTile[][] tiles) {
        this.tiles = tiles;
    }

    public void setMessageOutput(List<String> messageOutput) {
        this.messageOutput = messageOutput;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public void clearSelectedHeroes() {
        heroes.clear();
    }

    public List<String> getAllHeroNamesAndSurnames() {
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

    public void loadAllHeroes() {
        heroes.clear();
        Gson gson = new Gson();
        for (String nameAndSurname : getAllHeroNamesAndSurnames()) {
            try {
                Reader reader = Files.newBufferedReader(Paths.get("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/" + nameAndSurname));
                Hero hero = gson.fromJson(reader, Hero.class);
                heroes.add(hero);
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public PartySelectorService getPartySelectorService() {
        return partySelectorService;
    }

    public void setPartySelectorService(PartySelectorService partySelectorService) {
        this.partySelectorService = partySelectorService;
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
