package com.dungeoncrawler.Javiarenka.save;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SaveGameController {
    PartySelectorService service = new PartySelectorService();
    SaveGameService saveGameService = new SaveGameService();

    @GetMapping("/saveGame")
    public String saveGame(Model model) {
        List<Hero> heroTeam = service.loadSelectedHeroes();
        model.addAttribute("heroTeam", heroTeam);
        return "saveGame";
    }

    @PostMapping("/saveGame")
    public String postSaveGame() {
        System.out.println("You are in post method :)");
        saveGameService.save();
        return "saveGameSuccess";
    }
}