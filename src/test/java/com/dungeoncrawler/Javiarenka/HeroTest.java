package com.dungeoncrawler.Javiarenka;

import com.dungeoncrawler.Javiarenka.character.Hero;
import com.dungeoncrawler.Javiarenka.character.HeroClass;
import com.dungeoncrawler.Javiarenka.character.Monster;
import com.dungeoncrawler.Javiarenka.character.NoMoreMoneyException;
import com.dungeoncrawler.Javiarenka.equipment.DamageType;
import com.dungeoncrawler.Javiarenka.equipment.Weapon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeroTest {

    private static Hero testedHero = new Hero();
    private static Monster testMonster = new Monster();
    private static Weapon testWeapon;
    private static final int MONSTER_STARTING_HP = 50;

    @BeforeAll
    static void beforeAllHeroTests() {
        testedHero.setName("Sir Test");
        testedHero.setName("von Tested");
        testedHero.setHeroClass(HeroClass.WARRIOR);
        testMonster.setName("Monstester");
        testWeapon = new Weapon("TestBazooka", List.of(testedHero.getHeroClass()), 2, 2, DamageType.DMG_SLASHING, 1);
    }

    @BeforeEach
    void resetTest(){
        testMonster.setAlive(true);
        testMonster.setHp(MONSTER_STARTING_HP);
        testedHero.setEquippedWeapon(testWeapon);
        testWeapon.setDamageDealt(20);
        testedHero.setMoney(30);
    }

    @Test
    void testAttackWithNoWeapon() {
        testedHero.setEquippedWeapon(null);
        testedHero.attack(testMonster);
        assert (testMonster.getHp() < MONSTER_STARTING_HP);
    }

    @Test
    void testAttackAndKillMonster() {
        testWeapon.setDamageDealt(60);
        testedHero.attack(testMonster);
        assert (!testMonster.isAlive());
    }

    @Test
    void testAttackAndNotKillMonster() {
        testedHero.setEquippedWeapon(testWeapon);
        testedHero.attack(testMonster);
        assert (testMonster.isAlive());
    }

    @Test
    void testGetMoneyFromEmptyPocket(){
        testedHero.setMoney(0);
        Exception exception = assertThrows(NoMoreMoneyException.class, () -> {
            testedHero.removeMoney(20);
        });

        String expectedMessage = NoMoreMoneyException.MESSAGE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
