package com.dungeoncrawler.Javiarenka.character;

import com.dungeoncrawler.Javiarenka.staticResources.SkillResources;
import com.dungeoncrawler.Javiarenka.equipment.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class Hero extends Character {
    private String surname;
    private HeroClass heroClass;
    private Armor equippedArmor;
    private Weapon equippedWeapon;
    private String weaponName;
    private String armorName;
    private String className;
    private int speed = 3;
    private int money;
    private List<Skill> skills;
    private Backpack backpack = new Backpack();
    private final int unarmedAttackDamage = 1;
    private boolean isSelectedForParty;
    //since thymeleaf looping works better when starting from 1, these two values should never be lower than 1:
    private int encounterXPosition;
    private int encounterYPosition;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEncounterXPosition() {
        return encounterXPosition;
    }

    public void setEncounterXPosition(int encounterXPosition) {
        this.encounterXPosition = encounterXPosition;
    }

    public int getEncounterYPosition() {
        return encounterYPosition;
    }

    public void setEncounterYPosition(int encounterYPosition) {
        this.encounterYPosition = encounterYPosition;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public int getUnarmedAttackDamage() {
        return unarmedAttackDamage;
    }

    public boolean isSelectedForParty() {
        return this.isSelectedForParty;
    }

    public void setSelectedForParty(boolean selectedForParty) {
        isSelectedForParty = selectedForParty;
    }

    public Hero() {
        super();
        addStartingBackpackItems();
    }

    public Hero(String name, int hp) {
        super(name, hp);
        addStartingBackpackItems();
    }

    public Hero(String name, String surname, HeroClass heroClass, Armor equippedArmor, Weapon equippedWeapon, int money, List<Skill> skills) {
        this();
        super.setName(name);
        this.surname = surname;
        this.heroClass = heroClass;
        this.equippedArmor = equippedArmor;
        this.equippedWeapon = equippedWeapon;
        this.backpack.setRightHandSlot(equippedWeapon);
        this.backpack.setChestSlot(equippedArmor);
        this.money = money;
        this.skills = skills;
    }

    public void addStartingBackpackItems() {
        try {
            backpack.putEquipmentToFirstAvailableSlot(new MundaneItem("Chipped Claw", 0.02, 2, 3));
            backpack.putEquipmentToFirstAvailableSlot(new MundaneItem("Pretty Stone", 0.06, 1, 6));
            backpack.putEquipmentToFirstAvailableSlot(new MundaneItem("Broken Branch", 0.05, 2, 7));
            backpack.putEquipmentToFirstAvailableSlot(new MundaneItem("Coal Ore Chunk", 0.2, 12, 3));
        } catch (InventorySlotsFullException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Hero{" +
                "surname='" + surname + '\'' +
                ", heroClass=" + heroClass +
                ", equippedArmor=" + equippedArmor +
                ", equippedWeapon=" + equippedWeapon +
                ", weaponName='" + weaponName + '\'' +
                ", armorName='" + armorName + '\'' +
                ", money=" + money +
                ", skills=" + skills +
                ", backpack=" + backpack +
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

    public int getTotalHp() {
        return getHp() + equippedArmor.getAdditionalHp();
    }

    public void saveThisHero() {
        setHpByHeroClass();
        setSkillsByHeroClass();
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
            monster.setHp(monster.getHp() - equippedWeapon.getDamageDealt());
        } else {
            damageDealt = " and dealt 1 damage.";
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

    public void setSkillsByHeroClass() {
        List<Skill> defaultSkills = SkillResources.defaultSkills();
        List<Skill> skillsFiltered = defaultSkills.stream()
                .filter(skill -> skill.getClassRestrictions().contains(getHeroClass()))
                .collect(Collectors.toList());
        setSkills(skillsFiltered);
    }

    public void addMundaneItemToBackpack(MundaneItem mundaneItem) {
        try {
            backpack.putEquipmentToFirstAvailableSlot(new MundaneItem(mundaneItem.getName(), mundaneItem.getWeight(),
                    mundaneItem.getPrice(), mundaneItem.getMaxStackSize(), mundaneItem.isQuestItem(), mundaneItem.getSelectPossibility(),
                    mundaneItem.getNumberOfItems(), mundaneItem.getItemType()));
        } catch (InventorySlotsFullException e) {
            e.printStackTrace();
        }
    }
}