package com.dungeoncrawler.Javiarenka.shop;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.equipment.Equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {

    private static List<Equipment> allAvailableEquipment = new ArrayList<>(Arrays.asList(null, null, null, null, null)); // todo: list of available Equipment

    private static List<Equipment> getNewEquipment(Object object) {
        Hero hero = validation(object);
        return getAvailableEquipment(hero);
    }

    private static List<Equipment> getAvailableEquipment(Hero hero) {
        return allAvailableEquipment
                .stream()
                .filter((Equipment equipment) ->
                        equipment.getClassRestrictions().contains(hero.getHeroClass())
                                && equipment.getPrice() <= hero.getMoney()) // todo: shopping with money, gems and gold
                .collect(Collectors.toList());
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
