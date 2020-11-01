package com.dungeoncrawler.Javiarenka.mainMenu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainMenuController {

    @GetMapping("/mainMenu")
    public String getMainMenu() {
        return "mainMenu";
    }

    @GetMapping("/newGame")
    public String getNewGame() {
        return "newGame";
    }

    @GetMapping("/aboutProject")
    public String getAboutProject() {
        return "aboutProject";
    }

}
