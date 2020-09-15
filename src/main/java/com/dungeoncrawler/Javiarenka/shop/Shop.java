package com.dungeoncrawler.Javiarenka.shop;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.equipment.Armor;
import com.dungeoncrawler.Javiarenka.equipment.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {

    private static List<Armor> allAvailableArmor = new ArrayList<>(Arrays.asList(null, null, null, null, null)); // todo
    private static List<Weapon> allAvailableWeapon = new ArrayList<>(Arrays.asList(null, null, null, null, null)); // todo

    public static List<Armor> getNewArmor(Object object) {
        Hero hero = validation(object);
        return getAvailableArmour(getHeroClass(hero));
    }

    public static List<Weapon> getNewWeapon(Object object) {
        Hero hero = validation(object);
        return getAvailableWeapon(getHeroClass(hero));
    }

    private static List<Weapon> getAvailableWeapon(HeroClass heroClass) {
        return allAvailableWeapon
                .stream()
                .filter((Weapon weapon) -> weapon.getClassRestrictions().contains(heroClass))
                .collect(Collectors.toList());
    }

    private static List<Armor> getAvailableArmour(HeroClass heroClass) {
        return allAvailableArmor
                .stream()
                .filter((Armor armor) -> armor.getClassRestrictions().contains(heroClass))
                .collect(Collectors.toList());
    }

    private static HeroClass getHeroClass(Hero hero){
        return hero.getHeroClass;
    }

    private static Hero validation(Object object) {
        Hero hero = null;
        try {
            if (object instanceof Hero) {
                hero = (Hero) object;
                return hero;
            }
        } catch (IllegalArgumentException ex) {
            ex.getStackTrace();
        }
        return null;
    }
}
