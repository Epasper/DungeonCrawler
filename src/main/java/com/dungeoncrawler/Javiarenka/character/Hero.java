package com.dungeoncrawler.Javiarenka.character;

import com.dungeoncrawler.Javiarenka.equipment.Armor;
import com.dungeoncrawler.Javiarenka.equipment.Weapon;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Hero extends Character {
    private String surname;
    private HeroClass heroClass;
    private Armor equippedArmor;
    private Weapon equippedWeapon;
    private String weaponName;
    private String armorName;
    private String className;
    private int money;
    private final int unarmedAttackDamage = 1;
    private boolean isSelectedForParty;

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getUnarmedAttackDamage() {
        return unarmedAttackDamage;
    }

    public boolean isSelectedForParty() {
        System.out.println(isSelectedForParty);
        return isSelectedForParty;
    }

    public void setSelectedForParty(boolean selectedForParty) {
        isSelectedForParty = selectedForParty;
    }

    public Hero(String name, int hp) {
        super(name, hp);
    }

    public Hero(String name, String surname, HeroClass heroClass, Armor equippedArmor, Weapon equippedWeapon, int money) {
        super();
        super.setName(name);
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
                ", weaponName='" + weaponName + '\'' +
                ", armorName='" + armorName + '\'' +
                ", money=" + money +
                '}';
    }

    public void setHpByHeroClass() {
        switch (heroClass) {
            case WARRIOR:
                setHp(100);
                break;
            case ROGUE:
                setHp(60);
                break;
            case ARCHER:
                setHp(70);
                break;
            case KNIGHT:
                setHp(120);
            case HEALER:
                setHp(80);
            case WIZARD:
                setHp(50);
        }
    }

    public void saveThisHero() {
        setHpByHeroClass();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/" + getName() + "---" + getSurname() + ".txt");
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String attack(Character monster) {
        String message;
        String hit = "hit ";
        String damageDealt;
        if (equippedWeapon != null) {
            damageDealt = " and dealt " + getEquippedWeapon().getDamageDealt() + " " + getEquippedWeapon().getDamageType() + " damage.";
        } else {
            damageDealt = " and dealt 1 damage.";
        }
        if (equippedWeapon != null) {
            monster.setHp(monster.getHp() - equippedWeapon.getDamageDealt());
        } else {
            monster.setHp(monster.getHp() - unarmedAttackDamage);
        }
        if (monster.getHp() < 1) {
            monster.setAlive(false);
        }
        message = "Hero " + getName() + " attacked a " + monster.getName() + ", " + hit + damageDealt;
        return message;
    }

    public void addMoney(int amount) {
        this.money = this.money + amount;
    }

    public void removeMoney(int amount) throws NoMoreMoneyException {
        if (amount > this.money) {
            throw new NoMoreMoneyException();
        }
        this.money = this.money - amount;
    }
    public int getTotalHp() {
        return getHp() + equippedArmor.getAdditionalHp();
    }
}