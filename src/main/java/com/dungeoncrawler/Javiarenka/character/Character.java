package com.dungeoncrawler.Javiarenka.character;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {

    private String name;
    private int hp;
    private boolean isAlive;
    private List<CharacterStatus> currentStatus;


    public Character() {
    }

    public Character(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.isAlive = true;
        this.currentStatus = new ArrayList<>();
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

    String attack(Character character) {
        return null;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<CharacterStatus> getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(List<CharacterStatus> currentStatus) {
        this.currentStatus = currentStatus;
    }

    public boolean checkForStatus(String statusName) { // todo: perhaps it should return CharacterStatus
        List<CharacterStatus> statusList = getCurrentStatus();
        return statusList.stream()
                .anyMatch((CharacterStatus status) -> status.equalsCharacterStatus(statusName));
    }
}