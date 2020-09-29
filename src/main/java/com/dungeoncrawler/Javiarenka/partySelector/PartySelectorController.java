package com.dungeoncrawler.Javiarenka.partySelector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class PartySelectorController {
    PartySelectorService service = new PartySelectorService();

    @GetMapping("/partySelector")
    public String partySelect(Model model) {
        service.setAllAvailableHeroes(service.loadAllHeroes());
        model.addAttribute("heroes", service.getAllAvailableHeroes());
        return "partySelector";
    }

    @PostMapping("/partySelector")
    public RedirectView postPartySelect(@RequestBody List<String> listOfChosenNames, Model model) {
        System.out.println("postMethodCalledSuccessfully: " + listOfChosenNames.toString());
        service.saveTheParty(listOfChosenNames);
        service.setAllAvailableHeroes(service.loadAllHeroes());
        model.addAttribute("heroes", service.getAllAvailableHeroes());
        //todo: return to main menu instead
        return new RedirectView("/fightBoard");
    }
}
