package com.dungeoncrawler.Javiarenka.load;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class LoadGameRestController {
    LoadGameService loadGameService = new LoadGameService();

    @GetMapping("/getAllSaves")
    public RedirectView getAllSaves(@RequestParam String numberToLoad) {
        loadGameService.load(numberToLoad);
        loadGameService.load();
        return new RedirectView("encounterBoard");
    }

}
