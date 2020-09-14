package com.dungeoncrawler.Javiarenka.Board;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.Monster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
            BoardService.attackMonster(heroFromForm, monsterFromForm);
            return "attackSuccess";
    }
}
