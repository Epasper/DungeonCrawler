package com.dungeoncrawler.Javiarenka.characterCreator;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
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
        return service.getCharacterClassToAvailableArmor().get(className.toUpperCase());
    }

    @GetMapping("/charClassToArmor")
    public List<String> getAllStartingArmors() {
        return service.getStartingArmors();
    }

    @GetMapping("/charClassToWeapon/{className}")
    public Set<String> getAvailableWeapons(@PathVariable String className) {
        return service.getCharacterClassToAvailableWeapon().get(className.toUpperCase());
    }

    @GetMapping("/charClassToWeapon")
    public List<Weapon> getAllStartingWeapons() {
        return service.getStartingWeapons();
    }

    @PostMapping("/saveCharacter")
    public Hero saveCharacter(@RequestBody Hero hero) {
        hero.setHeroClass(HeroClass.getHeroClassByName(hero.getClassName()));
        hero.setEquippedWeapon(StartingWeapon.getWeaponByName(hero.getWeaponName()));
        hero.setEquippedArmor(StartingArmor.getArmorByName(hero.getArmorName()));
        System.out.println(hero.toString());
        return null;
    }
}
