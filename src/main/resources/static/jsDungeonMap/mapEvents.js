import { makeSelection, getX, getY, selectedGridTileDiv, deleteSelection } from './mapSelection.js'
import { mapWidth, mapHeight } from './mapStyling.js'
import { actionAByContext, actionBByContext, notify, updateLoadButtons } from './mapButtons.js'
import { draw } from './mapRender.js'
import { party, directions } from './partyManager.js'
import { finished, getMappedElementById, updateMap } from './dungeonMap.js'

const mapGrid = document.getElementById(`grid`);
let highlightedColumnLegendTile;
let highlightedRowLegendTile;
let currentMenu = null;
let currentButtonAwaitingConfirmation = null;
let confirmTextBackup = '';
let cancelTextBackup = '';
let tooltipTextBackups = new Map();

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

    let qSaveBtn = getMappedElementById('q-save-btn');
    qSaveBtn.addEventListener('click', requestQuickSave);

    let qLoadBtn = getMappedElementById('q-load-btn');
    qLoadBtn.addEventListener('click', requestQuickLoad);

    let backBtn = getMappedElementById('back-btn');
    backBtn.addEventListener('click', backButtonClicked)

    injectSaveMenuListeners();
    injectLoadMenuListeners();
}

function injectSaveMenuListeners() {
    const saveBtn = getMappedElementById('save-btn');
    saveBtn.addEventListener('click', saveMenuClicked);

    const saveButtons = Array.from(document.getElementsByClassName('button-save'));
    saveButtons.forEach(loadBtn => {
        loadBtn.addEventListener('click', confirmableButtonClicked);
    })

    const cancelSaveButtons = Array.from(document.querySelectorAll('.save-slot .button-cancel'));
    cancelSaveButtons.forEach(cancelSaveBtn => {
        cancelSaveBtn.addEventListener('click', exitConfirmation);
    })

    const confirmSaveButtons = Array.from(document.querySelectorAll('.save-slot .button-confirm'));
    confirmSaveButtons.forEach(confirmSaveBtn => {
        confirmSaveBtn.addEventListener('click', saveButtonConfirmed);
    })

    const backBtn = getMappedElementById('save-back-btn');
    backBtn.addEventListener('click', backButtonClicked)

    const delBtn = getMappedElementById('save-delete-btn');
    delBtn.addEventListener('click', prepareSaveDeletion);
}

function prepareSaveDeletion({ target: deleteButton }) {
    deleteButton.style.backgroundColor = 'red';
    deleteButton.dataset.status = "active";
    
    exitConfirmation();

    const saveSlotButtons = Array.from(document.getElementsByClassName('button-save'));
    saveSlotButtons.forEach(saveButton => {
        const currentSlotDiv = saveButton.parentElement;
        const currentSlotNumber = currentSlotDiv.dataset.slot;
        saveButton.classList.add('button-delete');

        const confirmButton = getChildNodeByIdPartialString(currentSlotDiv, 'confirm');
        confirmButton.removeEventListener('click', saveButtonConfirmed);
        confirmButton.addEventListener('click', saveButtonDelete);
        confirmTextBackup = confirmButton.dataset.text;
        confirmButton.dataset.text = ' Confirm ';
        confirmButton.innerText = '🗑️';

        const cancelButton = getChildNodeByIdPartialString(currentSlotDiv, 'cancel');
        cancelTextBackup = cancelButton.dataset.text;
        cancelButton.dataset.text = ' Keep ';
        cancelButton.innerText = '✅';

        //const labelsDiv = getChildNodeByIdPartialString(currentSlotDiv, 'labels');
        // const tooltipDiv = getChildNodeByIdPartialString(labelsDiv, 'tooltip');
        const tooltipDiv = getMappedElementById(`save-${currentSlotNumber}-tooltip`)
        const p = tooltipDiv.querySelector('p');
        tooltipTextBackups.set(tooltipDiv.id, p.innerText);
        p.innerText = `Delete save file from ${saveButton.innerText}`;
        
        saveButton.disabled = getMappedElementById(`load-${currentSlotNumber}-btn`).disabled;
    })

    deleteButton.removeEventListener('click', prepareSaveDeletion);
    deleteButton.addEventListener('click', exitSaveDeletion);
}

