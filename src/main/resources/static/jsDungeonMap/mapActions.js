import { updateMap } from './dungeonMap.js'
import { directions, party } from './jsMapClasses/partyManager.js'
import { selectedGridTileDiv } from './mapSelection.js'
import { DoorOperator } from './jsMapClasses/doorOperator.js'
import { EncounterOperator } from './jsMapClasses/encounterOperator.js'

import * as utils from './mapUtils.js'

class ActionsManager {
    constructor() {
        this.actionA = async () => { };
        this.actionB = async () => { };
    }

    async performActionA() {
        await this.actionA();
    }

    async performActionB() {
        await this.actionB();
    }
}

const directionalButtons = Array.from(document.getElementsByClassName('move-button'));
let actionsManager = new ActionsManager();
let doorOperator = new DoorOperator();
let encounterOperator = new EncounterOperator();


async function updateSpawnButton() {
    let spawnButton = utils.getMappedElementById('spawn-party-btn');
    // if (utils.getMappedElementById('party')) {
    if (party.isSpawned) {
        spawnButton.style.opacity = '0';
        spawnButton.style.pointerEvents = 'none';
    }

    if (!selectedGridTileDiv) return false;

    let coordX = utils.getXCoord(selectedGridTileDiv)
    let coordY = utils.getYCoord(selectedGridTileDiv)

    //AXIOS method:
    const response = await axios.get(`http://localhost:8080/checkSpawnability?coordX=${coordX}&coordY=${coordY}`);
    let isSpawnable = response.data;

    //let isSpawnable = data;
    let isSelection = (utils.getMappedElementById('selection') != null)

    if (isSpawnable && isSelection) {
        spawnButton.disabled = false;
    } else {
        spawnButton.disabled = true;
    }

    return isSpawnable
}

function isPartySelected() {
    if (!party) return false;
    return party.isSelected;
}

function updateMoveButton() {
    let moveButton = utils.getMappedElementById('move-party-btn');
    let moveButtonsContainer = utils.getMappedElementById('move-group');

    //dodawanie i usuwanie sub-klasy 'move-container-hover' ostylowanej w CSS wyłącza reakcję interfacu, kiedy button "Move Party" jest nieaktywny
    if (isPartySelected()) {
        moveButton.disabled = false;
        moveButtonsContainer.classList.add('move-container-hover')
    } else {
        moveButton.disabled = true;
        moveButtonsContainer.classList.remove('move-container-hover')
    }
}

async function updateDirectionalButtons() {
    if (!isPartySelected()) return;

    const { data: movabilityData } = await axios.get(`http://localhost:8080/movability`);
    //console.log('movability: ', movabilityData);

    directionalButtons.forEach(dirBtn => {
        let [dir] = dirBtn.id.split('-').splice(-1);
        dir = dir.toUpperCase();
        if (movabilityData[dir] == false) {        //pobiera z obiektu wartość pola 'dir' - nie poprzez syntax obiekt.pole, ale obiekt['nazwa_pola']
            dirBtn.classList.add('move-button-blocked');
        } else {
            dirBtn.classList.remove('move-button-blocked');
        }
    });
}

function activateActionMenu() {
    utils.getMappedElementById('action-group').classList.add('active');
}

function deactivateActionMenu() {
    utils.getMappedElementById('action-group').classList.remove('active');
    let btn1 = utils.getMappedElementById('action-btn-1');
    let btn2 = utils.getMappedElementById('action-btn-2');

    btn1.disabled = true;
    btn2.disabled = true;
}

async function updateActionButton() {
    let actionBtn = utils.getMappedElementById('action-btn');
    if (!isPartySelected()) return;

    if (party.direction == directions.NONE) return;

    const { data: pointedBackendTile } = await axios.get(`http://localhost:8080/getPointedTile?dir=${party.direction}`);

    //ACTIONS FOR DOOR
    if (pointedBackendTile.type.includes('DOOR')) {
        actionBtn.disabled = false;
        activateActionMenu();
        provideDoorActions(pointedBackendTile);
        return;
    }

    //ACTIONS FOR ROOM
    const standingTile = party.occupiedTileDiv;
    const classListArr = Array.from(standingTile.classList);
    const isRoom = classListArr.some(cls => cls.includes('ROOM'));
    if (isRoom) {
        actionBtn.disabled = false;
        activateActionMenu();
        provideEncounterActions();
        return;
    }

    actionBtn.disabled = true;
    actionBtn.dataset.actionType = 'action';
    actionBtn.textContent = "Actions";
    deactivateActionMenu();
}

export async function actionAByContext() {
    // doorOperator.actionA();
    await actionsManager.performActionA();
    await updateMap();
}

export async function actionBByContext() {
    // doorOperator.actionB();
    await actionsManager.performActionB();
    await updateMap();
}

async function provideDoorActions(pointedBackendTile) {
    doorOperator.update(pointedBackendTile);

    let action1Btn = utils.getMappedElementById('action-btn-1');
    let action2Btn = utils.getMappedElementById('action-btn-2');

    doorOperator.giveButtonAnAction(action1Btn, 'A');
    doorOperator.giveButtonAnAction(action2Btn, 'B');

    actionsManager.actionA = async () => { await doorOperator.actionA(); };
    actionsManager.actionB = async () => { await doorOperator.actionB(); };

    const actionBtn = utils.getMappedElementById('action-btn');
    actionBtn.textContent = doorOperator.current.actionButtonPrompt;
}

function provideEncounterActions() {
    //const standingBackendTile = party.
    encounterOperator.update(party.occupiedTileDiv, party.encounterStatus);

    let action1Btn = utils.getMappedElementById('action-btn-1');
    let action2Btn = utils.getMappedElementById('action-btn-2');

    encounterOperator.giveButtonAnAction(action1Btn, 'A');
    encounterOperator.giveButtonAnAction(action2Btn, 'B');

    actionsManager.actionA = async () => { await encounterOperator.actionA(); };
    actionsManager.actionB = async () => { await encounterOperator.actionB(); };

    const actionBtn = utils.getMappedElementById('action-btn');
    actionBtn.textContent = encounterOperator.current.actionButtonPrompt;
}

export async function updateButtons() {
    //TODO: Axios umożliwia wysyłanie setu zapytań - można więc zamiast w każdej funkcji poniżej kilka razy odpytywać serwer (axios.all()),
    // to raz zrobić większe odpytanie, a potem wyniki odpytania powrzucać do funkcji updatujących guziki
    updateSpawnButton();
    updateMoveButton();
    updateDirectionalButtons();
    updateActionButton();
}

