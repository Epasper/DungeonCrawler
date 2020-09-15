package com.dungeoncrawler.Javiarenka.character;

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
        hero.setHp(hero.getHp() - damageStrength);
        }
    }