package com.dungeoncrawler.Javiarenka.equipment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EquipmentController {

    EquipmentService service = new EquipmentService();

    @GetMapping("/editEquipment")
    public String backpackGet(@RequestParam String heroNameAndSurname, Model model) {
        service.setCurrentlySelectedHero(service.getPartySelector().loadAHeroByNameAndSurname(heroNameAndSurname));
        service.setHeroBackpack(service.getCurrentlySelectedHero().getBackpack());
        System.out.println(service.getHeroBackpack().getRightHandSlot());
        model.addAttribute("hero", service.getCurrentlySelectedHero());
        model.addAttribute("backpack", service.getHeroBackpack());
        model.addAttribute("baggageSlotsNumber", Backpack.getMaxBaggageCapacity());
        return "editEquipment";
    }

    @PostMapping("/editEquipment")
    public String backpackPost(@RequestBody Backpack backpack) {
        if (backpack.getArmsSlot() != null && backpack.getArmsSlot().getName().equals("NO EQUIPMENT"))
            backpack.setArmsSlot(null);
        if (backpack.getChestSlot() != null && backpack.getChestSlot().getName().equals("NO EQUIPMENT"))
            backpack.setChestSlot(null);
        if (backpack.getRightHandSlot() != null && backpack.getRightHandSlot().getName().equals("NO EQUIPMENT"))
            backpack.setRightHandSlot(null);
        if (backpack.getLeftHandSlot() != null && backpack.getLeftHandSlot().getName().equals("NO EQUIPMENT"))
            backpack.setLeftHandSlot(null);
        if (backpack.getFeetSlot() != null && backpack.getFeetSlot().getName().equals("NO EQUIPMENT"))
            backpack.setFeetSlot(null);
        if (backpack.getHeadSlot() != null && backpack.getHeadSlot().getName().equals("NO EQUIPMENT"))
            backpack.setHeadSlot(null);
        service.setHeroBackpack(backpack);
        service.getCurrentlySelectedHero().setBackpack(backpack);
        service.getCurrentlySelectedHero().saveThisHero();
        System.out.println(backpack);
        return "fightBoard";
    }
}
