package com.dungeoncrawler.Javiarenka.character;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {

    private String name;
    private int hp;
    private boolean isAlive;
    private List<CharacterStatus> allCharacterStatuses;


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