package com.dungeoncrawler.Javiarenka.staticResources;

import com.dungeoncrawler.Javiarenka.character.CharacterStatus;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.character.Skill;
import com.dungeoncrawler.Javiarenka.character.SkillTarget;

import java.util.Arrays;
import java.util.List;

public interface SkillResources {
    static List<Skill> defaultSkills() {
        List<Skill> defaultSkills = Arrays.asList(
                new Skill("Allies Healing", 0, 10, true, false,
                        SkillTarget.ALL_ALLIES, 1, 20, 0, List.of(CharacterStatus.DEFAULT),
                        1, List.of(HeroClass.HEALER)),
                new Skill("Self Healing", 5, 15, false, true,
                        SkillTarget.ALLY_AND_ENEMY, 2, 5, 10, List.of(CharacterStatus.DEFAULT),
                        1, List.of(HeroClass.HEALER)),
                new Skill("Hit and burn", 10, 0, true, false,
                        SkillTarget.ENEMY, 3, 15, 5, List.of(CharacterStatus.BURNED),
                        2, List.of(HeroClass.WIZARD)),
//                new Skill("Hit and speed up", 2, 0, false, true,
//                        SkillTarget.ENEMY, 4, 5, 12, List.of(CharacterStatus.SPEEDED),
//                        2, List.of(HeroClass.WIZARD)),
                new Skill("Hit, bleed and heal", 10, 10, true, false,
                        SkillTarget.ALLY_AND_ENEMY, 4, 20, 15, List.of(CharacterStatus.BLEEDING),
                        2, List.of(HeroClass.WARRIOR)),
                new Skill("Hit and self-hurt", 20, -10, false, true,
                        SkillTarget.ENEMY, 3, 5, 15, List.of(CharacterStatus.DEFAULT),
                        2, List.of(HeroClass.WARRIOR)));
        return defaultSkills;
    }
}
