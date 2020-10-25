package com.dungeoncrawler.Javiarenka.character;

import com.dungeoncrawler.Javiarenka.equipment.MundaneItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Monster extends Creature {

    private String race;
    private int damageStrength;
    private List<MundaneItem> afterDeathItems;

    public Monster() {
    }

    public Monster(String name, int hp, String race, int damageStrength) {
        super(name, hp);
        this.race = race;
        this.damageStrength = damageStrength;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
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
        System.out.println(randomNumber);

        if(getHp() < 1) {
            for(MundaneItem item : afterDeathItems) {
                if(randomNumber <= item.getSelectPossibility()) {
                    availableItems.add(item);
                }
            }
            return availableItems.get(random.nextInt(availableItems.size()));
        }
            return null;
    }

    public void afterDeath(Hero hero) {
        hero.addMundaneItemToBackpack(Objects.requireNonNull(afterDeathItem()));
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + super.getName() + '\'' +
                "race='" + race + '\'' +
                ", damageStrength=" + damageStrength +
                '}';
    }
}