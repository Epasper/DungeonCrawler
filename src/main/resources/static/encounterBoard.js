let globalSelectedHero

function initialize() {
    document.onload = heroes.forEach(element => {
        const id = 'hero:' + element.encounterXPosition + '---' + element.encounterYPosition;
        const image = '../images/' + element.heroClass + '.png';
        let heroDiv = document.getElementById(id);
        let heroSideDiv = document.getElementById(element.name + '|||' + element.surname);
        let heroHPBarText = document.getElementById(element.name + '|||' + element.surname + '|||hp-text');
        let heroMagicBarText = document.getElementById(element.name + '|||' + element.surname + '|||mshield-text');
        let heroPhysicalBarText = document.getElementById(element.name + '|||' + element.surname + '|||pshield-text');
        heroSideDiv.src = image;
        heroHPBarText.innerHTML = element.hp + `/` + element.maxHp;
        heroMagicBarText.innerHTML = element.magicShield + `/` + element.maxMagicShield;
        heroPhysicalBarText.innerHTML = element.physicalShield + `/` + element.maxPhysicalShield;
        heroDiv.style.zIndex = "3";
        heroDiv.src = image;
        element.active ? heroDiv.classList.add("active-creature") : heroDiv.classList.add("inactive-creature")
        element.active ? heroDiv.classList.remove("inactive-creature") : heroDiv.classList.remove("active-creature")
        heroDiv.onclick = () => getMovableTiles(element);
    });
}

function getMovableTiles(curentlyClickedHero) {
    globalSelectedHero = curentlyClickedHero;
    clearTheBoardOfWalkEffects();
    const heroSpeed = curentlyClickedHero.speed;
    const xPos = curentlyClickedHero.encounterXPosition;
    const yPos = curentlyClickedHero.encounterYPosition;
    const isSelected = curentlyClickedHero.isSelected;
    for (i = xPos - heroSpeed; i < xPos + heroSpeed + 1; i++) {
        if (i < 1 || i > width) continue;
        for (j = yPos - heroSpeed; j < yPos + heroSpeed + 1; j++) {
            if (j < 1 || j > height) continue;
            let currentEffectDiv = document.getElementById('container' + i + '---' + j);
            currentEffectDiv.classList.add('walkable-effects');
            tiles[i - 1][j - 1].withinReach = true;
        }
    }
    curentlyClickedHero.isSelected = !isSelected;
}

function clearTheBoardOfWalkEffects() {
    for (i = 1; i <= width; i++) {
        for (j = 1; j <= height; j++) {
            let currentEffectDiv = document.getElementById('container' + i + '---' + j);
            currentEffectDiv.classList.remove('walkable-effects');
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
    clearTheBoardOfWalkEffects()
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
    const name = heroNameAndSurname.substring(0, heroNameAndSurname.indexOf('|||'))
    const surname = heroNameAndSurname.substring(heroNameAndSurname.length, heroNameAndSurname.indexOf('|||') + 3)
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