package com.dungeoncrawler.Javiarenka.equipment;

import com.dungeoncrawler.Javiarenka.character.HeroClass;

import java.util.List;

public class Equipment {
    private String name;
    private double weight;
    private double price;
    private List<HeroClass> classRestrictions;
    private String imageLink;

    public Equipment(String name, List<HeroClass> classRestriction, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.classRestrictions = classRestriction;
        this.imageLink = constructImageLink();
    }

    public Equipment(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.imageLink = constructImageLink();
    }

    public Equipment() {
        this.imageLink = constructImageLink();
    }

    private String constructImageLink(){
        return "../images/" + this.name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<HeroClass> getClassRestrictions() {
        return classRestrictions;
    }

    public void setClassRestrictions(List<HeroClass> classRestrictions) {
        this.classRestrictions = classRestrictions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
