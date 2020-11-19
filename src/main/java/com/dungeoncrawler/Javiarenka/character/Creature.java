package com.dungeoncrawler.Javiarenka.character;

import java.util.ArrayList;
import java.util.List;

public abstract class Creature {

    private String name;
    private int hp;
    private int maxHp;
    private boolean isAlive;
    private List<CharacterStatus> allCharacterStatuses;
    private int maxPhysicalShield;
    private int maxMagicShield;
    private int physicalShield;
    private int magicShield;
    private int speed = 3;
    private int initiative;
    private String imageLink;
    private boolean isActive = false;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxPhysicalShield() {
        return maxPhysicalShield;
    }

    public void setMaxPhysicalShield(int maxPhysicalShield) {
        this.maxPhysicalShield = maxPhysicalShield;
    }

    public int getMaxMagicShield() {
        return maxMagicShield;
    }

    public void setMaxMagicShield(int maxMagicShield) {
        this.maxMagicShield = maxMagicShield;
    }

    public int getPhysicalShield() {
        return physicalShield;
    }

    public void setPhysicalShield(int physicalShield) {
        this.physicalShield = physicalShield;
    }

    public int getMagicShield() {
        return magicShield;
    }

    public void setMagicShield(int magicShield) {
        this.magicShield = magicShield;
    }

    public Creature() {
        this.isAlive = true;
        this.allCharacterStatuses = new ArrayList<>();
    }

    public Creature(int hp) {
        this();
        this.hp = hp;
    }

    public Creature(String name, int hp, String imageLink) {
        this.name = name;
        this.hp = hp;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public abstract String attack(Creature character);

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<CharacterStatus> getAllCharacterStatuses() {
        return allCharacterStatuses;
    }

    public void setAllCharacterStatuses(List<CharacterStatus> allCharacterStatuses) {
        this.allCharacterStatuses = allCharacterStatuses;
    }

    public boolean checkForStatus(String statusName) {
        List<CharacterStatus> statusList = getAllCharacterStatuses();
        return statusList.stream()
                .anyMatch((CharacterStatus status) -> status.equalsCharacterStatus(statusName));
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", maxHp=" + maxHp +
                ", isAlive=" + isAlive +
                ", allCharacterStatuses=" + allCharacterStatuses +
                ", maxPhysicalShield=" + maxPhysicalShield +
                ", maxMagicShield=" + maxMagicShield +
                ", physicalShield=" + physicalShield +
                ", magicShield=" + magicShield +
                ", speed=" + speed +
                ", initiative=" + initiative +
                '}';
    }
}