export function exitSaveDeletion() {
    const deleteButton = getMappedElementById('save-delete-btn');
    const delBtnStatus = deleteButton.dataset.status;
    if (delBtnStatus != "active") return;

    exitConfirmation();

    deleteButton.style.backgroundColor = '';
    deleteButton.dataset.status = "inactive";

    const saveSlotButtons = Array.from(document.getElementsByClassName('button-save'));
    saveSlotButtons.forEach(saveButton => {
        const currentSlotDiv = saveButton.parentElement;
        const currentSlotNumber = currentSlotDiv.dataset.slot;
        saveButton.classList.remove('button-delete');
        saveButton.disabled = false;

        const confirmButton = getChildNodeByIdPartialString(currentSlotDiv, 'confirm');
        confirmButton.removeEventListener('click', saveButtonDelete);
        confirmButton.addEventListener('click', saveButtonConfirmed);
        confirmButton.dataset.text = confirmTextBackup;
        confirmButton.innerText = '✔️';

        const cancelButton = getChildNodeByIdPartialString(currentSlotDiv, 'cancel');
        cancelButton.dataset.text = cancelTextBackup;
        cancelButton.innerText = '❌';

        // const labelsDiv = getChildNodeByIdPartialString(currentSlotDiv, 'labels');
        // const tooltipDiv = getChildNodeByIdPartialString(labelsDiv, 'tooltip');
        const tooltipDiv = getMappedElementById(`save-${currentSlotNumber}-tooltip`)
        const p = tooltipDiv.querySelector('p');
        p.innerText = tooltipTextBackups.get(tooltipDiv.id);
        tooltipTextBackups.delete(tooltipDiv.id);
    })
    confirmTextBackup = '';
    cancelTextBackup = '';
    deleteButton.removeEventListener('click', exitSaveDeletion);
    deleteButton.addEventListener('click', prepareSaveDeletion);
}

function injectLoadMenuListeners() {
    const loadBtn = getMappedElementById('load-btn');
    loadBtn.addEventListener('click', loadClicked);

    const loadButtons = Array.from(document.getElementsByClassName('button-load'));
    loadButtons.forEach(loadBtn => {
        //loadBtn.addEventListener('click', loadButtonClicked);
        loadBtn.addEventListener('click', confirmableButtonClicked);
    })

    const cancelLoadButtons = Array.from(document.querySelectorAll('.load-slot .button-cancel'));
    cancelLoadButtons.forEach(cancelLoadBtn => {
        cancelLoadBtn.addEventListener('click', exitConfirmation);
    })

    const confirmLoadButtons = Array.from(document.querySelectorAll('.load-slot .button-confirm'));
    confirmLoadButtons.forEach(confirmLoadBtn => {
        confirmLoadBtn.addEventListener('click', loadButtonConfirmed);
    })

    const backBtn = getMappedElementById('load-back-btn');
    backBtn.addEventListener('click', backButtonClicked)
}

function getChildNodeByIdPartialString(node, searchedString) {
    if (!node.children) return null;

    let children = Array.from(node.children);
    return children.find(node => node.id.includes(searchedString));
}

function getActiveTooltip() {
    if (!currentButtonAwaitingConfirmation) return null;

    const currentSlotDiv = currentButtonAwaitingConfirmation.parentElement;
    const currentSlotLabels = getChildNodeByIdPartialString(currentSlotDiv, 'labels');

    return getChildNodeByIdPartialString(currentSlotLabels, 'tooltip');
}

function showTooltip(tooltipText = 'default') {
    if (!currentButtonAwaitingConfirmation) return;
    const tooltipDiv = getActiveTooltip();

    if (tooltipText !== 'default') {
        const p = tooltipDiv.querySelector('p');
        p.innerText = tooltipText;
    }
    tooltipDiv.classList.remove('hidden');
}

function hideTooltip() {
    if (!currentButtonAwaitingConfirmation) return;
    const tooltipDiv = getActiveTooltip();

    tooltipDiv.classList.add('hidden');
}

function showConfirmationButtons(targetButton) {
    currentButtonAwaitingConfirmation = targetButton;
    targetButton.classList.add('button-await-confirmation');
}

