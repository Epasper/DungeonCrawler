package com.dungeoncrawler.Javiarenka.Board;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.Monster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BoardController {
    BoardService service = new BoardService();

    @GetMapping("/fightBoard")
    public String fightGet(Model model) {
        model.addAttribute("heroes", service.getHeroes());
        model.addAttribute("monsters", service.getMonsters());
        return "fightBoard";
    }

    @PostMapping("/fightBoard/attack")
    public String attackMonster(Hero heroFromForm, Monster monsterFromForm) {
        service.getSelectedHero().attack(monsterFromForm);
        return "attackSuccess";
    }

    @GetMapping("/fightBoard/selectHero")
    public RedirectView selectHero(Model model, @RequestParam(value = "heroName") String heroName) {
        service.setSelectedHero(service.getCurrentHeroByName(heroName));
        System.out.println("Selected Hero: " + service.getSelectedHero().getName());
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/selectMonster")
    public RedirectView selectMonster(Model model, @RequestParam(value = "monsterName") String monsterName) {
        service.setSelectedMonster(service.getCurrentMonsterByName(monsterName));
        System.out.println("Selected Monster: " + service.getSelectedMonster().getName());
        return new RedirectView("");
    }
}
