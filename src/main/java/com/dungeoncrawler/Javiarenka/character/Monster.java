package com.dungeoncrawler.Javiarenka.character;

    class Monster extends Character {

    private String race;
    private int damageStrength;

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