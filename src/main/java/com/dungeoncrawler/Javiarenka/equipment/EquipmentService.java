package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.Hero;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

    Hero currentlySelectedHero;

    public Hero getCurrentlySelectedHero() {
        return currentlySelectedHero;
    }

    public void setCurrentlySelectedHero(Hero currentlySelectedHero) {
        this.currentlySelectedHero = currentlySelectedHero;
    }
}
