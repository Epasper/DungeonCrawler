package com.dungeoncrawler.Javiarenka;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.character.Monster;
import com.dungeoncrawler.Javiarenka.equipment.DamageType;
import com.dungeoncrawler.Javiarenka.equipment.Weapon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HeroTest {

    private static Hero testedHero = new Hero();
    private static Monster testMonster = new Monster();
    private static Weapon testWeapon;

    @BeforeAll
    static void beforeAllHeroTests() {
        testedHero.setName("Sir Test");
        testedHero.setName("von Tested");
        testedHero.setHeroClass(HeroClass.WARRIOR);
        testMonster.setName("Monstester");
        testWeapon = new Weapon("TestBazooka", List.of(testedHero.getHeroClass()), 2, 2, DamageType.DMG_SLASHING, 1);
    }

    @Test
    void testAttackWithNoWeapon() {
        testMonster.setHp(100);
        testedHero.setEquippedWeapon(null);
        testedHero.attack(testMonster);
        assert (testMonster.getHp() < 100);
    }

    @Test
    void testAttackAndKillMonster() {
        testMonster.setHp(20);
        testWeapon.setDamageDealt(50);
        testedHero.setEquippedWeapon(testWeapon);
        testedHero.attack(testMonster);
        assert (!testMonster.isAlive());
    }

    @Test
    void testAttackAndNotKillMonster() {
        testMonster.setHp(50);
        testWeapon.setDamageDealt(20);
        testedHero.setEquippedWeapon(testWeapon);
        testedHero.attack(testMonster);
        assert (testMonster.isAlive());
    }

}
