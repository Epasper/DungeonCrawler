package com.dungeoncrawler.Javiarenka.character;

import lombok.Getter;
import lombok.Setter;

public class Skill {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int additionalDamage;
    @Getter
    @Setter
    private int additionalHp;
    @Getter
    @Setter
    private boolean isMagicSkill;
    @Getter
    @Setter
    private boolean isPhysicalSkill;
    @Getter
    @Setter
    private SkillTarget target;
    @Getter
    @Setter
    private int coolDown;
    @Getter
    @Setter
    private int manaCost;
    @Getter
    @Setter
    private int staminaCost;
    @Getter
    @Setter
    private CharacterStatus appliesStatus;
    @Getter
    @Setter
    private int appliedStatusDuration;
    /* @Getter @Setter
    private HashSet<Character> selectedCharacters; TODO */

    public Skill(String name, int additionalDamage, int additionalHp, boolean isMagicSkill, boolean isPhysicalSkill,
                 SkillTarget target, int coolDown, int manaCost, int staminaCost, CharacterStatus appliesStatus,
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
        this.appliesStatus = appliesStatus;
        this.appliedStatusDuration = appliedStatusDuration;
    }
}
