package com.dungeoncrawler.Javiarenka.characterCreator;

import com.dungeoncrawler.Javiarenka.creature.Attribute;
import com.dungeoncrawler.Javiarenka.creature.HeroClass;
import com.dungeoncrawler.Javiarenka.equipment.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CharacterCreatorService {

    private final List<Weapon> startingWeapons = new ArrayList<>();
    private final List<Armor> startingArmors = new ArrayList<>();
    private final Map<String, Set<String>> characterClassToAvailableArmor = new HashMap<>();
    private final Map<String, Set<String>> characterClassToAvailableWeapon = new HashMap<>();
    private final List<String> availableClassesStrings = Arrays.stream(HeroClass.values()).map(HeroClass::toString).collect(Collectors.toList());
    private final List<String> allAttributesStrings = Arrays.stream(Attribute.values()).map(Attribute::toString).collect(Collectors.toList());
    private final int startingAttributePoints = 20;

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
        startingWeapons.addAll(StartingWeapon.ALL_STARTING_WEAPON);
        startingArmors.addAll(StartingArmor.ALL_STARTING_ARMOR);
    }

    public int getStartingAttributePoints() {
        return startingAttributePoints;
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

    public List<String> getAvailableClassesStrings() {
        return availableClassesStrings;
    }

    public List<String> getAllAttributesStrings() {
        return allAttributesStrings;
    }
}
