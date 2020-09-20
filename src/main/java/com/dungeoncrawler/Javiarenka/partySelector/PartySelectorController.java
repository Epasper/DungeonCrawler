package com.dungeoncrawler.Javiarenka.partySelector;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class PartySelectorController {
    PartySelectorService service = new PartySelectorService();

    @GetMapping("/partySelector")
    public String fightGet(Model model) {
        model.addAttribute("heroes", service.getAllAvailableHeroes());
//        model.addAttribute("monsters", service.getMonsters());
//        model.addAttribute("consoleText", service.getMessageOutput());
        return "fightBoard";
    }
}
