package com.dungeoncrawler.Javiarenka.creature;

import com.dungeoncrawler.Javiarenka.creature.skill.SkillCard;
import com.dungeoncrawler.Javiarenka.partySelector.PartySelectorService;
import com.dungeoncrawler.Javiarenka.staticResources.SkillResources;
import com.dungeoncrawler.Javiarenka.equipment.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class Hero extends Creature {
    private String surname;
    private HeroClass heroClass;
    private Armor equippedArmor;
    private Weapon equippedWeapon;
    private String weaponName;
    private String armorName;
    private String className;
    private int money;
    private List<SkillCard> skillCards;
    private Backpack backpack = new Backpack();
    private final int unarmedAttackDamage = 1;
    private boolean isSelectedForParty;
    //since thymeleaf looping works better when starting from 1, these two values should never be lower than 1:
    private boolean isSelected;
    private int strength;
    private int dexterity;
    private int stamina;
    private int intelligence;
    private int willpower;
    private int resilience;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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


    public List<SkillCard> getSkillCards() {
        return skillCards;
    }

    public void setSkillCards(List<SkillCard> skillCards) {
        this.skillCards = skillCards;
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

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    public int getResilience() {
        return resilience;
    }

    public void setResilience(int resilience) {
        this.resilience = resilience;
    }

    public static void main(String[] args) {
        PartySelectorService partySelectorService = new PartySelectorService();
        List<Hero> temp = partySelectorService.loadAllHeroes();
        for(Hero h : temp) {
            h.setSkillCards(null);
            h.saveThisHero();
        }
    }

    public Hero() {
        super();
        addStartingBackpackItems();
    }

    public Hero(String name, String surname, HeroClass heroClass, Armor equippedArmor, Weapon equippedWeapon, int money, List<SkillCard> skillCards) {
        this();
        super.setName(name);
        this.surname = surname;
        this.heroClass = heroClass;
        this.equippedArmor = equippedArmor;
        this.equippedWeapon = equippedWeapon;
        this.backpack.setRightHandSlot(equippedWeapon);
        this.backpack.setChestSlot(equippedArmor);
        this.money = money;
        this.skillCards = skillCards;
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
                ", skillCards=" + skillCards +
                ", backpack=" + backpack +
                '}';
    }

    public void setDefensesByHeroClass() {
        switch (heroClass) {
            case WARRIOR:
                setMaxHp(100);
                setHp(100);
                setMaxMagicShield(20);
                setMagicShield(20);
                setMaxPhysicalShield(40);
                setPhysicalShield(40);
                break;
            case ROGUE:
                setMaxHp(60);
                setHp(60);
                setMaxMagicShield(30);
                setMagicShield(30);
                setMaxPhysicalShield(30);
                setPhysicalShield(30);
                break;
            case ARCHER:
                setMaxHp(70);
                setHp(70);
                setMaxMagicShield(25);
                setMagicShield(25);
                setMaxPhysicalShield(45);
                setPhysicalShield(45);
                break;
            case KNIGHT:
                setMaxHp(120);
                setHp(120);
                setMaxMagicShield(10);
                setMagicShield(10);
                setMaxPhysicalShield(50);
                setPhysicalShield(50);
                break;
            case HEALER:
                setMaxHp(80);
                setHp(80);
                setMaxMagicShield(40);
                setMagicShield(40);
                setMaxPhysicalShield(20);
                setPhysicalShield(20);
                break;
            case WIZARD:
                setMaxHp(50);
                setHp(50);
                setMaxMagicShield(50);
                setMagicShield(50);
                setMaxPhysicalShield(10);
                setPhysicalShield(10);
                break;
        }
    }

    public void saveThisHero() {
        setEncounterXPosition(1);
        setEncounterYPosition(1);
        setDefensesByHeroClass();
        setSkillsByHeroClass();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        System.out.println(this);
        try {
            Writer writer = new FileWriter("src/main/java/com/dungeoncrawler/Javiarenka/dataBase/heroes/" + getName() + "---" + getSurname() + ".txt");
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String attack(Creature monster) {
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


    public void setSkillsByHeroClass() {
        SkillResources s = new SkillResources();
        List<SkillCard> defaultSkillCards = SkillResources.getAllStartingSkillCards();
        List<SkillCard> skillsFiltered = defaultSkillCards
                .stream()
                .filter(skillCard -> skillCard
                        .getClassRestrictions()
                        .contains(getHeroClass()))
                .collect(Collectors.toList());
        setSkillCards(skillsFiltered);
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