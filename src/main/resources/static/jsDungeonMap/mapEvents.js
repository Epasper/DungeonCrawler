import { makeSelection, getX, getY, selectedGridTileDiv } from './mapSelection.js'
import { mapWidth, mapHeight } from './mapStyling.js'
import { updateButtons } from './mapButtons.js'
import { draw } from './mapRender.js'
import { party, directions } from './partyManager.js'
import { finished, update } from './dungeonMap.js'

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

    let actionBtn = document.getElementById('action-btn');
    actionBtn.addEventListener('click', makeAction);

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
    // document.addEventListener('keyup', keyPressedMove);

    let saveBtn = document.getElementById('save-btn')
    saveBtn.addEventListener('click', requestMapSave)

    let loadBtn = document.getElementById('load-btn')
    loadBtn.addEventListener('click', requestMapLoad)
}

async function requestMapSave() {
    await axios.get(`http://localhost:8080/saveMap`)
}

async function requestMapLoad() {
    //await axios.get(`http://localhost:8080/loadMap`)
    window.location.replace(`http://localhost:8080/loadMap`)
}

function makeAction() {
    let selectedTile = selectedGridTileDiv;
    selectedTile.classList.remove('DOOR_CLOSED');
    selectedTile.classList.add('DOOR_OPENED');
    console.log('DOOOOOOOOOOOOOOOOOOOOOR');
}

async function spawnPartyBackend() {
    let coordX = getX(selectedGridTileDiv)
    let coordY = getY(selectedGridTileDiv)

    //Axios method:
    const response = await axios.get(`http://localhost:8080/spawnParty?coordX=${coordX}&coordY=${coordY}`);
    const backendParty = response.data;
    console.log('spawn: ', backendParty)

    // await draw();
    // updateButtons();

    await update();
}

async function teleportPartyBackend() {
    let coordX = getX(selectedGridTileDiv);
    let coordY = getY(selectedGridTileDiv);
    //let [heroId] = selectedHero.id.split('-').slice(-1); //slice (-1) zwroci tablicę o długosci 1 elementu od końca

    await axios.get(`http://localhost:8080/moveParty?coordX=${coordX}&coordY=${coordY}`)

    // await draw();
    // updateButtons();
    await update();
}

async function movePartyOneStepBackend({ target: clickedMoveButton }) {
    //console.log(clickedMoveButton);
    let [dir] = clickedMoveButton.id.split('-').slice(-1)
    dir = dir.toUpperCase();
    //console.log(direction);
    //debugger;
    //party.direction = directions[dir]
    await axios.get(`http://localhost:8080/stepParty?dir=${dir}`);

    party.direction = directions[dir];

    // await draw();
    // updateButtons();

    await update();
}

async function keyPressedMove({ keyCode }) {
    const moveBtn = document.getElementById('move-party-btn');
    if (moveBtn.disabled) return;
    let dirBtn;

    switch (keyCode) {
        case 37:    //LEFT
            dirBtn = document.getElementById('move-left');
            console.log('---KEY LOGGED: left---');
            break;
        case 38:    //UP
            dirBtn = document.getElementById('move-up');
            console.log('---KEY LOGGED: up---');
            break;
        case 39:    //RIGHT
            dirBtn = document.getElementById('move-right');
            console.log('---KEY LOGGED: right---');
            break;
        case 40:    //DOWN
            dirBtn = document.getElementById('move-down');
            console.log('---KEY LOGGED: down---');
            break;
        default:
            return;
    }

    console.log(`*** DISPATCH CLICK ***`)
    dirBtn.dispatchEvent(new Event('click'));

    //Poniżej emulowane wciśnięcie przycisku myszą poprzez dodanie przyciskowi tymczasowej klasy '...-active', a potem wygaszenie jej
    let btnClasses = Array.from(dirBtn.classList);
    //console.log(btnClasses);
    let tempActiveEmulationClassName = ''
    if (btnClasses.some(className => { return className.includes('blocked') })) {
        tempActiveEmulationClassName = 'move-button-blocked-active';
    } else {
        tempActiveEmulationClassName = 'move-button-active';
    }

    dirBtn.classList.add(tempActiveEmulationClassName);
    setTimeout(function () {
        dirBtn.classList.remove(tempActiveEmulationClassName);
    }, 100)



}

async function tileClicked({ target: clickedTileDiv }) {
    var tileId = clickedTileDiv.id
    var tileType = clickedTileDiv.classList[1]

    const coordX = getX(clickedTileDiv);
    const coordY = getY(clickedTileDiv);
    const message = `Tile: ${tileId} (${tileType}) has been selected!`

    const { data: clickedTileBackend3 } = await axios
        .get(`http://localhost:8080/getClickedTile?coordX=${coordX}&coordY=${coordY}&message=${message}`)
    console.log(clickedTileBackend3);

    makeSelection(clickedTileDiv, mapGrid)

    // await draw()
    // updateButtons()
    await update();
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

    hideCrossHighlightElements();
    showCrossHighlightElements(hoveredTileDiv);

    row.forEach(element => {
        // brightness(element, 10);
        // addCrossHighlight(element);
    });

    col.forEach(element => {
        // brightness(element, 10)
        // addCrossHighlight(element);
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

    hideCrossHighlightElements();
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

    //style.transition = `0s`;
    style.backgroundColor = `rgb(${r},${g},${b})`;
}

function addCrossHighlight(divElement) {
    divElement.classList.add('cross-highlight');
}

function removeCrossHighlight(divElement) {
    divElement.classList.remove('cross-highlight');
}

function showCrossHighlightElements(centerDivElement) {
    let rowElement = document.getElementById('cross-highlight-row');
    let colElement = document.getElementById('cross-highlight-col');

    rowElement.classList.add('cross-highlight');
    colElement.classList.add('cross-highlight');

    const currentRow = getY(centerDivElement);
    const currentCol = getX(centerDivElement);

    rowElement.style.gridArea = `${currentRow + 1} / 1 / ${currentRow + 2} / ${mapWidth + 1}`;
    colElement.style.gridArea = `1 / ${currentCol + 1} / ${mapHeight + 1} / ${currentCol + 2}`;

    let columnLegendTile = document.getElementById(`col-${currentCol}`);
    let rowLegendTile = document.getElementById(`row-${currentRow}`);

    columnLegendTile.classList.add('legend-highlight');
    rowLegendTile.classList.add('legend-highlight');
}

function hideCrossHighlightElements() {
    let rowElement = document.getElementById('cross-highlight-row');
    let colElement = document.getElementById('cross-highlight-col');
    let highlightedLegendTiles = Array.from(document.getElementsByClassName('legend-highlight'));

    rowElement.classList.remove('cross-highlight');
    colElement.classList.remove('cross-highlight');

    highlightedLegendTiles.forEach(tile => tile.classList.remove('legend-highlight'));
}

function resetColor(tile) {
    tile.style.backgroundColor = ``
    // setTimeout(function () {
    //     tile.style.transition = ``;
    // }, 2000)

}