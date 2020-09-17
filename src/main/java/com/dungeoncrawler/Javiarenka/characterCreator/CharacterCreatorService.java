package com.dungeoncrawler.Javiarenka.characterCreator;

import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.equipment.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CharacterCreatorService {

    private List<Weapon> startingWeapons = new ArrayList<>();
    private List<Armor> startingArmors = new ArrayList<>();
    private Map<String, Set<String>> characterClassToAvailableArmor = new HashMap<>();
    private Map<String, Set<String>> characterClassToAvailableWeapon = new HashMap<>();
    private List<HeroClass> availableClasses = Arrays.asList(HeroClass.values());
    private List<String> availableClassesStringified = new ArrayList<>();

    public CharacterCreatorService() {
        verifyAndAddStartingEquipment(HeroClass.ARCHER, StartingArmor.RUSTED_CHAIN_ARMOR, StartingArmor.LEATHER_ARMOR);
        verifyAndAddStartingEquipment(HeroClass.WIZARD, StartingArmor.CLOTH_ARMOR);
        verifyAndAddStartingEquipment(HeroClass.ROGUE, StartingArmor.CLOTH_ARMOR, StartingArmor.LEATHER_ARMOR);
        verifyAndAddStartingEquipment(HeroClass.HEALER, StartingArmor.CLOTH_ARMOR);
        verifyAndAddStartingEquipment(HeroClass.WARRIOR, StartingArmor.RUSTED_CHAIN_ARMOR, StartingArmor.LEATHER_ARMOR);
        verifyAndAddStartingEquipment(HeroClass.KNIGHT, StartingArmor.RUSTED_CHAIN_ARMOR);
        verifyAndAddStartingEquipment(HeroClass.WIZARD, StartingWeapon.STAFF_OF_FIRE);
        verifyAndAddStartingEquipment(HeroClass.ARCHER, StartingWeapon.DAGGER, StartingWeapon.SHORT_BOW);
        verifyAndAddStartingEquipment(HeroClass.ROGUE, StartingWeapon.DAGGER, StartingWeapon.SHORT_SWORD, StartingWeapon.SHORT_BOW);
        verifyAndAddStartingEquipment(HeroClass.WARRIOR, StartingWeapon.DAGGER, StartingWeapon.CLUB, StartingWeapon.SHORT_BOW, StartingWeapon.SHORT_SWORD);
        verifyAndAddStartingEquipment(HeroClass.KNIGHT, StartingWeapon.CLUB);
        verifyAndAddStartingEquipment(HeroClass.HEALER, StartingWeapon.CLUB);
        for (HeroClass hc : availableClasses) {
            String classString = hc.toString();
            classString = classString.substring(0, 1).toUpperCase() + classString.substring(1).toLowerCase();
            availableClassesStringified.add(classString);
        }
        startingWeapons.addAll(StartingWeapon.ALL_STARTING_WEAPON);
        startingArmors.addAll(StartingArmor.ALL_STARTING_ARMOR);
    }

    private void verifyAndAddStartingEquipment(HeroClass heroClass, Equipment... equipment) {
        Set<String> armorToBeAdded = new HashSet<>();
        Set<String> weaponToBeAdded = new HashSet<>();
        for (Equipment eq : equipment) {
            if (eq.getClass().equals(Armor.class) && eq.getClassRestrictions().contains(heroClass)) {
                armorToBeAdded.add(eq.getName());
            } else if (eq.getClass().equals(Weapon.class) && eq.getClassRestrictions().contains(heroClass)) {
                weaponToBeAdded.add(eq.getName());
            } else
                throw (new RuntimeException("You are trying to add an inappropriate equipment to the starting equipment list"));
        }
        if (!armorToBeAdded.isEmpty()) {
            characterClassToAvailableArmor.put(heroClass.toString(), armorToBeAdded);
            return;
        }
        if (!weaponToBeAdded.isEmpty()) {
            characterClassToAvailableWeapon.put(heroClass.toString(), weaponToBeAdded);
        }
    }

    public Map<String, Set<String>> getCharacterClassToAvailableArmor() {
        return characterClassToAvailableArmor;
    }

    public Map<String, Set<String>> getCharacterClassToAvailableWeapon() {
        return characterClassToAvailableWeapon;
    }

    public List<Weapon> getStartingWeapons() {
        return startingWeapons;
    }

    public List<Armor> getStartingArmors() {
        return startingArmors;
    }

    public List<HeroClass> getAvailableClasses() {
        return availableClasses;
    }

    public List<String> getAvailableClassesStringified() {
        return availableClassesStringified;
    }
}
