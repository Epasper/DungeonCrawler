package com.dungeoncrawler.Javiarenka.character;

import java.util.HashSet;

public class Skill {
    private int additionalDamage;
    private boolean isMagicSkill;
    private boolean isPhysicalSkill;
    private HashSet<Character> target;
    private int coolDown;
    private int manaCost;
    private int staminaCost;
    private boolean appliesStatus;
    private int appliedStatusDuration;

    public Skill(int additionalDamage, boolean isMagicSkill, boolean isPhysicalSkill, HashSet<Character> target, int coolDown,
                 int manaCost, int staminaCost, boolean appliesStatus, int appliedStatusDuration) {
        this.additionalDamage = additionalDamage;
        this.isMagicSkill = isMagicSkill;
        this.isPhysicalSkill = isPhysicalSkill;
        this.target = target;
        this.coolDown = coolDown;
        this.manaCost = manaCost;
        this.staminaCost = staminaCost;
        this.appliesStatus = appliesStatus;
        this.appliedStatusDuration = appliedStatusDuration;
    }


    public int getAdditionalDamage() {
        return additionalDamage;
    }

    public void setAdditionalDamage(int additionalDamage) {
        this.additionalDamage = additionalDamage;
    }

    public boolean isMagicSkill() {
        return isMagicSkill;
    }

    public void setMagicSkill(boolean magicSkill) {
        isMagicSkill = magicSkill;
    }

    public boolean isPhysicalSkill() {
        return isPhysicalSkill;
    }

    public void setPhysicalSkill(boolean physicalSkill) {
        isPhysicalSkill = physicalSkill;
    }

    public HashSet<Character> getTarget() {
        return target;
    }

    public void setTarget(HashSet<Character> target) {
        this.target = target;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getStaminaCost() {
        return staminaCost;
    }

    public void setStaminaCost(int staminaCost) {
        this.staminaCost = staminaCost;
    }

    public boolean isAppliesStatus() {
        return appliesStatus;
    }

    public void setAppliesStatus(boolean appliesStatus) {
        this.appliesStatus = appliesStatus;
    }

    public int getAppliedStatusDuration() {
        return appliedStatusDuration;
    }

    public void setAppliedStatusDuration(int appliedStatusDuration) {
        this.appliedStatusDuration = appliedStatusDuration;
    }
}
