package com.dungeoncrawler.Javiarenka.characterCreator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;


@Controller
public class CharacterCreatorController {

    CharacterCreatorService service = new CharacterCreatorService();

    @GetMapping("/characterCreator")
    public String characterCreatorGet(@RequestParam(value = "className", required = false) String className, Model model) {
        model.addAttribute("heroClasses", service.getAvailableClassesStringified());
        if (className != null && !className.isBlank()) {
            model.addAttribute("startingWeapon", service.getCharacterClassToAvailableWeapon().get(className));
            model.addAttribute("startingArmor", service.getCharacterClassToAvailableArmor().get(className));
        }
        return "characterCreator";
    }

    @PostMapping("/characterCreator")
    public String characterCreatorPost(Model model) {
        return "character";
    }
}
