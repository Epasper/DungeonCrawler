package com.dungeoncrawler.Javiarenka.character;

import com.dungeoncrawler.Javiarenka.equipment.Armor;
import com.dungeoncrawler.Javiarenka.equipment.Weapon;

public class Hero extends Character {
    private String surname;
    private HeroClass heroClass;
    private Armor equippedArmor;
    private Weapon equippedWeapon;
    private String weaponName;
    private String armorName;
    private String className;
    private int money;
    private int unarmedAttackDamage = 1;

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + super.getName() + '\'' +
                "surname='" + surname + '\'' +
                ", heroClass=" + heroClass +
                ", equippedArmor=" + equippedArmor +
                ", equippedWeapon=" + equippedWeapon +
                ", weaponName='" + weaponName + '\'' +
                ", armorName='" + armorName + '\'' +
                ", money=" + money +
                '}';
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getUnarmedAttackDamage() {
        return unarmedAttackDamage;
    }

    public void setUnarmedAttackDamage(int unarmedAttackDamage) {
        this.unarmedAttackDamage = unarmedAttackDamage;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void saveThisHero() {
        //TODO: DUN-26: Zapisać postać w lokalnym pliku
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getArmorName() {
        return armorName;
    }

    public void setArmorName(String armorName) {
        this.armorName = armorName;
    }

    @Override
    public void attack(Character monster) {
        if (equippedWeapon != null) {
            monster.setHp(monster.getHp() - equippedWeapon.getDamageDealt());
        } else {
            monster.setHp(monster.getHp() - unarmedAttackDamage);
        }
        if (monster.getHp() < 1) {
            monster.setAlive(false);
        }
    }

    public void addMoney(int amount) {
        this.money = this.money + amount;
    }

    public void removeMoney (int amount) throws NoMoreMoneyException {
        if (amount > this.money){
            throw new NoMoreMoneyException();
        }
        this.money = this.money - amount;
    }
}