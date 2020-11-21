package com.dungeoncrawler.Javiarenka.characterCreator;

import com.dungeoncrawler.Javiarenka.creature.Hero;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CharacterCreatorController {

    CharacterCreatorService service = new CharacterCreatorService();

    @GetMapping("/characterCreator")
    public String characterCreatorGet(Model model) {
        model.addAttribute("heroClasses", service.getAvailableClassesStrings());
        model.addAttribute("hero", new Hero());
        model.addAttribute("allStartingArmors", service.getStartingArmors());
        model.addAttribute("allStartingWeapons", service.getStartingWeapons());
        model.addAttribute("allAttributes",service.getAllAttributesStrings());
        model.addAttribute("startingAttributePoints",service.getStartingAttributePoints());
        return "characterCreator";
    }

}
