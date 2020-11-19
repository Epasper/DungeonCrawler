import { makeSelection, getX, getY, selectedGridTileDiv, deleteSelection } from './mapSelection.js'
import { mapWidth, mapHeight } from './mapStyling.js'
import { actionAByContext, actionBByContext } from './mapActions.js'
import { party, directions } from './partyManager.js'
import { updateMap } from './dungeonMap.js'
import * as utils from './mapUtils.js'
import * as menus from './mapMenuActions.js'

const mapGrid = document.getElementById(`grid`);
let highlightedColumnLegendTile;
let highlightedRowLegendTile;
//let currentMenu = null;
// let currentButtonAwaitingConfirmation = null;
// let confirmTextBackup = '';
// let cancelTextBackup = '';
// let tooltipTextBackups = new Map();

export function injectTileListeners() {
    var tiles = document.getElementsByClassName("tile")
    for (var tile of tiles) {
        tile.addEventListener('click', tileClicked);
        tile.addEventListener('mouseenter', tileMouseEntered);
    }
    mapGrid.addEventListener('mouseleave', tileMouseLeft);
}

function injectMenuListeners() {
    const menuBtn = utils.getMappedElementById('menu-btn');
    menuBtn.addEventListener('mouseenter', menus.menuButtonEntered);
    menuBtn.addEventListener('mouseleave', menus.menuButtonExited);
    menuBtn.addEventListener('click', menus.menuClicked);

    let qSaveBtn = utils.getMappedElementById('save-q-btn');
    qSaveBtn.addEventListener('click', menus.requestQuickSave);

    let qLoadBtn = utils.getMappedElementById('load-q-btn');
    qLoadBtn.addEventListener('click', menus.requestQuickLoad);

    let backBtn = utils.getMappedElementById('back-btn');
    backBtn.addEventListener('click', menus.backButtonClicked)

    injectSaveMenuListeners();
    injectLoadMenuListeners();
}

function injectSaveMenuListeners() {
    const saveBtn = utils.getMappedElementById('save-btn');
    saveBtn.addEventListener('click', menus.saveMenuClicked);

    const saveButtons = Array.from(document.getElementsByClassName('button-save'));
    saveButtons.forEach(loadBtn => {
        loadBtn.addEventListener('click', menus.confirmableButtonClicked);
    })
    const cancelSaveButtons = Array.from(document.querySelectorAll('.save-slot .button-cancel'));
    cancelSaveButtons.forEach(cancelSaveBtn => {
        cancelSaveBtn.addEventListener('click', menus.exitConfirmation);
    })
    const confirmSaveButtons = Array.from(document.querySelectorAll('.save-slot .button-confirm'));
    confirmSaveButtons.forEach(confirmSaveBtn => {
        confirmSaveBtn.addEventListener('click', menus.saveButtonConfirmed);
    })

    const backBtn = utils.getMappedElementById('save-back-btn');
    backBtn.addEventListener('click', menus.backButtonClicked)

    const delBtn = utils.getMappedElementById('save-delete-btn');
    delBtn.addEventListener('click', menus.prepareSaveDeletion);
}

function injectLoadMenuListeners() {
    const loadBtn = utils.getMappedElementById('load-btn');
    loadBtn.addEventListener('click', menus.loadClicked);

    const loadButtons = Array.from(document.getElementsByClassName('button-load'));
    loadButtons.forEach(loadBtn => {
        loadBtn.addEventListener('click', menus.confirmableButtonClicked);
    })

    const cancelLoadButtons = Array.from(document.querySelectorAll('.load-slot .button-cancel'));
    cancelLoadButtons.forEach(cancelLoadBtn => {
        cancelLoadBtn.addEventListener('click', menus.exitConfirmation);
    })

    const confirmLoadButtons = Array.from(document.querySelectorAll('.load-slot .button-confirm'));
    confirmLoadButtons.forEach(confirmLoadBtn => {
        confirmLoadBtn.addEventListener('click', menus.loadButtonConfirmed);
    })

    const backBtn = utils.getMappedElementById('load-back-btn');
    backBtn.addEventListener('click', menus.backButtonClicked)
}

export function injectButtonsListeners() {
    let spawnBtn = utils.getMappedElementById('spawn-party-btn');
    spawnBtn.addEventListener('click', spawnPartyBackend);

    let actionBtn = utils.getMappedElementById('action-btn');
    let actionBtn1 = utils.getMappedElementById('action-btn-1');
    let actionBtn2 = utils.getMappedElementById('action-btn-2');
    actionBtn.addEventListener('click', makeAction);
    actionBtn1.addEventListener('click', actionAByContext);
    actionBtn2.addEventListener('click', actionBByContext);

    let moveBtn = utils.getMappedElementById('move-party-btn');
    moveBtn.addEventListener('click', teleportPartyBackend);

    let moveUpBtn = utils.getMappedElementById('move-up');
    let moveDownBtn = utils.getMappedElementById('move-down');
    let moveLeftBtn = utils.getMappedElementById('move-left');
    let moveRightBtn = utils.getMappedElementById('move-right');
    let moveButtons = [moveUpBtn, moveDownBtn, moveLeftBtn, moveRightBtn]
    moveButtons.forEach(mvBtn => {
        mvBtn.addEventListener('click', movePartyOneStepBackend);
    });
    document.addEventListener('keydown', keyPressedMove);

    injectMenuListeners();
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

    const response = await axios.get(`http://localhost:8080/spawnParty?coordX=${coordX}&coordY=${coordY}`);
    const backendParty = response.data;
    console.log('spawn: ', backendParty)

    const corridorFogDivs = Array.from(document.getElementsByClassName('fog-CORRIDOR'));
    corridorFogDivs.forEach(div => div.style.backgroundColor = '');

    await deleteSelection();
    await updateMap();
}

