package com.dungeoncrawler.Javiarenka.board;

import com.dungeoncrawler.Javiarenka.creature.Creature;
import com.dungeoncrawler.Javiarenka.creature.Hero;
import com.dungeoncrawler.Javiarenka.creature.Monster;
import com.dungeoncrawler.Javiarenka.load.LoadGameService;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private List<Hero> heroes;
    private List<Monster> monsters = new ArrayList<>();
    private List<Creature> initiativeOrder = new ArrayList<>();
    private Monster selectedMonster;
    private Hero selectedHero;
    private List<String> messageOutput = new ArrayList<>();
    private PartySelectorService partySelectorService = new PartySelectorService();
    private LoadGameService loadGameService = new LoadGameService();
    private int boardWidth;
    private int boardHeight;
    private static final int maxInitiative = 200;
    private EncounterTile[][] tiles;
    private Map<String, String> imageSources = new HashMap<>();
    Random random = new Random();
    public static boolean isAlreadyLoaded = false;
    private final String grassImageSource = "../images/encounterTiles/GrassTile1.png";
    private final String stoneImageSource = "../images/encounterTiles/StoneFloor";
    private final String rubbleImageSource = "../images/encounterTiles/rubble";

    public BoardService() {
        heroes = new ArrayList<>();
        messageOutput.add("Fight log:");
        Monster testMon1 = new Monster(80, "Goblin", 9);
        Monster testMon2 = new Monster(30, "Animal", 4);
        Monster testMon3 = new Monster(200, "Undead", 15);
        testMon1.setImageLink("../images/monsters/monster_goblinscout.png");
        testMon1.setName("Goblin Warrior");
        testMon2.setImageLink("../images/monsters/monster_ratling.png");
        testMon2.setName("Rat");
        testMon3.setImageLink("../images/monsters/monster_skeleton.png");
        testMon3.setName("Skeleton Warrior");
        monsters.addAll(List.of(testMon1, testMon2, testMon3));
        if (!isAlreadyLoaded) prepareTheBoard();
    }

    public void prepareTheBoard() {
        //todo: replace the hardcoded values with values from the entered room upon entering
        boardHeight = 8;
        boardWidth = 16;
        tiles = new EncounterTile[boardWidth][boardHeight];
        rollForBoardTiles();
        clearSelectedHeroes();
        heroes = loadGameService.load();
        //TODO ma pobierać z Save game
        rollForInitialYCoordinates();
        rollForInitiative();
        setHeroImages();
    }

    //todo: change the images from class based to those customized in character creation (after such customization is possible)
    private void setHeroImages() {
        for (Hero hero : heroes) {
            hero.setImageLink("../images/" + hero.getClassName() + ".png");
        }
    }

    private void rollForInitiative() {
        List<Creature> tempCharList = new ArrayList<>();
        for (Hero hero : heroes) {
            int initRoll = random.nextInt(maxInitiative);
            hero.setInitiative(initRoll);
            hero.setActive(false);
            tempCharList.add(hero);
        }
        for (Monster monster : monsters) {
            int initRoll = random.nextInt(maxInitiative);
            monster.setInitiative(initRoll);
            monster.setActive(false);
            tempCharList.add(monster);
        }
        initiativeOrder = tempCharList.stream()
                .sorted(Comparator.comparing(Creature::getInitiative))
                .collect(Collectors.toList());
        initiativeOrder.get(0).setActive(true);
        for (Creature c : initiativeOrder) {
            System.out.println(c.getInitiative() + ": " + c.toString());
        }
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

        List<Integer> monsterYPositions = new ArrayList<>();
        while (monsterYPositions.size() < monsters.size()) {
            int rollForInitialPosition = random.nextInt(boardHeight - 1) + 1;
            int rollUpOrDown = Math.random() > 0.5 ? 1 : -1;
            if (rollUpOrDown > 0 && rollForInitialPosition + monsters.size() > boardHeight) {
                continue;
            } else if (rollForInitialPosition - monsters.size() < 1) {
                continue;
            }
            for (int i = 0; i < monsters.size(); i++) {
                monsterYPositions.add(rollForInitialPosition + rollUpOrDown);
                rollForInitialPosition += rollUpOrDown;
            }
        }
        System.out.println(monsters.size());
        for (Monster monster : monsters) {
            int randomListElement = random.nextInt(monsterYPositions.size());
            monster.setEncounterYPosition(monsterYPositions.get(randomListElement));
            monsterYPositions.remove(randomListElement);
            monster.setEncounterXPosition(boardWidth);
            System.out.println(monster.getEncounterXPosition() + "-" + monster.getEncounterYPosition());
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

    public List<Creature> getInitiativeOrder() {
        return initiativeOrder;
    }

    public void setInitiativeOrder(List<Creature> initiativeOrder) {
        this.initiativeOrder = initiativeOrder;
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
