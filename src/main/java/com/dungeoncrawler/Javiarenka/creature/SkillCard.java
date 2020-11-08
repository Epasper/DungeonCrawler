package com.dungeoncrawler.Javiarenka.creature;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkillCard {
    @NotNull
    private String name = "";
    private int flatAdditionalDamage = 0;
    private int secondaryAttackFlatAdditionalDamage = 0;
    private int healingAmount = 0;
    private List<DamageMultiplier> allDamageMultipliers = new ArrayList<>();
    private boolean isMagicSkill = false;
    private boolean isPhysicalSkill = false;
    private boolean isSecondAttack = false;
    @NotNull
    private SkillTarget primaryTarget = SkillTarget.ENEMY;
    private SkillTarget secondaryTarget = SkillTarget.ENEMY;
    private SkillTarget secondAttackTarget = SkillTarget.ENEMY;
    private List<Condition> appliedConditions;
    private List<HeroClass> classRestrictions;
    private String imageSource = "";
    /* @Getter @Setter
    private HashSet<Character> selectedCharacters; TODO */

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int getSecondaryAttackFlatAdditionalDamage() {
        return secondaryAttackFlatAdditionalDamage;
    }

    public void setSecondaryAttackFlatAdditionalDamage(int secondaryAttackFlatAdditionalDamage) {
        this.secondaryAttackFlatAdditionalDamage = secondaryAttackFlatAdditionalDamage;
    }

    public boolean isSecondAttack() {
        return isSecondAttack;
    }

    public void setSecondAttack(boolean secondAttack) {
        isSecondAttack = secondAttack;
    }

    public SkillTarget getSecondAttackTarget() {
        return secondAttackTarget;
    }

    public void setSecondAttackTarget(SkillTarget secondAttackTarget) {
        this.secondAttackTarget = secondAttackTarget;
    }

    public SkillTarget getSecondaryTarget() {
        return secondaryTarget;
    }

    public void setSecondaryTarget(SkillTarget secondaryTarget) {
        this.secondaryTarget = secondaryTarget;
    }

    public List<DamageMultiplier> getAllDamageMultipliers() {
        return allDamageMultipliers;
    }

    public void setAllDamageMultipliers(List<DamageMultiplier> allDamageMultipliers) {
        this.allDamageMultipliers = allDamageMultipliers;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getFlatAdditionalDamage() {
        return flatAdditionalDamage;
    }

    public void setFlatAdditionalDamage(int flatAdditionalDamage) {
        this.flatAdditionalDamage = flatAdditionalDamage;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
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

    public @NotNull SkillTarget getPrimaryTarget() {
        return primaryTarget;
    }

    public void setPrimaryTarget(@NotNull SkillTarget primaryTarget) {
        this.primaryTarget = primaryTarget;
    }

    public List<Condition> getAppliedConditions() {
        return appliedConditions;
    }

    public void setAppliedConditions(List<Condition> appliedConditions) {
        this.appliedConditions = appliedConditions;
    }

    public List<HeroClass> getClassRestrictions() {
        return classRestrictions;
    }

    public void setClassRestrictions(List<HeroClass> classRestrictions) {
        this.classRestrictions = classRestrictions;
    }

    public SkillCard() {
    }

    public SkillCard(@NotNull String name, int flatAdditionalDamage, int healingAmount, boolean isMagicSkill, boolean isPhysicalSkill, @NotNull SkillTarget primaryTarget, List<HeroClass> classRestrictions) {
        this.name = name;
        this.flatAdditionalDamage = flatAdditionalDamage;
        this.healingAmount = healingAmount;
        this.isMagicSkill = isMagicSkill;
        this.isPhysicalSkill = isPhysicalSkill;
        this.primaryTarget = primaryTarget;
        this.classRestrictions = classRestrictions;
    }

    public SkillCard(@NotNull String name, int flatAdditionalDamage, int healingAmount, boolean isMagicSkill, boolean isPhysicalSkill, @NotNull SkillTarget primaryTarget, List<Condition> appliedConditions, List<HeroClass> classRestrictions) {
        this.name = name;
        this.flatAdditionalDamage = flatAdditionalDamage;
        this.healingAmount = healingAmount;
        this.isMagicSkill = isMagicSkill;
        this.isPhysicalSkill = isPhysicalSkill;
        this.primaryTarget = primaryTarget;
        this.appliedConditions = appliedConditions;
        this.classRestrictions = classRestrictions;
    }

    @Override
    public String toString() {
        return "SkillCard{" +
                "name='" + name + '\'' +
                ", flatAdditionalDamage=" + flatAdditionalDamage +
                ", secondaryAttackFlatAdditionalDamage=" + secondaryAttackFlatAdditionalDamage +
                ", healingAmount=" + healingAmount +
                ", allDamageMultipliers=" + allDamageMultipliers +
                ", isMagicSkill=" + isMagicSkill +
                ", isPhysicalSkill=" + isPhysicalSkill +
                ", isSecondAttack=" + isSecondAttack +
                ", primaryTarget=" + primaryTarget +
                ", secondaryTarget=" + secondaryTarget +
                ", secondAttackTarget=" + secondAttackTarget +
                ", appliedConditions=" + appliedConditions +
                ", classRestrictions=" + classRestrictions +
                ", imageSource='" + imageSource + '\'' +
                '}';
    }
}
