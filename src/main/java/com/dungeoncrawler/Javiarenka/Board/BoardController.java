package com.dungeoncrawler.Javiarenka.Board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    BoardService service = new BoardService();

    @GetMapping("/fightBoard")
    public String fightGet(Model model) {
        model.addAttribute("heroes", service.getHeroes());
        model.addAttribute("monsters", service.getMonsters());
        return "fightBoard";
    }
}
