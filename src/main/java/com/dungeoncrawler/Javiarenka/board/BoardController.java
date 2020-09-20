package com.dungeoncrawler.Javiarenka.board;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BoardController {
    BoardService service = new BoardService();

    @GetMapping("/fightBoard")
    public String fightGet(Model model) {
        model.addAttribute("heroes", service.getHeroes());
        model.addAttribute("monsters", service.getMonsters());
        model.addAttribute("consoleText", service.getMessageOutput());
        return "fightBoard";
    }

    @GetMapping("/fightBoard/attackHero")
    public RedirectView attackHero(Hero heroFromForm, Monster monsterFromForm) {
        if (service.getSelectedHero() != null && service.getSelectedMonster() != null) {
            service.setMessageOutput(service.getMessageOutput() + "\r\n" + service.getSelectedMonster().attack(service.getSelectedHero()));
        } else {
            service.setMessageOutput(service.getMessageOutput() + "\r\n" + "Hero or Monster not selected!");
        }
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/attackMonster")
    public RedirectView attackMonster(Hero heroFromForm, Monster monsterFromForm) {
        if (service.getSelectedHero() != null && service.getSelectedMonster() != null) {
            service.setMessageOutput(service.getMessageOutput() + "\r\n" + service.getSelectedHero().attack(service.getSelectedMonster()));
        } else {
            service.setMessageOutput(service.getMessageOutput() + "\r\n" + "Hero or Monster not selected!");
        }
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/selectHero")
    public RedirectView selectHero(Model model, @RequestParam(value = "heroName") String heroName) {
        service.setSelectedHero(service.getCurrentHeroByName(heroName));
        return new RedirectView("");
        // todo unselect hero after attack
    }

    @GetMapping("/fightBoard/selectMonster")
    public RedirectView selectMonster(Model model, @RequestParam(value = "monsterName") String monsterName) {
        service.setSelectedMonster(service.getCurrentMonsterByName(monsterName));
        return new RedirectView("");
        // todo unselect monster after attack
    }
}
