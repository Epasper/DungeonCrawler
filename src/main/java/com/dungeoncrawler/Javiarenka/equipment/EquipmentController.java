package com.dungeoncrawler.Javiarenka.equipment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EquipmentController {

    EquipmentService service = new EquipmentService();

    @GetMapping("/characterCreator")
    public String backpackGet(Model model) {
        model.addAttribute("hero", service.getCurrentlySelectedHero());
        return "backpack";
    }
}
