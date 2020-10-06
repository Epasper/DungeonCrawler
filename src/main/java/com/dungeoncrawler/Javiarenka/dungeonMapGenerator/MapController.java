package com.dungeoncrawler.Javiarenka.dungeonMapGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.stream.IntStream;

//TODO: bug w wyświetlaniu mapy dla wysokości mniejszej niż 30

@Controller
//@RequestMapping("generateMap") //wtedy wszystkie metody poniżej będą zaczynać się od /generateMap/...
public class MapController
{
    //TODO: Thymeleaf generuje nazwy klas hmtl-owych CAPSem, bo bierze je z enuma. Wg sztuki - nazwy klas w htmlu powinny być pisane małymi literami

    MapGeneratorService service = new MapGeneratorService();

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/dungeonMap")
    public String generateMap(@RequestParam int width, @RequestParam int height, Model model) throws IOException
    {


        Stage stage = service.generateMap(width, height);
        model.addAttribute("tiles", stage.getTilesAsOneDimensionalArray());
        int[] colNumbers = IntStream.range(0, stage.getWidth()).toArray();
        int[] rowNumbers = IntStream.range(0, stage.getHeight()).toArray();
        model.addAttribute("colNumbers", colNumbers);
        model.addAttribute("rowNumbers", rowNumbers);

        return "dungeonMapThyme";
    }

    @GetMapping("/createMap")
    public String createMap()
    {
        return "mapForm";   //zwraca nazwę html'a, który ma sie wyswietlic na stronie (html musi byc stworzony w templatach w templatach). Wtedy już nie potrzeba @ResponseBody
    }
}
