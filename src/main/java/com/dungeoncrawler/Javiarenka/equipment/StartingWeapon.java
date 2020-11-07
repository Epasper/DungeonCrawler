package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.creature.HeroClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class StartingWeapon {
    public static final Weapon DAGGER = new Weapon("Dagger", List.of(HeroClass.ROGUE, HeroClass.WARRIOR, HeroClass.ARCHER), 5, 10, DamageType.DMG_SLASHING, 5);
    public static final Weapon CLUB = new Weapon("Club", List.of(HeroClass.KNIGHT, HeroClass.WARRIOR, HeroClass.HEALER), 7, 8, DamageType.DMG_BLUDGEONING, 5);
    public static final Weapon SHORT_SWORD = new Weapon("Short Sword", List.of(HeroClass.KNIGHT, HeroClass.WARRIOR, HeroClass.ROGUE), 7, 20, DamageType.DMG_SLASHING, 8);
    public static final Weapon SHORT_BOW = new Weapon("Short Bow", List.of(HeroClass.ROGUE, HeroClass.WARRIOR, HeroClass.ARCHER), 5, 10, DamageType.DMG_PIERCING, 8);
    public static final Weapon STAFF_OF_FIRE = new Weapon("Staff of Fire", List.of(HeroClass.WIZARD), 8, 20, DamageType.DMG_FIRE, 5);
    public static final List<Weapon> ALL_STARTING_WEAPON = Arrays.asList(DAGGER,CLUB,SHORT_SWORD,SHORT_BOW,STAFF_OF_FIRE);

    public static Weapon getWeaponByName(String weaponName){
        return ALL_STARTING_WEAPON
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(weaponName))
                .collect(Collectors.toList())
                .get(0);
    }
}
