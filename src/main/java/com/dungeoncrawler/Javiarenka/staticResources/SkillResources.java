package com.dungeoncrawler.Javiarenka.staticResources;

import com.dungeoncrawler.Javiarenka.creature.CreatureStatus;
import com.dungeoncrawler.Javiarenka.creature.HeroClass;
import com.dungeoncrawler.Javiarenka.creature.Skill;
import com.dungeoncrawler.Javiarenka.creature.SkillTarget;

import java.util.Arrays;
import java.util.List;

public interface SkillResources {
    static List<Skill> defaultSkills() {
        List<Skill> defaultSkills = Arrays.asList(
                new Skill("Heals all allies", 0, 10, true, false,
                        SkillTarget.ALL_ALLIES, 1, 20, 0, List.of(CreatureStatus.DEFAULT),
                        1, List.of(HeroClass.HEALER)),
                new Skill("Hits and heals itself", 5, 15, false, true,
                        SkillTarget.SELF_AND_ENEMY, 2, 5, 10, List.of(CreatureStatus.DEFAULT),
                        1, List.of(HeroClass.HEALER)),
                new Skill("Hits and burns", 10, 0, true, false,
                        SkillTarget.ENEMY, 3, 15, 5, List.of(CreatureStatus.BURNED),
                        2, List.of(HeroClass.WIZARD)),
                new Skill("Hit and speeds up", 2, 0, false, true,
                        SkillTarget.SELF_AND_ENEMY, 4, 5, 12, List.of(CreatureStatus.HASTED),
                        2, List.of(HeroClass.WIZARD)),
                new Skill("Hits, bleeds and heals itself", 10, 10, true, false,
                        SkillTarget.SELF_AND_ENEMY, 4, 20, 15, List.of(CreatureStatus.BLEEDING),
                        2, List.of(HeroClass.WARRIOR)),
                new Skill("Hits and self-hurt", 20, -10, false, true,
                        SkillTarget.SELF_AND_ENEMY, 3, 5, 15, List.of(CreatureStatus.DEFAULT),
                        2, List.of(HeroClass.WARRIOR)),
                new Skill("Hits and weakens", 15, 0, true, false,
                        SkillTarget.ENEMY, 2, 15, 5, List.of(CreatureStatus.WEAKENED),
                        2, List.of(HeroClass.KNIGHT)),
                new Skill("Hits and slows down all enemies", 25, 0, false, true,
                        SkillTarget.ALL_ENEMIES, 5, 5, 25, List.of(CreatureStatus.SLOWED),
                        2, List.of(HeroClass.KNIGHT)),
                new Skill("Hits and poisons", 17, 0, true, false,
                        SkillTarget.ENEMY, 2, 14, 8, List.of(CreatureStatus.POISONED),
                        1, List.of(HeroClass.ROGUE)),
                new Skill("Hits and blinds", 21, 0, false, true,
                        SkillTarget.ENEMY, 4, 7, 19, List.of(CreatureStatus.BLINDED),
                        2, List.of(HeroClass.ROGUE)),
                new Skill("Shoots and becomes invisible", 18, 0, true, false,
                        SkillTarget.SELF_AND_ENEMY, 2, 22, 14, List.of(CreatureStatus.INVISIBLE),
                        3, List.of(HeroClass.ARCHER)),
                new Skill("Shoots and burns", 8, 0, false, true,
                        SkillTarget.ALL_ENEMIES, 2, 4, 15, List.of(CreatureStatus.BURNED),
                        3, List.of(HeroClass.ARCHER)));
        return defaultSkills;
    }
}
