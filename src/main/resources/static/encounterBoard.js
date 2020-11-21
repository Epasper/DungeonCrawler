let globalSelectedHero

function initialize() {
    document.onload = manageHeroesAndMonsters();
}

function manageHeroesAndMonsters(){
    heroes.forEach(hero => {
        const id = 'hero:' + hero.encounterXPosition + '---' + hero.encounterYPosition;
        const image = '../images/' + hero.heroClass + '.png';
        let heroDiv = document.getElementById(id);
        let heroSideDiv = document.getElementById(hero.name + '|||' + hero.surname);
        let heroHPBarText = document.getElementById(hero.name + '|||' + hero.surname + '|||hp-text');
        let heroMagicBarText = document.getElementById(hero.name + '|||' + hero.surname + '|||mshield-text');
        let heroPhysicalBarText = document.getElementById(hero.name + '|||' + hero.surname + '|||pshield-text');
        heroSideDiv.src = image;
        heroHPBarText.innerHTML = hero.hp + `/` + hero.maxHp;
        heroMagicBarText.innerHTML = hero.magicShield + `/` + hero.maxMagicShield;
        heroPhysicalBarText.innerHTML = hero.physicalShield + `/` + hero.maxPhysicalShield;
        heroDiv.style.zIndex = "3";
        heroDiv.src = image;
        hero.active ? heroDiv.classList.add("active-creature") : heroDiv.classList.add("inactive-creature")
        hero.active ? heroDiv.classList.remove("inactive-creature") : heroDiv.classList.remove("active-creature")
        heroDiv.onclick = () => getMovableTiles(hero);
    });
    monsters.forEach(monster => {
        const id = 'hero:' + monster.encounterXPosition + '---' + monster.encounterYPosition;
        const image = monster.imageLink;
        let monsterDiv = document.getElementById(id);
        monsterDiv.style.zIndex = "3";
        monsterDiv.src = image;
        monster.active ? monsterDiv.classList.add("active-creature") : monsterDiv.classList.add("inactive-creature")
        monster.active ? monsterDiv.classList.remove("inactive-creature") : monsterDiv.classList.remove("active-creature")
    })
}

function getSurnameFromId(id) {
    return id.substring(id.length, id.indexOf('|||') + 3);
}

function getNameFromId(id) {
    return id.substring(0, id.indexOf('|||'));
}

function selectCharacter(Id) {
    console.log(Id);
    clearTheBoardOfEffects();
    const surname = getSurnameFromId(Id);
    const name = getNameFromId(Id);
    const hero = heroes
        .filter(e => e.name === name)
        .find(f => f.surname === surname);
    let heroSkillsDiv = document.getElementById('current-hero-skills');
    while (heroSkillsDiv.firstChild) {
        heroSkillsDiv.removeChild(heroSkillsDiv.lastChild);
    }
    hero.skillCards.forEach(e => {
        let imgDiv = document.createElement("img");
        let skillImgDiv = document.createElement("img");
        imgDiv.src = '../images/skillCards/skillCardBlank.png';
        skillImgDiv.src = e.imageSource;
        imgDiv.classList.add('skill-card');
        skillImgDiv.classList.add('skill-image');
        imgDiv.onclick = function() {selectSkill(e, hero)};
        skillImgDiv.onclick = function() {selectSkill(e, hero)};
        let singleSkillDiv = document.createElement("div");
        singleSkillDiv.className = 'single-skill';
        singleSkillDiv.appendChild(imgDiv);
        singleSkillDiv.appendChild(skillImgDiv);
        heroSkillsDiv.appendChild(singleSkillDiv);
    });
}

function selectSkill(skill, hero) {
    console.log(skill.name);
    console.log(skill);
    if (skill.movementSpeed > 0) {
        getMovableTiles(hero, skill.movementSpeed);
    }
    if (skill.attackRange > 0) {
        getAttackRange(hero, skill.attackRange);
    }
}

function getMovableTiles(curentlyClickedHero, movementSpeed) {
    globalSelectedHero = curentlyClickedHero;
    clearTheBoardOfEffects();
    const xPos = curentlyClickedHero.encounterXPosition;
    const yPos = curentlyClickedHero.encounterYPosition;
    const isSelected = curentlyClickedHero.isSelected;
    for (i = xPos - movementSpeed; i < xPos + movementSpeed + 1; i++) {
        if (i < 1 || i > width) continue;
        for (j = yPos - movementSpeed; j < yPos + movementSpeed + 1; j++) {
            if (j < 1 || j > height) continue;
            let currentEffectDiv = document.getElementById('container' + i + '---' + j);
            currentEffectDiv.classList.add('walkable-effects');
            tiles[i - 1][j - 1].withinReach = true;
        }
    }
    curentlyClickedHero.isSelected = !isSelected;
}

