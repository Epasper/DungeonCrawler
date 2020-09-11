package com.dungeoncrawler.Javiarenka.character;

import com.dungeoncrawler.Javiarenka.equipment.Armor;
import com.dungeoncrawler.Javiarenka.equipment.Weapon;

public class Hero extends Character {
    private String surname;
    private HeroClass heroClass;
    private Armor equippedArmor;
    private Weapon equippedWeapon;
    private int money;

    public Hero(String name, int hp) {
        super(name, hp);
    }

    public Hero(String name, int hp, String surname, HeroClass heroClass, Armor equippedArmor, Weapon equippedWeapon, int money) {
        super(name, hp);
        this.surname = surname;
        this.heroClass = heroClass;
        this.equippedArmor = equippedArmor;
        this.equippedWeapon = equippedWeapon;
        this.money = money;
    }

    public Hero() {
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + super.getName() + '\'' +
                "surname='" + surname + '\'' +
                ", heroClass=" + heroClass +
                ", equippedArmor=" + equippedArmor +
                ", equippedWeapon=" + equippedWeapon +
                '}';
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public void setEquippedArmor(Armor equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public int getMoney() { return money; }

    public void setMoney(int money) { this.money = money; }

    public void saveThisHero() {
        //TODO: DUN-26: Zapisać postać w lokalnym pliku
    }

    @Override
    void attack(Character monster) {
        monster.setHp(monster.getHp() - equippedWeapon.getDamageDealt());
    }
}