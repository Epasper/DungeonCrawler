package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.IntStream;

@Controller
//@RequestMapping("generateMap") //wtedy wszystkie metody poniżej będą zaczynać się od /generateMap/...
public class MapController
{

    //TODO: w prawym dolnym rogu przycisk "Menu" z wysuwanym menu -> savegame / loadgame / etc.
    // spawn party przesunąć w miejsce Move Party i niech znika po zespawnowaniu

    //TODO: zamiast tworzyć Divy selekcji i Party Javascriptem - niech będą od początku w htmlu, a JS niech zarządza tylko ich wyświetlaniem

    @Autowired
    MapGeneratorService service;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/dungeonMap")
    public String generateMap(@RequestParam(required = false) Integer width, @RequestParam(required = false) Integer height, Model model) throws IOException
    {

        if(!Objects.isNull(width) || !Objects.isNull(height)) service.generateMap(width.intValue(), height.intValue());

//        service.generateMap(width, height);

        Stage stage = service.getStage();

        model.addAttribute("tiles", stage.getTilesAsOneDimensionalArray());
        int[] colNumbers = IntStream.range(0, stage.getWidth()).toArray();
        int[] rowNumbers = IntStream.range(0, stage.getHeight()).toArray();
        model.addAttribute("colNumbers", colNumbers);
        model.addAttribute("rowNumbers", rowNumbers);
        model.addAttribute("width", stage.getWidth());
        model.addAttribute("height", stage.getHeight());

        return "dungeonMapThyme";
    }

    @GetMapping("/createMap")
    public String createMap()
    {
        return "mapForm";   //zwraca nazwę html'a, który ma sie wyswietlic  na stronie (html musi byc stworzony w templatach w templatach). Wtedy już nie potrzeba @ResponseBody
    }

    @GetMapping("/loadMap")
    public RedirectView loadMap(Model model)
    {
        service.load();
//        Stage stage = service.getStage();
//
//        model.addAttribute("tiles", stage.getTilesAsOneDimensionalArray());
//        int[] colNumbers = IntStream.range(0, stage.getWidth()).toArray();
//        int[] rowNumbers = IntStream.range(0, stage.getHeight()).toArray();
//        model.addAttribute("colNumbers", colNumbers);
//        model.addAttribute("rowNumbers", rowNumbers);

        return new RedirectView("/dungeonMap");
    }
}
