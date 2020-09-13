package com.dungeoncrawler.Javiarenka.characterCreator;

import com.dungeoncrawler.Javiarenka.character.Hero;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CharacterCreatorController {

    CharacterCreatorService service = new CharacterCreatorService();

    @GetMapping("/characterCreator")
    public String characterCreatorGet(Model model) {
        model.addAttribute("heroClasses", service.getAvailableClassesStringified());
        model.addAttribute("hero", new Hero());
        model.addAttribute("allStartingArmors", service.getStartingArmors());
        model.addAttribute("allStartingWeapons", service.getStartingWeapons());
        return "characterCreator";
    }

    @PostMapping("/characterCreator")
    public String characterCreatorPost(@ModelAttribute("hero") Hero hero, Model model) {
        model.addAttribute("equippedWeapon", hero.getEquippedWeapon());
        System.out.println("POST TEST:" + hero.toString());
        System.out.println("POST TEST 1:" + model.getAttribute("equippedWeapon"));
//        System.out.println("POST TEST wpn:" + hero.getEquippedWeapon().toString());
        hero.saveThisHero();
        model.addAttribute("heroClasses", service.getAvailableClassesStringified());
        model.addAttribute("hero", new Hero());
        model.addAttribute("allStartingArmors", service.getStartingArmors());
        model.addAttribute("allStartingWeapons", service.getStartingWeapons());
        return "characterCreator";
    }
}
