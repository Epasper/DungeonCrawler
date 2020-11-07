package com.dungeoncrawler.Javiarenka.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {
    ShopService service = new ShopService();

    @GetMapping("/shop")
    public String getShop(Model model) {
        System.out.println("lubie placki");
        model.addAttribute("merchant", service.getMerchant());
        model.addAttribute("hero", service.getCurrentlyTradingHero());
        model.addAttribute("party", service.getParty());
        return "shop";
    }
}
