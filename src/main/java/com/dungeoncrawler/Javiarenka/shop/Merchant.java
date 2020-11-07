package com.dungeoncrawler.Javiarenka.shop;

import com.dungeoncrawler.Javiarenka.equipment.Backpack;

public class Merchant {
    private String name;
    private int money;
    private Backpack backpack = new Backpack();
    private String imageLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Merchant(String name, int money, Backpack backpack, String imageLink) {
        this.name = name;
        this.money = money;
        this.backpack = backpack;
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", backpack=" + backpack +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }
}

