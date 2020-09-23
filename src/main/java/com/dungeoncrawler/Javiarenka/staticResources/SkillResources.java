package com.dungeoncrawler.Javiarenka.staticResources;

import com.dungeoncrawler.Javiarenka.character.Skill;
import com.dungeoncrawler.Javiarenka.character.SkillTarget;

import java.util.Arrays;
import java.util.List;

public interface SkillResources {
    static List<Skill> defaultSkills() {
        List<Skill> defaultSkills = Arrays.asList(
                new Skill("Allies Healing", 0, 10, true, false,
                        SkillTarget.ALL_ALLIES, 1, 20, 0, CharacterStatus.DEFAULT, 1),
                new Skill("Self Healing", 5, 15, false, true,
                        SkillTarget.ALLY_AND_ENEMY, 2, 5, 10, CharacterStatus.DEFAULT, 1),
                new Skill("Hit and burn", 10, 0, true, false,
                        SkillTarget.ENEMY, 3, 15, 5, CharacterStatus.BURNED, 2));

        return defaultSkills;
    }
}
