let globalSelectedHero

function initialize() {
    document.onload = heroes.forEach(element => {
        const id = 'hero:' + element.encounterXPosition + '---' + element.encounterYPosition;
        const image = '../images/' + element.heroClass + '.png';
        let heroDiv = document.getElementById(id);
        let heroSideDiv = document.getElementById(element.name + '|||' + element.surname);
        let heroHPBarText = document.getElementById(element.name + '|||' + element.surname + '|||text');
        heroSideDiv.src = image;
        heroHPBarText.innerHTML = element.hp + `/` + element.totalHp;
        heroDiv.style.zIndex = "3";
        heroDiv.src = image;
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
    clearTheBoardOfWalkEffects()
    initialize();
}

function testAddHp(heroNameAndSurname) {
    changeHitPoints(5, heroNameAndSurname)
}

function testRemoveHp(heroNameAndSurname) {
    changeHitPoints(-5, heroNameAndSurname)
}

function changeHitPoints(numberToChange, heroNameAndSurname) {
    const name = heroNameAndSurname.substring(0, heroNameAndSurname.indexOf('|||'))
    const surname = heroNameAndSurname.substring(heroNameAndSurname.length, heroNameAndSurname.indexOf('|||') + 3)
    let hero = heroes
        .filter(e => e.name === name)
        .find(f => f.surname === surname)
    hero.hp += numberToChange;
    let barPercent = hero.hp * (100 / hero.totalHp);
    let greenBar = document.getElementById(heroNameAndSurname + '|||green');
    let blueBar = document.getElementById(heroNameAndSurname + '|||blue');
    let redBar = document.getElementById(heroNameAndSurname + '|||red');
    let textBar = document.getElementById(heroNameAndSurname + '|||text');
    greenBar.style.width = barPercent + '%'
    redBar.style.width = barPercent + '%'
    blueBar.style.width = barPercent + '%'
    textBar.innerHTML = hero.hp + `/` + hero.totalHp
}