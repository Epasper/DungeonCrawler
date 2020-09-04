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
    public String characterCreatorGet(String className, Model model) {
        model.addAttribute("heroClasses", service.getAvailableClassesStringified());
        return "characterCreator";
    }

    @PostMapping("/characterCreator")
    public String characterCreatorPost(Model model) {
        return "character";
    }
}
