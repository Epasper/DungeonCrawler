package com.dungeoncrawler.Javiarenka.character;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private List<Hero> heroes = new ArrayList<>();

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public String toString() {
        return "Party{" +
                "heroes=" + heroes +
                '}';
    }
}
