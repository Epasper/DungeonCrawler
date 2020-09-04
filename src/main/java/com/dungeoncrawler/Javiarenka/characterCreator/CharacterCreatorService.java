package com.dungeoncrawler.Javiarenka.characterCreator;

import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.equipment.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CharacterCreatorService {

    private List<Equipment> startingGear = new ArrayList<>();
    private Map<String, Set<String>> characterClassToAvailableArmor = new HashMap<>();
    private Map<String, Set<String>> characterClassToAvailableWeapon = new HashMap<>();
    private List<HeroClass> availableClasses = Arrays.asList(HeroClass.values());
    private List<String> availableClassesStringified = new ArrayList<>();


    public CharacterCreatorService() {
        //ciekawostka:  List.of(); oraz Set.of(); to nowe metody z Javy 9
        characterClassToAvailableArmor.put(
                HeroClass.ARCHER.toString(), Set.of(
                        (StartingArmor.CLOTH_ARMOR.getName()),
                        (StartingArmor.RUSTED_CHAIN_ARMOR.getName())
                ));
        characterClassToAvailableArmor.put(
                HeroClass.WIZARD.toString(), Set.of(
                        (StartingArmor.CLOTH_ARMOR.getName())
                ));
        characterClassToAvailableArmor.put(
                HeroClass.ROGUE.toString(), Set.of(
                        (StartingArmor.CLOTH_ARMOR.getName()),
                        (StartingArmor.LEATHER_ARMOR.getName())
                ));
        characterClassToAvailableArmor.put(
                HeroClass.HEALER.toString(), Set.of(
                        (StartingArmor.CLOTH_ARMOR.getName())
                ));
        characterClassToAvailableArmor.put(
                HeroClass.WARRIOR.toString(), Set.of(
                        (StartingArmor.RUSTED_CHAIN_ARMOR.getName()),
                        (StartingArmor.LEATHER_ARMOR.getName())
                ));
        characterClassToAvailableArmor.put(
                HeroClass.KNIGHT.toString(), Set.of(
                        (StartingArmor.RUSTED_CHAIN_ARMOR.getName())
                ));
        characterClassToAvailableWeapon.put(
                HeroClass.WIZARD.toString(), Set.of(
                        (StartingWeapon.STAFF_OF_FIRE.getName())
                ));
        characterClassToAvailableWeapon.put(
                HeroClass.ARCHER.toString(), Set.of(
                        (StartingWeapon.DAGGER.getName()),
                        (StartingWeapon.SHORT_BOW.getName())
                ));
        characterClassToAvailableWeapon.put(
                HeroClass.ROGUE.toString(), Set.of(
                        (StartingWeapon.DAGGER.getName()),
                        (StartingWeapon.SHORT_BOW.getName())
                ));
        characterClassToAvailableWeapon.put(
                HeroClass.WARRIOR.toString(), Set.of(
                        (StartingWeapon.DAGGER.getName()),
                        (StartingWeapon.CLUB.getName()),
                        (StartingWeapon.SHORT_BOW.getName()),
                        (StartingWeapon.SHORT_SWORD.getName())
                ));
        characterClassToAvailableWeapon.put(
                HeroClass.KNIGHT.toString(), Set.of(
                        (StartingWeapon.CLUB.getName()),
                        (StartingWeapon.SHORT_SWORD.getName())
                ));
        characterClassToAvailableWeapon.put(
                HeroClass.HEALER.toString(), Set.of(
                        (StartingWeapon.CLUB.getName())
                ));
        for (HeroClass hc : availableClasses) {
            String classString = hc.toString();
            classString = classString.substring(0, 1).toUpperCase() + classString.substring(1).toLowerCase();
            availableClassesStringified.add(classString);
        }
    }

    public Map<String, Set<String>> getCharacterClassToAvailableArmor() {
        return characterClassToAvailableArmor;
    }

    public Map<String, Set<String>> getCharacterClassToAvailableWeapon() {
        return characterClassToAvailableWeapon;
    }

    public List<Equipment> getStartingGear() {
        return startingGear;
    }

    public List<HeroClass> getAvailableClasses() {
        return availableClasses;
    }

    public List<String> getAvailableClassesStringified() {
        return availableClassesStringified;
    }
}
