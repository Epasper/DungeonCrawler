package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

    PartySelectorService partySelector = new PartySelectorService();
    Hero currentlySelectedHero;
    Backpack heroBackpack;

    public Hero getCurrentlySelectedHero() {
        return currentlySelectedHero;
    }

    public void setCurrentlySelectedHero(Hero currentlySelectedHero) {
        this.currentlySelectedHero = currentlySelectedHero;
    }

    public PartySelectorService getPartySelector() {
        return partySelector;
    }

    public void setPartySelector(PartySelectorService partySelector) {
        this.partySelector = partySelector;
    }

    public Backpack getHeroBackpack() {
        return heroBackpack;
    }

    public void setHeroBackpack(Backpack heroBackpack) {
        this.heroBackpack = heroBackpack;
    }
}