function getAttackRange(curentlyClickedHero, range) {
    globalSelectedHero = curentlyClickedHero;
    clearTheBoardOfEffects();
    const xPos = curentlyClickedHero.encounterXPosition;
    const yPos = curentlyClickedHero.encounterYPosition;
    const isSelected = curentlyClickedHero.isSelected;
    for (i = xPos - range; i < xPos + range + 1; i++) {
        if (i < 1 || i > width) continue;
        for (j = yPos - range; j < yPos + range + 1; j++) {
            if (j < 1 || j > height) continue;
            let currentEffectDiv = document.getElementById('container' + i + '---' + j);
            currentEffectDiv.classList.add('in-range-effects');
            tiles[i - 1][j - 1].withinReach = true;
        }
    }
    curentlyClickedHero.isSelected = !isSelected;
}

function clearTheBoardOfEffects() {
    for (i = 1; i <= width; i++) {
        for (j = 1; j <= height; j++) {
            let currentEffectDiv = document.getElementById('container' + i + '---' + j);
            currentEffectDiv.classList.remove('walkable-effects');
            currentEffectDiv.classList.remove('in-range-effects');
        }
    }
}

function moveTheHero(currentId) {
    const xPosition = parseInt(currentId.slice(0, currentId.indexOf('---')));
    const yPosition = parseInt(currentId.slice(currentId.indexOf('---') + 3));
    const oldXPos = globalSelectedHero.encounterXPosition;
    const oldYPos = globalSelectedHero.encounterYPosition;
    let oldDiv = document.getElementById('hero:' + oldXPos + '---' + oldYPos);
    oldDiv.style.zIndex = "-1";
    delete oldDiv.src;
    globalSelectedHero.encounterXPosition = xPosition;
    globalSelectedHero.encounterYPosition = yPosition;
    const image = '../images/' + globalSelectedHero.heroClass + '.png';
    const heroId = 'hero:' + currentId;
    let currentDiv = document.getElementById(heroId);
    currentDiv.src = image;
    currentDiv.style.zIndex = "3";
    globalSelectedHero.active = false;
    initiativeOrder.push(initiativeOrder.shift());
    initiativeOrder[0].active = true;
    let oldInitiativeDiv = document.getElementById('init|||' + globalSelectedHero.initiative);
    oldInitiativeDiv.classList.remove("initiative-bar-active");
    console.log('init|||' + globalSelectedHero.initiative)
    let newInitiativeDiv = document.getElementById('init|||' + initiativeOrder[0].initiative);
    newInitiativeDiv.classList.add("initiative-bar-active");
    console.log('init|||' + initiativeOrder[0].initiative)
    let hero = heroes
        .filter(e => e.name === initiativeOrder[0].name)
        .find(f => f.surname === initiativeOrder[0].surname);
    hero && (hero.active = true);
    clearTheBoardOfEffects()
    initialize();
}

function testAddHp(heroNameAndSurname) {
    changeHitPoints(5, heroNameAndSurname, 'hp')
}

function testRemoveHp(heroNameAndSurname) {
    changeHitPoints(-5, heroNameAndSurname, 'hp')
}

function testAddMagicShield(heroNameAndSurname) {
    changeHitPoints(5, heroNameAndSurname, 'mshield')
}

function testRemoveMagicShield(heroNameAndSurname) {
    changeHitPoints(-5, heroNameAndSurname, 'mshield')
}

function testAddPhysicalShield(heroNameAndSurname) {
    changeHitPoints(5, heroNameAndSurname, 'pshield')
}

function testRemovePhysicalShield(heroNameAndSurname) {
    changeHitPoints(-5, heroNameAndSurname, 'pshield')
}

function changeHitPoints(numberToChange, heroNameAndSurname, defense) {
    const name = getNameFromId(heroNameAndSurname);
    const surname = getSurnameFromId(heroNameAndSurname);
    let hero = heroes
        .filter(e => e.name === name)
        .find(f => f.surname === surname)
    //todo:refactor with object spread notation
    defense === 'hp' ? hero.hp += numberToChange :
        defense === 'pshield' ? hero.physicalShield += numberToChange :
            defense === 'mshield' ? hero.magicShield += numberToChange : false
    let barPercent = defense === 'hp' ? hero.hp * (100 / hero.maxHp) :
        defense === 'pshield' ? hero.physicalShield * (100 / hero.maxPhysicalShield) :
            defense === 'mshield' ? hero.magicShield * (100 / hero.maxMagicShield) : false
    let greenBar = document.getElementById(heroNameAndSurname + '|||' + defense + '-green');
    let blueBar = document.getElementById(heroNameAndSurname + '|||' + defense + '-blue');
    let redBar = document.getElementById(heroNameAndSurname + '|||' + defense + '-red');
    let textBar = document.getElementById(heroNameAndSurname + '|||' + defense + '-text');
    greenBar.style.width = barPercent + '%'
    redBar.style.width = barPercent + '%'
    blueBar.style.width = barPercent + '%'
    textBar.innerHTML =
        defense === 'hp' ? hero.hp + `/` + hero.maxHp :
            defense === 'pshield' ? hero.physicalShield + `/` + hero.maxPhysicalShield :
                defense === 'mshield' ? hero.magicShield + `/` + hero.maxMagicShield : undefined
}