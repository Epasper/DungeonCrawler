import { makeSelection, getX, getY, selectedGridTileDiv } from './mapSelection.js'
import { mapWidth, mapHeight } from './mapStyling.js'
import { updateButtons } from './mapButtons.js'
import { draw } from './mapRender.js'

const mapGrid = document.getElementById(`grid`)

export function injectTileListeners() {
    var tiles = document.getElementsByClassName("tile")
    for (var tile of tiles) {
        tile.addEventListener('click', tileClicked);
    }
    for (var tile of tiles) {
        tile.addEventListener('mouseenter', tileMouseEntered);
    }
    for (var tile of tiles) {
        tile.addEventListener('mouseleave', tileMouseLeft);
    }
}

export function injectButtonsListeners() {
    let spawnBtn = document.getElementById('spawn-party-btn');
    spawnBtn.addEventListener('click', spawnPartyBackend);

    let moveBtn = document.getElementById('move-party-btn');
    moveBtn.addEventListener('click', teleportPartyBackend);

    let moveUpBtn = document.getElementById('move-up');
    let moveDownBtn = document.getElementById('move-down');
    let moveLeftBtn = document.getElementById('move-left');
    let moveRightBtn = document.getElementById('move-right');
    let moveButtons = [moveUpBtn, moveDownBtn, moveLeftBtn, moveRightBtn]
    moveButtons.forEach(mvBtn => {
        mvBtn.addEventListener('click', movePartyOneStepBackend);
    });

    document.addEventListener('keydown', keyPressedMove);
}

async function spawnPartyBackend() {
    let coordX = getX(selectedGridTileDiv)
    let coordY = getY(selectedGridTileDiv)

    //Axios method:
    const response = await axios.get(`http://localhost:8080/spawnParty?coordX=${coordX}&coordY=${coordY}`);
    const backendParty = response.data;
    console.log('spawn: ', backendParty)

    draw();
    updateButtons();
}

async function teleportPartyBackend() {
    let coordX = getX(selectedGridTileDiv);
    let coordY = getY(selectedGridTileDiv);
    //let [heroId] = selectedHero.id.split('-').slice(-1); //slice (-1) zwroci tablicę o długosci 1 elementu od końca

    await axios.get(`http://localhost:8080/moveParty?coordX=${coordX}&coordY=${coordY}`)

    updateButtons();
    draw();
}

async function movePartyOneStepBackend({ target: clickedMoveButton }) {
    console.log(clickedMoveButton);
    let [direction] = clickedMoveButton.id.split('-').slice(-1)
    direction = direction.toUpperCase();
    console.log(direction);
    await axios.get(`http://localhost:8080/stepParty?dir=${direction}`);

    updateButtons();
    draw();
}

async function keyPressedMove({ keyCode }) {
    const moveBtn = document.getElementById('move-party-btn');
    if(moveBtn.disabled) return;
    let dirBtn;

    switch (keyCode) {
        case 37:    //LEFT
            dirBtn = document.getElementById('move-left');
            console.log('key left');
            break;
        case 38:    //UP
            dirBtn = document.getElementById('move-up');
            console.log('key up');
            break;
        case 39:    //RIGHT
            dirBtn = document.getElementById('move-right');
            console.log('key right');
            break;
        case 40:    //DOWN
            dirBtn = document.getElementById('move-down');
            console.log('key down');
            break;
        default:
            return;
    }
    dirBtn.dispatchEvent(new Event('click'));

    //Poniżej emulowane wciśnięcie przycisku myszą poprzez dodanie przyciskowi tymczasowej klasy '...-active', a potem wygaszenie jej
    let btnClasses = Array.from(dirBtn.classList);
    console.log(btnClasses);
    let tempActiveEmulationClassName = ''
    if(btnClasses.some(className => {return className.includes('blocked')}))
    {
        tempActiveEmulationClassName = 'move-button-blocked-active';
    } else {
        tempActiveEmulationClassName = 'move-button-active';
    }

    dirBtn.classList.add(tempActiveEmulationClassName);
    setTimeout( function () {
        dirBtn.classList.remove(tempActiveEmulationClassName);
    }, 100)
}

async function tileClicked({ target: clickedTileDiv }) {
    var tileId = clickedTileDiv.id
    var tileType = clickedTileDiv.classList[1]

    let postData = {
        x: getX(clickedTileDiv),
        y: getY(clickedTileDiv),
        message: `Tile: ${tileId} (${tileType}) has been selected!`
    };

    const { data: clickedTileBackend } = await axios.post('http://localhost:8080/getClickedTile', postData);
    console.log(clickedTileBackend);

    makeSelection(clickedTileDiv, mapGrid)

    updateButtons()
    draw()
}

function tileMouseEntered({ target: hoveredTileDiv }) {
    var tileId = ``
    tileId = hoveredTileDiv.id

    var x = tileId.split(`-`)[0]
    var y = tileId.split(`-`)[1]

    var row = getRowOfElements(y)
    var col = getColOfElements(x)

    row[0].style.color = (`rgb(220,220,220)`)
    col[0].style.color = (`rgb(220,220,220)`)
    row[0].style.fontWeight = (`bold`)
    col[0].style.fontWeight = (`bold`)
    row[0].style.fontSize = (`1.5vh`)
    col[0].style.fontSize = (`1.5vh`)

    row.forEach(element => {
        brightness(element, 10)
    });

    col.forEach(element => {
        brightness(element, 10)
    });

    //console.log('on!', x, `/`, y)
}

function tileMouseLeft({ target: exitedTileDiv }) {
    var tileId = ``
    tileId = exitedTileDiv.id

    var x = tileId.split(`-`)[0]
    var y = tileId.split(`-`)[1]

    var row = getRowOfElements(y)
    var col = getColOfElements(x)

    row[0].style.color = (``)
    col[0].style.color = (``)
    row[0].style.fontWeight = (``)
    col[0].style.fontWeight = (``)
    row[0].style.fontSize = (``)
    col[0].style.fontSize = (``)

    row.forEach(element => {
        resetColor(element)
    });

    col.forEach(element => {
        resetColor(element)
    });
}

function getRowOfElements(rowNumber) {
    var row = [document.getElementById(`row-${rowNumber}`)]
    for (var i = 0; i < mapWidth; i++) {
        var tile = document.getElementById(`${i}-${rowNumber}`)
        row.push(tile)
    }
    return row
}

function getColOfElements(colNumber) {
    var col = [document.getElementById(`col-${colNumber}`)]
    for (var i = 0; i < mapHeight; i++) {
        var tile = document.getElementById(`${colNumber}-${i}`)
        col.push(tile)
    }
    return col
}

function brightness(tile, value) {
    var currentColor = window.getComputedStyle(tile).backgroundColor
    var style = tile.style

    var r = 0
    var g = 0
    var b = 0

    r = currentColor.split(`(`)[1].split(`)`)[0].split(',')[0] * 1
    g = currentColor.split(`(`)[1].split(`)`)[0].split(',')[1] * 1
    b = currentColor.split(`(`)[1].split(`)`)[0].split(',')[2] * 1

    r += value
    g += value + 10
    b += value

    style.backgroundColor = `rgb(${r},${g},${b})`
}

function resetColor(tile) {
    tile.style.backgroundColor = ``
}