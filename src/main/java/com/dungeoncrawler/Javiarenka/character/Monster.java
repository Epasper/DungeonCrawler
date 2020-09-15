package com.dungeoncrawler.Javiarenka.character;

import java.util.Random;

public class Monster extends Character {

    private String race;
    private int damageStrength;

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

    @Override
    public String attack(Character hero) {
        String message;
        String killMessage = "";
        Hero attackedHero = (Hero) hero;

        Random random = new Random();
        int hitPossibility = random.nextInt(100);

        if (!attackedHero.isAlive()) {
            message = "Monster " + getName() + " can't attack Hero " + attackedHero.getName() + " because he is dead!";
        } else if (hitPossibility <= attackedHero.getEquippedArmor().getChanceToHitReduction()) {
            message = "Monster " + getName() + " missed attack on Hero " + attackedHero.getName() + "!";
        } else if (attackedHero.getEquippedArmor().getDamageReduction() < damageStrength) {
            attackedHero.setHp(attackedHero.getHp() + attackedHero.getEquippedArmor().getDamageReduction() - damageStrength);
            message = "Monster " + getName() + " attacked Hero " + attackedHero.getName() + " and dealt " +
                    (damageStrength - attackedHero.getEquippedArmor().getDamageReduction()) + " damage. ";
            if (attackedHero.getHp() < 1) {
                attackedHero.setAlive(false);
                attackedHero.setHp(0);
                killMessage += "Hero " + attackedHero.getName() + " has been killed by Monster " + getName() + " !";
            }
        } else {
            message = "Monster " + getName() + " is to weak to attack Hero " + attackedHero.getName() + "!";
        }
        return message + killMessage;
    }
}