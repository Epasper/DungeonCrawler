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
    String attack(Character hero) {
        Hero attackedHero = (Hero)hero;

        Random random = new Random();
        int hitPossibility = random.nextInt(100);

        if (hitPossibility <= attackedHero.getEquippedArmor().getChanceToHitReduction()) { return; }

        if (attackedHero.getEquippedArmor().getDamageReduction() <= damageStrength) {
            attackedHero.setHp(attackedHero.getHp() + attackedHero.getEquippedArmor().getDamageReduction() - damageStrength);
        }
        return null;
        }
    }