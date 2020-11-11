package com.dungeoncrawler.Javiarenka.creature.skill;

import com.dungeoncrawler.Javiarenka.creature.Condition;
import com.dungeoncrawler.Javiarenka.creature.DamageMultiplier;
import com.dungeoncrawler.Javiarenka.creature.HeroClass;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkillCard {
    @NotNull
    private String name = "";
    @NotNull
    private String description = "";
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
    private int movementSpeed = 0;
    private int attackRange = 0;

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

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

    //==================================================================================================================
    //END OF GETTERS AND SETTERS
    //==================================================================================================================

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

    //==================================================================================================================
    //END OF CONSTRUCTORS
    //==================================================================================================================

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
