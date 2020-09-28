package com.dungeoncrawler.Javiarenka.character;

import com.dungeoncrawler.Javiarenka.equipment.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Hero extends Character {
    @Getter @Setter
    private String surname;
    @Getter @Setter
    private HeroClass heroClass;
    @Getter @Setter
    private Armor equippedArmor;
    @Getter @Setter
    private Weapon equippedWeapon;
    @Getter @Setter
    private String weaponName;
    @Getter @Setter
    private String armorName;
    @Getter @Setter
    private String className;
    @Getter @Setter
    private int money;
    @Getter @Setter
    private int unarmedAttackDamage = 1;
    private Backpack backpack = new Backpack();

    public Hero(String name, int hp) {
        super(name, hp);
    }

    public Hero(String name, String surname, HeroClass heroClass, Armor equippedArmor, Weapon equippedWeapon, int money) {
        this();
        super.setName(name);
        this.surname = surname;
        this.heroClass = heroClass;
        this.equippedArmor = equippedArmor;
        this.equippedWeapon = equippedWeapon;
        this.money = money;
    }

    public Hero() {
        super();
        backpack.add(new MundaneItem("Chipped Claw",0.02,2,3));
        backpack.add(new MundaneItem("Pretty Stone",0.06,1,6));
        backpack.add(new MundaneItem("Broken Branch",0.05,2, 7));
        backpack.add(new MundaneItem("Coal Ore Chunk",0.2,12,3));
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