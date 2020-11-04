package com.dungeoncrawler.Javiarenka.load;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoadGameController {
    LoadGameService loadGameService = new LoadGameService();

    @GetMapping("/loadGame")
    public String loadGame(Model model) {
        model.addAttribute("allSaves", loadGameService.getAllSaves());
        for (String s : loadGameService.getAllSaves()){
            System.out.println(s);
        }
        return "loadGame";
    }
}
