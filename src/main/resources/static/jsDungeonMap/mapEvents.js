import { makeSelection, getX, getY, selectedGridTileDiv, deleteSelection } from './mapSelection.js'
import { mapWidth, mapHeight } from './mapStyling.js'
import { actionAByContext, actionBByContext } from './mapButtons.js'
import { draw } from './mapRender.js'
import { party, directions } from './partyManager.js'
import { finished, getMappedElementById, updateMap } from './dungeonMap.js'

const mapGrid = document.getElementById(`grid`);
let highlightedColumnLegendTile;
let highlightedRowLegendTile;
let currentMenu = null;

export function injectTileListeners() {
    var tiles = document.getElementsByClassName("tile")
    for (var tile of tiles) {
        tile.addEventListener('click', tileClicked);
        tile.addEventListener('mouseenter', tileMouseEntered);
    }
    mapGrid.addEventListener('mouseleave', tileMouseLeft);
}

function injectMenuListeners() {
    const menuBtn = getMappedElementById('menu-btn');
    menuBtn.addEventListener('mouseenter', menuButtonEntered);
    menuBtn.addEventListener('mouseleave', menuButtonExited);
    menuBtn.addEventListener('click', menuClicked);

    let qSaveBtn = getMappedElementById('q-save-btn')
    qSaveBtn.addEventListener('click', requestQuickSave)

    let qLoadBtn = getMappedElementById('q-load-btn')
    qLoadBtn.addEventListener('click', requestQuickLoad)

    injectSaveMenuListeners();
    injectLoadMenuListeners();
}

function injectSaveMenuListeners() {
    const saveBtn = getMappedElementById('save-btn');
    saveBtn.addEventListener('click', saveMenuClicked);

    const save1Btn = getMappedElementById('save-1-btn');
    save1Btn.addEventListener('click', saveButtonClicked);
    const save2Btn = getMappedElementById('save-2-btn');
    save2Btn.addEventListener('click', saveButtonClicked);
    const save3Btn = getMappedElementById('save-3-btn');
    save3Btn.addEventListener('click', saveButtonClicked);

    const backBtn = getMappedElementById('save-back-btn');
    backBtn.addEventListener('click', backButtonClicked)
}

function injectLoadMenuListeners() {
    const loadBtn = getMappedElementById('load-btn');
    loadBtn.addEventListener('click', loadClicked);

    const load1Btn = getMappedElementById('load-1-btn');
    load1Btn.addEventListener('click', loadButtonClicked);
    const load2Btn = getMappedElementById('load-2-btn');
    load2Btn.addEventListener('click', loadButtonClicked);
    const load3Btn = getMappedElementById('load-3-btn');
    load3Btn.addEventListener('click', loadButtonClicked);

    const backBtn = getMappedElementById('load-back-btn');
    backBtn.addEventListener('click', backButtonClicked)
}

async function saveButtonClicked({target: saveSlotBtn}) {
    const saveSlotNumber = saveSlotBtn.dataset.slot;
    console.log('save: ', saveSlotBtn, 'number: ', saveSlotNumber)
    await axios.get(`http://localhost:8080/saveMap?saveSlotNumber=${saveSlotNumber}`);
}

async function loadButtonClicked({target: loadSlotBtn}) {
    const loadSlotNumber = loadSlotBtn.dataset.slot;
    console.log('load: ', loadSlotBtn, 'number: ', loadSlotNumber);
    // await axios.get(`http://localhost:8080/loadMap?loadSlotNumber=${loadSlotNumber}`);
    window.location.replace(`http://localhost:8080/loadMap?loadSlotNumber=${loadSlotNumber}`)
}

function backButtonClicked() {
    hideMenu(currentMenu);
    showMenu(getMappedElementById('main-menu'));
}

export function injectButtonsListeners() {
    let spawnBtn = getMappedElementById('spawn-party-btn');
    spawnBtn.addEventListener('click', spawnPartyBackend);

    let actionBtn = getMappedElementById('action-btn');
    let actionBtn1 = getMappedElementById('action-btn-1');
    let actionBtn2 = getMappedElementById('action-btn-2');
    actionBtn.addEventListener('click', makeAction);
    actionBtn1.addEventListener('click', actionAByContext);
    actionBtn2.addEventListener('click', actionBByContext);

    let moveBtn = getMappedElementById('move-party-btn');
    moveBtn.addEventListener('click', teleportPartyBackend);

    let moveUpBtn = getMappedElementById('move-up');
    let moveDownBtn = getMappedElementById('move-down');
    let moveLeftBtn = getMappedElementById('move-left');
    let moveRightBtn = getMappedElementById('move-right');
    let moveButtons = [moveUpBtn, moveDownBtn, moveLeftBtn, moveRightBtn]
    moveButtons.forEach(mvBtn => {
        mvBtn.addEventListener('click', movePartyOneStepBackend);
    });
    document.addEventListener('keydown', keyPressedMove);

    injectMenuListeners();
}

