package com.dungeoncrawler.Javiarenka.partySelector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PartySelectorController {
    PartySelectorService service = new PartySelectorService();

    @GetMapping("/partySelector")
    public String partySelect(Model model) {
        model.addAttribute("heroes", service.getAllAvailableHeroes());
        return "partySelector";
    }

    @PostMapping("/partySelector")
    public String postPartySelect(Model model) {
        System.out.println("partySelectorService: "+service.getAllAvailableHeroes());
        model.addAttribute("heroes", service.getAllAvailableHeroes());
        return "partySelector";
    }

    //todo: save Party button that would save the chosen party to hard drive
}
