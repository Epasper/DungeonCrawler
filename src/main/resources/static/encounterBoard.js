let globalSelectedHero

function initialize() {
    document.onload = setOnClicksOnAllTiles();
    document.onload = heroes.forEach(element => {
        const id = 'hero:' + element.encounterXPosition + '---' + element.encounterYPosition;
        const image = '../images/' + element.heroClass + '.png';
        console.log(id + "|||" + image);
        let heroDiv = document.getElementById(id);
        heroDiv.style.zIndex = "3";
        heroDiv.src = image;
        heroDiv.onclick = () => getMoveableTiles(element);
    });
    console.log('initialized!');
}

function getMoveableTiles(curentlyClickedHero) {
    globalSelectedHero = curentlyClickedHero;
    clearTheBoardOfWalkEffects();
    const heroSpeed = curentlyClickedHero.speed;
    const xPos = curentlyClickedHero.encounterXPosition;
    const yPos = curentlyClickedHero.encounterYPosition;
    const isSelected = curentlyClickedHero.isSelected;
    for (i = xPos - heroSpeed; i < xPos + heroSpeed; i++) {
        if (i < 1) continue;
        for (j = yPos - heroSpeed; j < yPos + heroSpeed; j++) {
            if (j < 1) continue;
            let currentEffectDiv = document.getElementById('container' + i + '---' + j);
            let currentDiv = document.getElementById(i + '---' + j);
            currentEffectDiv.classList.add('walkable-effects');
            currentDiv.classList.add('walkable-tile');
            tiles[i - 1][j - 1].withinReach = true;
        }
    }
    curentlyClickedHero.isSelected = !isSelected;
}

function setOnClicksOnAllTiles() {
    for (i = 1; i <= width; i++) {
        for (j = 1; j <= height; j++) {
            let currentDiv = document.getElementById('container' + i + '---' + j);
            currentDiv.onclick = () => moveTheHero(i, j);
        }
    }
}

function clearTheBoardOfWalkEffects() {
    for (i = 1; i <= width; i++) {
        for (j = 1; j <= height; j++) {
            let currentEffectDiv = document.getElementById('container' + i + '---' + j);
            let currentDiv = document.getElementById(i + '---' + j);
            currentEffectDiv.classList.remove('walkable-effects');
            currentDiv.classList.remove('walkable-tile');
        }
    }
}

function moveTheHero(x, y) {
    console.log('StartWalking')
    globalSelectedHero.encounterXPosition = x;
    globalSelectedHero.encounterYPosition = y;
    const image = '../images/' + globalSelectedHero.heroClass + '.png';
    const id = 'hero:' + globalSelectedHero.encounterXPosition + '---' + globalSelectedHero.encounterYPosition;
    let currentDiv = document.getElementById(id);
    currentDiv.src = image;
    console.log('Walking! ' + x + ' -- ' + y)
}