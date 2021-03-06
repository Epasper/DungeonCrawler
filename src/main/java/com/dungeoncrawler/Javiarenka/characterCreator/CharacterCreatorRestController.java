package com.dungeoncrawler.Javiarenka.characterCreator;

import com.dungeoncrawler.Javiarenka.creature.Hero;
import com.dungeoncrawler.Javiarenka.creature.HeroClass;
import com.dungeoncrawler.Javiarenka.equipment.Armor;
import com.dungeoncrawler.Javiarenka.equipment.StartingArmor;
import com.dungeoncrawler.Javiarenka.equipment.StartingWeapon;
import com.dungeoncrawler.Javiarenka.equipment.Weapon;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class CharacterCreatorRestController {
    CharacterCreatorService service = new CharacterCreatorService();

    @GetMapping("/charClassToArmor/{className}")
    public Set<String> getAvailableArmors(@PathVariable String className) {
        return service.getCharacterClassToAvailableArmor().get(className);
    }

    @GetMapping("/charClassToArmor")
    public List<Armor> getAllStartingArmors() {
        return service.getStartingArmors();
    }

    @GetMapping("/charClassToWeapon/{className}")
    public Set<String> getAvailableWeapons(@PathVariable String className) {
        return service.getCharacterClassToAvailableWeapon().get(className);
    }

    @GetMapping("/charClassToWeapon")
    public List<Weapon> getAllStartingWeapons() {
        return service.getStartingWeapons();
    }

    @PostMapping("/saveCharacter")
    public Hero saveCharacter(@RequestBody Hero hero) {
        Armor selectedArmor = StartingArmor.getArmorByName(hero.getArmorName());
        Weapon selectedWeapon = StartingWeapon.getWeaponByName(hero.getWeaponName());
        hero.setHeroClass(HeroClass.getHeroClassByName(hero.getClassName()));
        hero.getBackpack().setChestSlot(selectedArmor);
        hero.setEquippedWeapon(selectedWeapon);
        hero.getBackpack().setRightHandSlot(selectedWeapon);
        hero.setEquippedArmor(selectedArmor);
        hero.saveThisHero();
        return null;
    }
}
