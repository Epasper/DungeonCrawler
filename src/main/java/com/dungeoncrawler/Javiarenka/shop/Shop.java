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

    private List<Armor> allAvailableArmor = new ArrayList<>(Arrays.asList(null, null, null, null, null)); // todo
    private List<Weapon> allAvailableWeapon = new ArrayList<>(Arrays.asList(null, null, null, null, null)); // todo

    public List<Armor> getNewArmor(Object object) {
        validation(object);
        Hero hero = (Hero) object;
        return getAvailableArmour(hero.getHeroClass());
    }

    public List<Weapon> getNewWeapon(Object object) {
        validation(object);
        Hero hero = (Hero) object;
        return getAvailableWeapon(hero.getHeroClass());
    }

    private List<Weapon> getAvailableWeapon(HeroClass heroClass) {
        return allAvailableWeapon
                .stream()
                .filter((Weapon weapon) -> weapon.getClassRestrictions().contains(heroClass))
                .collect(Collectors.toList());
    }

    private List<Armor> getAvailableArmour(HeroClass heroClass) {
        return allAvailableArmor
                .stream()
                .filter((Armor armor) -> armor.getClassRestrictions().contains(heroClass))
                .collect(Collectors.toList());
    }

    private boolean validation(Object object) {
        try {
            if (!(object instanceof Hero)) {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
