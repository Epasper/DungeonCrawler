package com.dungeoncrawler.Javiarenka.board;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.Monster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BoardController {
    BoardService service = new BoardService();

    //todo: this functionality will be deprecated and replaced in future release
    @GetMapping("/fightBoard")
    public String fightGet(Model model) {
        service.clearSelectedHeroes();
        service.setHeroes(service.getPartySelectorService().loadSelectedHeroes());
        model.addAttribute("heroes", service.getHeroes());
        model.addAttribute("monsters", service.getMonsters());
        model.addAttribute("consoleText", service.getMessageOutput());
        return "fightBoard";
    }

    @GetMapping("/encounterBoard")
    public String encounter(Model model) {
        service.prepareTheBoard();
        model.addAttribute("heroes", service.getHeroes());
        model.addAttribute("monsters", service.getMonsters());
        model.addAttribute("consoleText", service.getMessageOutput());
        model.addAttribute("boardWidth", service.getBoardWidth());
        model.addAttribute("boardHeight", service.getBoardHeight());
        model.addAttribute("tiles", service.getTiles());
        model.addAttribute("tileImages", service.getImageSources());
        model.addAttribute("initiativeOrder", service.getInitiativeOrder());
        return "encounterBoard";
    }

    @GetMapping("/fightBoard/attackHero")
    public RedirectView attackHero(Hero heroFromForm, Monster monsterFromForm) {
        if (service.getSelectedHero() != null && service.getSelectedMonster() != null) {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add(service.getSelectedMonster().attack(service.getSelectedHero()));
            service.setSelectedHero(null);
            service.setSelectedMonster(null);
        } else {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add("Hero or Monster not selected!");
        }
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/attackMonster")
    public RedirectView attackMonster(Hero heroFromForm, Monster monsterFromForm) {
        if (service.getSelectedHero() != null && service.getSelectedMonster() != null) {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add(service.getSelectedHero().attack(service.getSelectedMonster()));
            service.setSelectedHero(null);
            service.setSelectedMonster(null);
        } else {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add("Hero or Monster not selected!");
        }
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/selectHero")
    public RedirectView selectHero(Model model, @RequestParam(value = "heroName") String heroName) {
        service.setSelectedHero(service.getCurrentHeroByName(heroName));
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/selectMonster")
    public RedirectView selectMonster(Model model, @RequestParam(value = "monsterName") String monsterName) {
        service.setSelectedMonster(service.getCurrentMonsterByName(monsterName));
        return new RedirectView("");
    }
}