async function teleportPartyBackend() {
    let coordX = getX(selectedGridTileDiv);
    let coordY = getY(selectedGridTileDiv);

    await axios.get(`http://localhost:8080/moveParty?coordX=${coordX}&coordY=${coordY}`)

    await deleteSelection();
    await updateMap();
}

async function movePartyOneStepBackend({ target: clickedMoveButton }) {
    let [dir] = clickedMoveButton.id.split('-').slice(-1)
    dir = dir.toUpperCase();

    await axios.get(`http://localhost:8080/stepParty?dir=${dir}`);

    party.direction = directions[dir];

    await updateMap();
}

async function keyPressedMove({ keyCode }) {
    const moveBtn = utils.getMappedElementById('move-party-btn');
    if (moveBtn.disabled) return;
    let dirBtn;

    switch (keyCode) {
        case 37:    //LEFT
            dirBtn = utils.getMappedElementById('move-left');
            console.log('---KEY LOGGED: left---');
            break;
        case 38:    //UP
            dirBtn = utils.getMappedElementById('move-up');
            console.log('---KEY LOGGED: up---');
            break;
        case 39:    //RIGHT
            dirBtn = utils.getMappedElementById('move-right');
            console.log('---KEY LOGGED: right---');
            break;
        case 40:    //DOWN
            dirBtn = utils.getMappedElementById('move-down');
            console.log('---KEY LOGGED: down---');
            break;
        default:
            return;
    }

    document.removeEventListener('keydown', keyPressedMove)

    await movePartyOneStepBackend({ target: dirBtn })

    //Poniżej emulowane wciśnięcie przycisku myszą poprzez dodanie przyciskowi tymczasowej klasy '...-active', a potem wygaszenie jej
    let btnClasses = Array.from(dirBtn.classList);
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

    document.addEventListener('keydown', keyPressedMove);
}

async function tileClicked({ target: clickedTileDiv }) {
    var tileId = clickedTileDiv.id
    var tileType = clickedTileDiv.classList[1]

    const coordX = getX(clickedTileDiv);
    const coordY = getY(clickedTileDiv);
    const message = `Tile: ${tileId} (${tileType}) has been selected!`

    const { data: clickedTileBackend3 } = await axios
        .get(`http://localhost:8080/getClickedTile?coordX=${coordX}&coordY=${coordY}&message=${message}`)

    makeSelection(clickedTileDiv, mapGrid)

    await updateMap();
}

export function tileMouseEntered({ target: hoveredTileDiv }, colorIdentifier = 'default') {
    hideCrossHighlightElements();
    showCrossHighlightElements(hoveredTileDiv, colorIdentifier);
}

function tileMouseLeft({ target: exitedTileDiv }) {
    hideCrossHighlightElements();
}

function showCrossHighlightElements(centerDivElement, colorIdentifier) {
    let root = document.documentElement;
    let cssColor = window.getComputedStyle(root).getPropertyValue(`--highlight-color-${colorIdentifier}`);

    root.style.setProperty('--highlight-color', cssColor);

    let rowElement = utils.getMappedElementById('cross-highlight-row');
    let colElement = utils.getMappedElementById('cross-highlight-col');

    rowElement.classList.add('cross-highlight');
    colElement.classList.add('cross-highlight');

    const currentRow = getY(centerDivElement);
    const currentCol = getX(centerDivElement);

    rowElement.style.gridArea = `${currentRow + 1} / 1 / ${currentRow + 2} / ${mapWidth + 1}`;
    colElement.style.gridArea = `1 / ${currentCol + 1} / ${mapHeight + 1} / ${currentCol + 2}`;

    let columnLegendTile = utils.getMappedElementById(`col-${currentCol}`);
    let rowLegendTile = utils.getMappedElementById(`row-${currentRow}`);

    columnLegendTile.classList.add('legend-highlight');
    highlightedColumnLegendTile = columnLegendTile;
    rowLegendTile.classList.add('legend-highlight');
    highlightedRowLegendTile = rowLegendTile;
}

function hideCrossHighlightElements() {
    let rowElement = utils.getMappedElementById('cross-highlight-row');
    let colElement = utils.getMappedElementById('cross-highlight-col');

    rowElement.classList.remove('cross-highlight');
    colElement.classList.remove('cross-highlight');

    highlightedColumnLegendTile?.classList.remove('legend-highlight');
    highlightedRowLegendTile?.classList.remove('legend-highlight');
}