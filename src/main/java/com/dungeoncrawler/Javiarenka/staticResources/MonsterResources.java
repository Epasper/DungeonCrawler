package com.dungeoncrawler.Javiarenka.staticResources;

import com.dungeoncrawler.Javiarenka.character.Monster;
import com.dungeoncrawler.Javiarenka.character.MonsterRace;

import java.util.Arrays;
import java.util.List;

public interface MonsterResources {
    static List<Monster> defaultMonsters() {
        return Arrays.asList(
                new Monster("Goblin Brawler", 80, "../images/monsters/monster_goblinscout.png",
                        MonsterRace.GOBLIN, 9),
                new Monster("Rat", 30, "../images/monsters/monster_ratling.png",
                        MonsterRace.ANIMAL, 4),
                new Monster("Skeleton Warrior", 200, "../images/monsters/monster_skeleton.png",
                        MonsterRace.UNDEAD, 15),
                new Monster("Imp", 100, "../images/monsters/monster_imp.png",
                        MonsterRace.FIEND, 12),
                new Monster("Red Dragon", 500, "../images/monsters/monster_dragonred.png",
                        MonsterRace.DRAGON, 40),
                new Monster("Centaur", 300, "../images/monsters/monster_centaur.png",
                        MonsterRace.MONSTROSITY, 20));
    }
}
