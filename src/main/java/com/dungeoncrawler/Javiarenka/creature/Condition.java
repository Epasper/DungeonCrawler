package com.dungeoncrawler.Javiarenka.creature;

import java.util.List;

public class Condition {
    CreatureStatus appliedStatuses;
    SkillTarget skillTarget;
    int duration;

    public CreatureStatus getAppliedStatuses() {
        return appliedStatuses;
    }

    public void setAppliedStatuses(CreatureStatus appliedStatuses) {
        this.appliedStatuses = appliedStatuses;
    }

    public SkillTarget getSkillTarget() {
        return skillTarget;
    }

    public void setSkillTarget(SkillTarget skillTarget) {
        this.skillTarget = skillTarget;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Condition() {
    }

    public Condition(CreatureStatus appliedStatuses, SkillTarget skillTarget, int duration) {
        this.appliedStatuses = appliedStatuses;
        this.skillTarget = skillTarget;
        this.duration = duration;
    }
}
