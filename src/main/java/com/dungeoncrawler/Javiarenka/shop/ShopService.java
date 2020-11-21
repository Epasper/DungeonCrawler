package com.dungeoncrawler.Javiarenka.shop;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.Party;
import com.dungeoncrawler.Javiarenka.equipment.Backpack;
import com.dungeoncrawler.Javiarenka.load.LoadGameService;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    private Party party = new Party();
    private Merchant merchant = new Merchant();
    private Hero currentlyTradingHero;
    private LoadGameService loadGameService = new LoadGameService();

    public ShopService() {
        //todo baza danych z merchantami
        merchant.setName("Bob the Merchant");
        merchant.setMoney(1000);
        merchant.setBackpack(new Backpack());
        merchant.setImageLink("../images/merchant/merchant.png"); //todo replace with image
        party.setHeroes(loadGameService.load());
        party.setMoney(500);
        currentlyTradingHero = party.getHeroes().get(0);
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Hero getCurrentlyTradingHero() {
        return currentlyTradingHero;
    }

    public void setCurrentlyTradingHero(Hero currentlyTradingHero) {
        this.currentlyTradingHero = currentlyTradingHero;
    }
}
