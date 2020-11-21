package com.dungeoncrawler.Javiarenka.creature;

import com.dungeoncrawler.Javiarenka.equipment.MundaneItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import com.dungeoncrawler.Javiarenka.creature.MonsterRace;

public class Monster extends Creature {

    private MonsterRace race;
    private int damageStrength;
    private List<MundaneItem> afterDeathItems;

    public Monster() {
    }

    public Monster(int hp, MonsterRace race, int damageStrength) {
        super(hp);
        this.race = race;
        this.damageStrength = damageStrength;
    }

    public MonsterRace getRace() {
        return race;
    }

    public void setRace(MonsterRace race) {
        this.race = race;
    }

    public int getDamageStrength() {
        return damageStrength;
    }

    public void setDamageStrength(int damageStrength) {
        this.damageStrength = damageStrength;
    }

    public List<MundaneItem> getAfterDeathItems() {
        return afterDeathItems;
    }

    public void setAfterDeathItems(List<MundaneItem> afterDeathItems) {
        this.afterDeathItems = afterDeathItems;
    }

    public void saveThisMonster() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        System.out.println(this);
        try {
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/monsters/" + getRace().getMonsterRaceName() + "---" + getName() + ".txt");
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String attack(Creature hero) {
        String message;
        Hero attackedHero = (Hero) hero;
        int dealtDamage = (damageStrength - attackedHero.getEquippedArmor().getDamageReduction());

        Random random = new Random();
        int hitPossibility = random.nextInt(100);

        if (!attackedHero.isAlive()) {
            return String.format("Monster %s can't attack Hero %s because he is dead!", getName(), attackedHero.getName());
        } else {
            if (hitPossibility <= attackedHero.getEquippedArmor().getChanceToHitReduction()) {
                return String.format("Monster %s missed attack on Hero %s!", getName(), attackedHero.getName());
            } else if (attackedHero.getEquippedArmor().getDamageReduction() < damageStrength) {
                attackedHero.setHp(attackedHero.getHp() - dealtDamage);
                message = String.format("Monster %s attacked Hero %s and dealt %d damage. ", getName(), attackedHero.getName(), dealtDamage);
                if (attackedHero.getHp() < 1) {
                    attackedHero.setAlive(false);
                    attackedHero.setHp(0);
                    message += String.format("Hero %s has been killed by Monster %s!", attackedHero.getName(), getName());
                }
                return message;
            } else {
                return String.format("%s is to weak to attack Hero %s!", getName(), attackedHero.getName());
            }
        }
    }

    public MundaneItem afterDeathItem() {
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        List<MundaneItem> availableItems = new ArrayList<>();

            for(MundaneItem item : afterDeathItems) {
                if(randomNumber <= item.getSelectPossibility()) {
                    availableItems.add(item);
                }
            }
            return availableItems.get(random.nextInt(availableItems.size()));
    }

    public void afterDeath(Hero hero) {
        hero.addMundaneItemToBackpack(Objects.requireNonNull(afterDeathItem()));
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + super.getName() + '\'' +
                "race='" + race.name() + '\'' +
                ", damageStrength=" + damageStrength +
                '}';
    }
}