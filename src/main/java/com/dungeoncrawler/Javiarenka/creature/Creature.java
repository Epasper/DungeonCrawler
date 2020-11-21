package com.dungeoncrawler.Javiarenka.creature;

import java.util.ArrayList;
import java.util.List;

public abstract class Creature {

    private String name;
    private int hp;
    private int maxHp;
    private boolean isAlive;
    private List<CreatureStatus> allCreatureStatuses;
    private int maxPhysicalShield;
    private int maxMagicShield;
    private int physicalShield;
    private int magicShield;
    private int speed = 3;
    private int initiative;
    private String imageLink;
    private boolean isActive = false;
    private int encounterXPosition;
    private int encounterYPosition;

    public List<CreatureStatus> getAllCreatureStatuses() {
        return allCreatureStatuses;
    }

    public void setAllCreatureStatuses(List<CreatureStatus> allCreatureStatuses) {
        this.allCreatureStatuses = allCreatureStatuses;
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
        this.allCreatureStatuses = new ArrayList<>();
    }

    public Creature(int hp) {
        this();
    }

    public Creature(String name, int hp) {
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

    public abstract String attack(Creature character);

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<CreatureStatus> getAllCharacterStatuses() {
        return allCreatureStatuses;
    }

    public void setAllCharacterStatuses(List<CreatureStatus> allCreatureStatuses) {
        this.allCreatureStatuses = allCreatureStatuses;
    }

    public boolean checkForStatus(String statusName) {
        List<CreatureStatus> statusList = getAllCharacterStatuses();
        return statusList.stream()
                .anyMatch((CreatureStatus status) -> status.equalsCharacterStatus(statusName));
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", maxHp=" + maxHp +
                ", isAlive=" + isAlive +
                ", allCharacterStatuses=" + allCreatureStatuses +
                ", maxPhysicalShield=" + maxPhysicalShield +
                ", maxMagicShield=" + maxMagicShield +
                ", physicalShield=" + physicalShield +
                ", magicShield=" + magicShield +
                ", speed=" + speed +
                ", initiative=" + initiative +
                '}';
    }
}