async function requestQuickSave() {
    //await axios.get(`http://localhost:8080/quickSaveMap`);
    await axios.get(`http://localhost:8080/saveMap?saveSlotNumber=${0}`);
}

async function requestQuickLoad() {
    window.location.replace(`http://localhost:8080/loadMap?loadSlotNumber=${0}`)
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
    //let [heroId] = selectedHero.id.split('-').slice(-1); //slice (-1) zwroci tablicę o długosci 1 elementu od końca

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
    const moveBtn = getMappedElementById('move-party-btn');
    if (moveBtn.disabled) return;
    let dirBtn;

    switch (keyCode) {
        case 37:    //LEFT
            dirBtn = getMappedElementById('move-left');
            console.log('---KEY LOGGED: left---');
            break;
        case 38:    //UP
            dirBtn = getMappedElementById('move-up');
            console.log('---KEY LOGGED: up---');
            break;
        case 39:    //RIGHT
            dirBtn = getMappedElementById('move-right');
            console.log('---KEY LOGGED: right---');
            break;
        case 40:    //DOWN
            dirBtn = getMappedElementById('move-down');
            console.log('---KEY LOGGED: down---');
            break;
        default:
            return;
    }
    
    document.removeEventListener('keydown', keyPressedMove)

    await movePartyOneStepBackend({target: dirBtn})

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

function menuButtonEntered() {
    if (currentMenu) return;

    const menu = getMappedElementById('main-menu');

    if (menu.classList.length == 1) return;

    menu.classList.remove('hidden');
    menu.classList.add('partial');
}

function menuButtonExited() {
    const menu = getMappedElementById('main-menu');

    if (menu.classList.length == 1) return;

    menu.classList.remove('partial');
    menu.classList.add('hidden');
}

function showMenu(menu) {
    menu.classList.remove('hidden');
    menu.classList.remove('partial');
}

function hideMenu(menu) {
    menu.classList.add('hidden');
}

function menuClicked() {
    const mainMenu = getMappedElementById('main-menu');

    //hideMenu(saveMenu);
    if (currentMenu) {
        mainMenu.classList.add('partial');
        hideMenu(currentMenu);
        currentMenu = null;
    } else {
        currentMenu = mainMenu;
        showMenu(mainMenu);
    }

    // if (mainMenu.classList.length == 1) {
    //     currentMenu = null;
    //     mainMenu.classList.add('partial');
    // } else {
    //     currentMenu = mainMenu;
    //     showMenu(mainMenu);
    // }
}

function saveMenuClicked() {
    const mainMenu = getMappedElementById('main-menu');
    const saveMenu = getMappedElementById('save-menu');

    currentMenu = saveMenu;
    hideMenu(mainMenu);
    showMenu(saveMenu);
}

function loadClicked() {
    const mainMenu = getMappedElementById('main-menu');
    const loadMenu = getMappedElementById('load-menu');

    currentMenu = loadMenu;
    hideMenu(mainMenu);
    showMenu(loadMenu);
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

    let rowElement = getMappedElementById('cross-highlight-row');
    let colElement = getMappedElementById('cross-highlight-col');

    rowElement.classList.add('cross-highlight');
    colElement.classList.add('cross-highlight');

    const currentRow = getY(centerDivElement);
    const currentCol = getX(centerDivElement);

    rowElement.style.gridArea = `${currentRow + 1} / 1 / ${currentRow + 2} / ${mapWidth + 1}`;
    colElement.style.gridArea = `1 / ${currentCol + 1} / ${mapHeight + 1} / ${currentCol + 2}`;

    let columnLegendTile = getMappedElementById(`col-${currentCol}`);
    let rowLegendTile = getMappedElementById(`row-${currentRow}`);

    columnLegendTile.classList.add('legend-highlight');
    highlightedColumnLegendTile = columnLegendTile;
    rowLegendTile.classList.add('legend-highlight');
    highlightedRowLegendTile = rowLegendTile;
}

function hideCrossHighlightElements() {
    let rowElement = getMappedElementById('cross-highlight-row');
    let colElement = getMappedElementById('cross-highlight-col');

    rowElement.classList.remove('cross-highlight');
    colElement.classList.remove('cross-highlight');

    highlightedColumnLegendTile?.classList.remove('legend-highlight');
    highlightedRowLegendTile?.classList.remove('legend-highlight');
}
