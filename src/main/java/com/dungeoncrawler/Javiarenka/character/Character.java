package com.dungeoncrawler.Javiarenka.character;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {

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

    public Character() {
        this.isAlive = true;
        this.allCharacterStatuses = new ArrayList<>();
    }

    public Character(String name, int hp) {
        this();
        this.name = name;
        this.hp = hp;
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

    public abstract String attack(Character character);

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
}