package com.dungeoncrawler.Javiarenka.shop;

import com.dungeoncrawler.Javiarenka.creature.Hero;
import com.dungeoncrawler.Javiarenka.equipment.Equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {

    private static List<Equipment> allAvailableEquipment = new ArrayList<>(Arrays.asList(null, null, null, null, null)); // todo: list of available Equipment

    // public static List<Equipment> getNewEquipment(Object object) {
    //     return getAvailableEquipment(hero);
    // }

    // private static List<Equipment> getAvailableEquipment(Hero hero) {
    //     return allAvailableEquipment
    //             .stream()
    //             .filter((Equipment equipment) ->
    //                     equipment.getClassRestrictions().contains(hero.getHeroClass())
    //                             && equipment.getPrice() <= hero.getMoney()) // todo: shopping with money, gems and gold
    //             .collect(Collectors.toList());
    // }

    
}
