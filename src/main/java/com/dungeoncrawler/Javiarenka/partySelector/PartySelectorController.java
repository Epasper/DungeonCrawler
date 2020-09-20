package com.dungeoncrawler.Javiarenka.partySelector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PartySelectorController {
    PartySelectorService service = new PartySelectorService();

    @GetMapping("/partySelector")
    public String fightGet(Model model) {
        System.out.println("partySelectorService: "+service.getAllAvailableHeroes());
        model.addAttribute("heroes", service.getAllAvailableHeroes());
        return "partySelector";
    }
}
