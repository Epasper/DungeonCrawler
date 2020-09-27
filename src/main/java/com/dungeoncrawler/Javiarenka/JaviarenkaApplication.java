package com.dungeoncrawler.Javiarenka;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JaviarenkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaviarenkaApplication.class, args);
	}
	private void reloadHeroes(){
        PartySelectorService ps = new PartySelectorService();
        for (Hero hero : ps.getAllAvailableHeroes()){
            hero.saveThisHero();
        }
    }

}
