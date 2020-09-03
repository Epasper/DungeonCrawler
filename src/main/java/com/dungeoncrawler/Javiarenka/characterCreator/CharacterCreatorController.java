package com.dungeoncrawler.Javiarenka.characterCreator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CharacterCreatorController {

    @GetMapping("/characterCreator")
    public String characterCreatorGet(Model model) {
        return "characterCreator";
    }

    @PostMapping("/characterCreator")
    public String characterCreatorPost(Model model) {
        return "character";
    }
}
