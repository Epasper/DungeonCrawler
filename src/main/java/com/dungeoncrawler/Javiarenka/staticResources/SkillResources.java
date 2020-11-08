package com.dungeoncrawler.Javiarenka.staticResources;

import com.dungeoncrawler.Javiarenka.creature.*;

import java.util.ArrayList;
import java.util.List;

public class SkillResources {

    static List<SkillCard> allStartingSkillCards = new ArrayList<>();

    public static List<SkillCard> getAllStartingSkillCards() {
        return allStartingSkillCards;
    }

    public static void setAllStartingSkillCards(List<SkillCard> allStartingSkillCards) {
        SkillResources.allStartingSkillCards = allStartingSkillCards;
    }

    public void addAllSkills() {
        SkillCard healCard = new SkillCard();
        healCard.setName("Heals All Allies");
        healCard.setHealingAmount(10);
        healCard.setMagicSkill(true);
        healCard.setPrimaryTarget(SkillTarget.ENEMY);
        healCard.setSecondaryTarget(SkillTarget.ALL_ALLIES);
        healCard.setClassRestrictions(List.of(HeroClass.HEALER));
        healCard.setImageSource("../images/skillCards/24.png");
        allStartingSkillCards.add(healCard);

        SkillCard hitsAndHeals = new SkillCard();
        hitsAndHeals.setName("Hits and Heals Self");
        hitsAndHeals.setFlatAdditionalDamage(5);
        hitsAndHeals.setHealingAmount(15);
        hitsAndHeals.setPhysicalSkill(true);
        hitsAndHeals.setPrimaryTarget(SkillTarget.ENEMY);
        hitsAndHeals.setSecondaryTarget(SkillTarget.SELF);
        hitsAndHeals.setClassRestrictions(List.of(HeroClass.HEALER));
        hitsAndHeals.setImageSource("../images/skillCards/18.png");
        allStartingSkillCards.add(hitsAndHeals);

        SkillCard hitsAndBurns = new SkillCard();
        hitsAndBurns.setName("Hits and Sets on Fire");
        hitsAndBurns.setFlatAdditionalDamage(10);
        hitsAndBurns.setMagicSkill(true);
        hitsAndBurns.setPrimaryTarget(SkillTarget.ENEMY);
        hitsAndBurns.setAppliedConditions(List.of(new Condition(CreatureStatus.BURNED, SkillTarget.ENEMY, 3)));
        hitsAndBurns.setClassRestrictions(List.of(HeroClass.WIZARD));
        hitsAndBurns.setImageSource("../images/skillCards/6.png");
        allStartingSkillCards.add(hitsAndBurns);

        SkillCard hitsAndHastens = new SkillCard();
        hitsAndHastens.setName("Hits and Speeds Up");
        hitsAndHastens.setFlatAdditionalDamage(2);
        hitsAndHastens.setMagicSkill(true);
        hitsAndHastens.setPrimaryTarget(SkillTarget.ENEMY);
        hitsAndHastens.setAppliedConditions(List.of(new Condition(CreatureStatus.HASTED, SkillTarget.SELF, 3)));
        hitsAndHastens.setClassRestrictions(List.of(HeroClass.WIZARD));
        hitsAndHastens.setImageSource("../images/skillCards/37.png");
        allStartingSkillCards.add(hitsAndHastens);

        SkillCard hitsBleedsAndHealsSelf = new SkillCard();
        hitsBleedsAndHealsSelf.setName("Hits, Bleeds Enemy and Heals Itself");
        hitsBleedsAndHealsSelf.setFlatAdditionalDamage(10);
        hitsBleedsAndHealsSelf.setHealingAmount(10);
        hitsBleedsAndHealsSelf.setPhysicalSkill(true);
        hitsBleedsAndHealsSelf.setPrimaryTarget(SkillTarget.ENEMY);
        hitsBleedsAndHealsSelf.setSecondaryTarget(SkillTarget.SELF);
        hitsBleedsAndHealsSelf.setAppliedConditions(List.of(new Condition(CreatureStatus.BLEEDING, SkillTarget.ENEMY, 3)));
        hitsBleedsAndHealsSelf.setClassRestrictions(List.of(HeroClass.WARRIOR));
        hitsBleedsAndHealsSelf.setImageSource("../images/skillCards/30.png");
        allStartingSkillCards.add(hitsBleedsAndHealsSelf);

        SkillCard hitsEnemyAndSelf = new SkillCard();
        hitsEnemyAndSelf.setName("Hits Enemy and Self");
        hitsEnemyAndSelf.setFlatAdditionalDamage(20);
        hitsEnemyAndSelf.setSecondAttack(true);
        hitsEnemyAndSelf.setSecondAttackTarget(SkillTarget.SELF);
        hitsEnemyAndSelf.setSecondaryAttackFlatAdditionalDamage(10);
        hitsEnemyAndSelf.setPhysicalSkill(true);
        hitsEnemyAndSelf.setPrimaryTarget(SkillTarget.ENEMY);
        hitsEnemyAndSelf.setSecondaryTarget(SkillTarget.SELF);
        hitsEnemyAndSelf.setClassRestrictions(List.of(HeroClass.WARRIOR));
        hitsEnemyAndSelf.setImageSource("../images/skillCards/4.png");
        allStartingSkillCards.add(hitsEnemyAndSelf);

        SkillCard hitsAndWeakens = new SkillCard();
        hitsAndWeakens.setName("Hits Enemy and Weakens It");
        hitsAndWeakens.setFlatAdditionalDamage(15);
        hitsAndWeakens.setPhysicalSkill(true);
        hitsAndWeakens.setPrimaryTarget(SkillTarget.ENEMY);
        hitsAndWeakens.setClassRestrictions(List.of(HeroClass.KNIGHT));
        hitsAndWeakens.setAppliedConditions(List.of(new Condition(CreatureStatus.WEAKENED, SkillTarget.ENEMY, 3)));
        hitsAndWeakens.setImageSource("../images/skillCards/12.png");
        allStartingSkillCards.add(hitsAndWeakens);

        SkillCard hitsAndSlowsDownAll = new SkillCard();
        hitsAndSlowsDownAll.setName("Hits and slows down all enemies");
        hitsAndSlowsDownAll.setFlatAdditionalDamage(5);
        hitsAndSlowsDownAll.setPhysicalSkill(true);
        hitsAndSlowsDownAll.setPrimaryTarget(SkillTarget.ENEMY);
        hitsAndSlowsDownAll.setClassRestrictions(List.of(HeroClass.KNIGHT));
        hitsAndSlowsDownAll.setAppliedConditions(List.of(new Condition(CreatureStatus.SLOWED, SkillTarget.ALL_ENEMIES, 3)));
        hitsAndSlowsDownAll.setImageSource("../images/skillCards/40.png");
        allStartingSkillCards.add(hitsAndSlowsDownAll);

        SkillCard blindTarget = new SkillCard();
        blindTarget.setName("Hits and Blinds Target");
        blindTarget.setFlatAdditionalDamage(21);
        blindTarget.setPhysicalSkill(true);
        blindTarget.setPrimaryTarget(SkillTarget.ENEMY);
        blindTarget.setClassRestrictions(List.of(HeroClass.ROGUE));
        blindTarget.setAppliedConditions(List.of(new Condition(CreatureStatus.BLINDED, SkillTarget.ENEMY, 3)));
        blindTarget.setImageSource("../images/skillCards/19.png");
        allStartingSkillCards.add(blindTarget);

        SkillCard poisonTarget = new SkillCard();
        poisonTarget.setName("Hits and Poisons Target");
        poisonTarget.setFlatAdditionalDamage(21);
        poisonTarget.setPhysicalSkill(true);
        poisonTarget.setPrimaryTarget(SkillTarget.ENEMY);
        poisonTarget.setClassRestrictions(List.of(HeroClass.ROGUE));
        poisonTarget.setAppliedConditions(List.of(new Condition(CreatureStatus.POISONED, SkillTarget.ENEMY, 3)));
        poisonTarget.setImageSource("../images/skillCards/26.png");
        allStartingSkillCards.add(poisonTarget);

        SkillCard shootAndDisappear = new SkillCard();
        shootAndDisappear.setName("Shoot and Disappear");
        shootAndDisappear.setFlatAdditionalDamage(18);
        shootAndDisappear.setPhysicalSkill(true);
        shootAndDisappear.setPrimaryTarget(SkillTarget.ENEMY);
        shootAndDisappear.setSecondaryTarget(SkillTarget.SELF);
        shootAndDisappear.setClassRestrictions(List.of(HeroClass.ARCHER));
        shootAndDisappear.setAppliedConditions(List.of(new Condition(CreatureStatus.INVISIBLE, SkillTarget.SELF, 3)));
        shootAndDisappear.setImageSource("../images/skillCards/43.png");
        allStartingSkillCards.add(shootAndDisappear);

        SkillCard shootAndSetOnFire = new SkillCard();
        shootAndSetOnFire.setName("Shoot and Set on Fire");
        shootAndSetOnFire.setFlatAdditionalDamage(8);
        shootAndSetOnFire.setMagicSkill(true);
        shootAndSetOnFire.setPrimaryTarget(SkillTarget.ENEMY);
        shootAndSetOnFire.setClassRestrictions(List.of(HeroClass.ARCHER));
        shootAndSetOnFire.setAppliedConditions(List.of(new Condition(CreatureStatus.BURNED, SkillTarget.ENEMY, 3)));
        shootAndSetOnFire.setImageSource("../images/skillCards/3.png");
        allStartingSkillCards.add(shootAndSetOnFire);
    }

    public SkillResources() {
        if(allStartingSkillCards.isEmpty()) addAllSkills();
    }
}
