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
        return "characterCreator";
    }

    @PostMapping("/characterCreator")
    public String characterCreatorPost(@ModelAttribute Hero hero, Model model) {
        System.out.println("POST TEST:" + hero.toString());
        hero.saveThisHero();
        return "characterCreator";
    }
}
