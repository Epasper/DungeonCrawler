package com.dungeoncrawler.Javiarenka.character;

public abstract class Character {

    private String name;
    private int hp;
    private boolean isAlive;
    private CharacterStatus currentStatus;


    public Character() {
    }

    public Character(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.isAlive = true;
        this.currentStatus = CharacterStatus.DEFAULT;
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

    public CharacterStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CharacterStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}