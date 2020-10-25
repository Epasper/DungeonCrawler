package com.dungeoncrawler.Javiarenka.character;

import java.util.Random;

public class Monster extends Creature {

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

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + super.getName() + '\'' +
                "race='" + race + '\'' +
                ", damageStrength=" + damageStrength +
                '}';
    }
}