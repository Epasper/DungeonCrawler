package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.HeroClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class StartingArmor {
    public static final Armor CLOTH_ARMOR = new Armor("Cloth Armor", List.of(HeroClass.WIZARD, HeroClass.ROGUE, HeroClass.HEALER), 5, 10, 4, 10);
    public static final Armor LEATHER_ARMOR = new Armor("Leather Armor", List.of(HeroClass.ARCHER, HeroClass.ROGUE, HeroClass.WARRIOR), 8, 20, 10, 30);
    public static final Armor RUSTED_CHAIN_ARMOR = new Armor("Rusted Chain Armor", List.of(HeroClass.KNIGHT, HeroClass.WARRIOR, HeroClass.ARCHER), 12, 30, 15, 50);
    public static List<Armor> ALL_STARTING_ARMOR = Arrays.asList(CLOTH_ARMOR,LEATHER_ARMOR,RUSTED_CHAIN_ARMOR);

    public static Armor getArmorByName(String armorName) {
        return ALL_STARTING_ARMOR
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(armorName))
                .collect(Collectors.toList())
                .get(0);
    }
}
