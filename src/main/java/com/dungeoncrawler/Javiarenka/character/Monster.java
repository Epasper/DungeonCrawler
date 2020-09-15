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
    void attack(Character hero) {
        Hero attackedHero = (Hero)hero;

        if (attackedHero.getEquippedArmor().getDamageReduction() <= damageStrength) {
            attackedHero.setHp(attackedHero.getHp() + attackedHero.getEquippedArmor().getDamageReduction() - damageStrength);
        } else {
            attackedHero.setHp(attackedHero.getHp());
        }

        Random random = new Random();
        int hitPossibility = random.nextInt(100);

        if (hitPossibility >= attackedHero.getEquippedArmor().getChanceToHitReduction()) {
            attack(attackedHero);
        } else {
            attackedHero.setHp(attackedHero.getHp());
        }
        }
    }