function exitConfirmation() {
    if (!currentButtonAwaitingConfirmation) return;
    debugger;
    const currentSlotDiv = currentButtonAwaitingConfirmation.parentElement;
    let cancelButton = getChildNodeByIdPartialString(currentSlotDiv, 'cancel');

    const cancelledBtnId = cancelButton.dataset.cancelling;
    const cancelledButton = getMappedElementById(cancelledBtnId);
    cancelledButton.classList.remove('button-await-confirmation');

    hideTooltip();

    currentButtonAwaitingConfirmation = null;
}

async function saveButtonClicked({ target: saveSlotBtn }) {
    const saveSlotNumber = saveSlotBtn.dataset.slot;
    console.log('save: ', saveSlotBtn, 'number: ', saveSlotNumber)
    await axios.get(`http://localhost:8080/saveMap?saveSlotNumber=${saveSlotNumber}`);

    updateLoadButtons();
}

async function saveButtonConfirmed({ target: saveSlotBtn }) {
    const saveSlotNumber = saveSlotBtn.parentElement.dataset.slot;
    console.log('save: ', saveSlotBtn, 'number: ', saveSlotNumber)
    await axios.get(`http://localhost:8080/saveMap?saveSlotNumber=${saveSlotNumber}`);

    notify('Save successful!');
    updateLoadButtons();
    exitConfirmation();
}

async function saveButtonDelete({ target: clickedConfirmButton }) {
    const currentSlotDiv = clickedConfirmButton.parentElement;
    const saveSlotNumber = currentSlotDiv.dataset.slot;
    console.log('delete slot number: ', saveSlotNumber);

    await axios.get(`http://localhost:8080/deleteSave?saveSlotNumber=${saveSlotNumber}`);

    const saveButton = getMappedElementById(`save-${saveSlotNumber}-btn`);
    saveButton.disabled = true;

    notify('Save deleted!');
    updateLoadButtons();
    exitConfirmation();
}

function confirmableButtonClicked({ target: clickedButton }) {
    exitConfirmation();
    showConfirmationButtons(clickedButton);
    showTooltip();
}

function loadButtonClicked({ target: loadSlotBtn }) {
    exitConfirmation();
    showConfirmationButtons(loadSlotBtn);
    showTooltip();
}

async function loadButtonConfirmed({ target: loadSlotBtn }) {
    const loadSlotNumber = loadSlotBtn.parentElement.dataset.slot;
    console.log('load: ', loadSlotBtn, 'number: ', loadSlotNumber);

    window.location.replace(`http://localhost:8080/loadMap?loadSlotNumber=${loadSlotNumber}`);
}

function backButtonClicked() {
    debugger;

    exitSaveDeletion();
    exitConfirmation();
    hideMenu(currentMenu);

    if (currentMenu === getMappedElementById('main-menu')) {
        currentMenu = null;
    } else {
        showMenu(getMappedElementById('main-menu'));
    }
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
    //saveButtonClicked({target: getMappedElementById('save-0-btn')});
    await axios.get(`http://localhost:8080/saveMap?saveSlotNumber=${0}`);
    updateLoadButtons();
}

async function requestQuickLoad() {
    //loadButtonClicked({target: getMappedElementById('load-0-btn')});
    window.location.replace(`http://localhost:8080/loadMap?loadSlotNumber=${0}`);
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
    currentMenu = menu;
}

function hideMenu(menu) {
    menu.classList.add('hidden');
}

function menuClicked() {
    const mainMenu = getMappedElementById('main-menu');

    //hideMenu(saveMenu);
    if (currentMenu) {
        exitSaveDeletion();
        exitConfirmation();
        mainMenu.classList.add('partial');
        hideMenu(currentMenu);
        currentMenu = null;
    } else {
        //currentMenu = mainMenu;
        showMenu(mainMenu);
    }
}

function saveMenuClicked() {
    const mainMenu = getMappedElementById('main-menu');
    const saveMenu = getMappedElementById('save-menu');

    //currentMenu = saveMenu;
    hideMenu(mainMenu);
    showMenu(saveMenu);
}

function loadClicked() {
    const mainMenu = getMappedElementById('main-menu');
    const loadMenu = getMappedElementById('load-menu');

    //currentMenu = loadMenu;
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
