package com.dungeoncrawler.Javiarenka.equipment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EquipmentController {

    EquipmentService service = new EquipmentService();

    @GetMapping("/editEquipment")
    public String backpackGet(@RequestParam String heroNameAndSurname, Model model) {
        service.setCurrentlySelectedHero(service.getPartySelector().loadAHeroByNameAndSurname(heroNameAndSurname));
        service.setHeroBackpack(service.getCurrentlySelectedHero().getBackpack());
        model.addAttribute("hero", service.getCurrentlySelectedHero());
        model.addAttribute("backpack", service.getHeroBackpack());
        model.addAttribute("baggageSlotsNumber", Backpack.getMaxBaggageCapacity());
        return "editEquipment";
    }
}
