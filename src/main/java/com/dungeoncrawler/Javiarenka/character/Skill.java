package com.dungeoncrawler.Javiarenka.character;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Skill {
    private String name;
    private int additionalDamage;
    private int additionalHp;
    private boolean isMagicSkill;
    private boolean isPhysicalSkill;
    private SkillTarget target;
    private int coolDown;
    private int manaCost;
    private int staminaCost;
    private List<CharacterStatus> appliesStatuses;
    private int appliedStatusDuration;
    private List<HeroClass> classRestrictions;
    /* @Getter @Setter
    private HashSet<Character> selectedCharacters; TODO */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdditionalDamage() {
        return additionalDamage;
    }

    public void setAdditionalDamage(int additionalDamage) {
        this.additionalDamage = additionalDamage;
    }

    public int getAdditionalHp() {
        return additionalHp;
    }

    public void setAdditionalHp(int additionalHp) {
        this.additionalHp = additionalHp;
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

    public SkillTarget getTarget() {
        return target;
    }

    public void setTarget(SkillTarget target) {
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

    public List<CharacterStatus> getAppliesStatuses() {
        return appliesStatuses;
    }

    public void setAppliesStatuses(List<CharacterStatus> appliesStatuses) {
        this.appliesStatuses = appliesStatuses;
    }

    public int getAppliedStatusDuration() {
        return appliedStatusDuration;
    }

    public void setAppliedStatusDuration(int appliedStatusDuration) {
        this.appliedStatusDuration = appliedStatusDuration;
    }

    public List<HeroClass> getClassRestrictions() {
        return classRestrictions;
    }

    public void setClassRestrictions(List<HeroClass> classRestrictions) {
        this.classRestrictions = classRestrictions;
    }

    public Skill(String name, int additionalDamage, int additionalHp, boolean isMagicSkill, boolean isPhysicalSkill,
                 SkillTarget target, int coolDown, int manaCost, int staminaCost, List<CharacterStatus> appliesStatuses,
                 int appliedStatusDuration) {
        this.name = name;
        this.additionalDamage = additionalDamage;
        this.additionalHp = additionalHp;
        this.isMagicSkill = isMagicSkill;
        this.isPhysicalSkill = isPhysicalSkill;
        this.target = target;
        this.coolDown = coolDown;
        this.manaCost = manaCost;
        this.staminaCost = staminaCost;
        this.appliesStatuses = appliesStatuses;
        this.appliedStatusDuration = appliedStatusDuration;
    }

    public Skill(String name, int additionalDamage, int additionalHp, boolean isMagicSkill, boolean isPhysicalSkill,
                 SkillTarget target, int coolDown, int manaCost, int staminaCost, List<CharacterStatus> appliesStatuses,
                 int appliedStatusDuration, List<HeroClass> classRestrictions) {
        this(name, additionalDamage, additionalHp, isMagicSkill, isPhysicalSkill, target, coolDown, manaCost,
                staminaCost, appliesStatuses, appliedStatusDuration);
        this.classRestrictions = classRestrictions;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                '}';
    }
}
