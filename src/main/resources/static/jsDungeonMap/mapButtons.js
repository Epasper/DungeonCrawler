import { getMappedElementById, updateMap } from './dungeonMap.js'
import { directions, party } from './partyManager.js';
import { getX, getY, selectedGridTileDiv, getDivFromBackendTile } from './mapSelection.js'
import { animateRoomChange } from './mapRender.js'
import { exitSaveDeletion, setInfoForSlotDiv } from './mapEvents.js';

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

class DoorOperator {

    constructor(pointedTile) {
        if (!pointedTile) return;
        console.log('constructing door operator on: ', pointedTile);
        this.pointedTile = pointedTile;
        this.div = getDivFromBackendTile(pointedTile);
        this.states = [new DoorOpened(), new DoorClosed(), new DoorLocked()];
        const [type] = pointedTile.type.split('_').splice(-1);
        const index = this.states.findIndex(door => door.state == type)
        this.current = this.states[index];
    }

    update(pointedTile) {
        console.log('updating door operator for: ', pointedTile);
        this.pointedTile = pointedTile;
        this.div = getDivFromBackendTile(pointedTile);
        this.states = [new DoorOpened(), new DoorClosed(), new DoorLocked()];
        const [type] = pointedTile.type.split('_').splice(-1);
        const index = this.states.findIndex(door => door.state == type)
        this.current = this.states[index];
    }

    async actionA() {
        console.log('ACTION A from DoorOperator');
        console.log(this);
        console.log(this.current);
        this.changeState(await this.current.actionA(this.div));
    }

    async actionB() {
        console.log('ACTION B from DoorOperator');
        console.log(this);
        console.log(this.current);
        this.changeState(await this.current.actionB(this.div));
    }

    changeState(targetState) {
        const index = this.states.findIndex(door => door.state == targetState);
        this.current = this.states[index];
    }

    giveButtonAnAction(btnElement, actionLetter) {
        btnElement.textContent = this.current[`action${actionLetter}Prompt`];
        btnElement.disabled = this.current[`action${actionLetter}isDisabled`];
    }
}

//TODO: emoji do nazw akcji

class Door {
    constructor(state, actionA, actionB, actionButtonPrompt) {
        this.state = state;
        this.actionAPrompt = actionA;
        this.actionBPrompt = actionB;
        this.actionButtonPrompt = actionButtonPrompt
    }
}

class DoorOpened extends Door {
    constructor() {
        super('OPENED', 'Open', 'Close', 'Opened door');
        this.actionAisDisabled = true;
        this.actionBisDisabled = false;
    }

    async actionA(div) {
        console.log('door already opened');
        return this.state;
    }

    async actionB(div) {
        console.log('closing the door');
        const newState = 'CLOSED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)

        await activateDoorBackend(newState)
        return newState;
    }
}

class DoorClosed extends Door {
    constructor() {
        super('CLOSED', 'Open', 'Close', 'Closed door');
        this.actionAisDisabled = false;
        this.actionBisDisabled = true;
    }

    async actionA(div) {
        console.log('opening the door');
        const newState = 'OPENED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)

        await activateDoorBackend(newState)
        return newState;
    }

    async actionB(div) {
        console.log('locking the door');
        const newState = 'LOCKED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)
        return newState;
    }

}

class DoorLocked extends Door {
    constructor() {
        super('LOCKED', '🗝️ Unlock', 'Lockpick 🔓', 'Locked door');
        this.actionAisDisabled = false;
        this.actionBisDisabled = false;
    }

    async actionA(div) {
        console.log('unlocking the door');
        const newState = 'CLOSED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)

        await activateDoorBackend(newState)
        return newState;
    }

    async actionB(div) {
        console.log('bashing the door');
        return this.state;
    }
}

const directionalButtons = Array.from(document.getElementsByClassName('move-button'));
let actionsManager = new ActionsManager();
let doorOperator = new DoorOperator();


async function updateSpawnButton() {
    let spawnButton = getMappedElementById('spawn-party-btn');
    if (getMappedElementById('party')) {
        spawnButton.style.opacity = '0';
        spawnButton.style.pointerEvents = 'none';
    }

    if (!selectedGridTileDiv) return false;

    let coordX = getX(selectedGridTileDiv)
    let coordY = getY(selectedGridTileDiv)

    //AXIOS method:
    const response = await axios.get(`http://localhost:8080/checkSpawnability?coordX=${coordX}&coordY=${coordY}`);
    let isSpawnable = response.data;

    //let isSpawnable = data;
    let isSelection = (getMappedElementById('selection') != null)

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
    let moveButton = getMappedElementById('move-party-btn');
    let moveButtonsContainer = getMappedElementById('move-group');

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
    getMappedElementById('action-group').classList.add('active');
}

function deactivateActionMenu() {
    getMappedElementById('action-group').classList.remove('active');
    let btn1 = getMappedElementById('action-btn-1');
    let btn2 = getMappedElementById('action-btn-2');

    btn1.disabled = true;
    btn2.disabled = true;
}

async function activateDoorBackend(newDoorState, descendingOrder = false) {
    console.log('======================= ACTIVATE DOOR BACKEND ==========================');

    const pointedTile = doorOperator.pointedTile;
    const { data: roomData } = await axios.get(`http://localhost:8080/activateDoor?coordX=${pointedTile.x}&coordY=${pointedTile.y}
    &newType=DOOR_${newDoorState}`);
    const changedTilesData = roomData[1];
    if (changedTilesData) await animateRoomChange(changedTilesData, descendingOrder);
    console.log('======================= STOP DOOR BACKEND ==========================');
}

async function updateActionButton() {
    let actionBtn = getMappedElementById('action-btn');
    if (!isPartySelected()) return;

    if (party.direction == directions.NONE) return;

    const { data: pointedTile } = await axios.get(`http://localhost:8080/getPointedTile?dir=${party.direction}`);

    if (pointedTile.type.includes('DOOR')) {
        actionBtn.disabled = false;
        activateActionMenu();
        provideDoorActions(pointedTile);
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

async function provideDoorActions(pointedTile) {
    // let doorOperator = new DoorOperator(pointedDiv);
    doorOperator.update(pointedTile);

    let action1Btn = getMappedElementById('action-btn-1');
    let action2Btn = getMappedElementById('action-btn-2');

    doorOperator.giveButtonAnAction(action1Btn, 'A');
    doorOperator.giveButtonAnAction(action2Btn, 'B');

    actionsManager.actionA = async () => { await doorOperator.actionA(); };
    actionsManager.actionB = async () => { await doorOperator.actionB(); };

    const actionBtn = getMappedElementById('action-btn');
    actionBtn.textContent = doorOperator.current.actionButtonPrompt;
}

async function updateLoadButton(button) {
    let elementWithSlotInfo;
    if (button.parentElement.dataset.slot) {
        elementWithSlotInfo = button.parentElement;
    } else {
        elementWithSlotInfo = button;
    }

    const slotNumber = elementWithSlotInfo.dataset.slot;
    const { data: slotExists } = await axios.get(`http://localhost:8080/checkIfSaveExists?slotNumber=${slotNumber}`);

    button.disabled = !slotExists;

    const saveSlotDiv = getMappedElementById(`save-${slotNumber}-slot`);
    const loadSlotDiv = getMappedElementById(`load-${slotNumber}-slot`);
    await setInfoForSlotDiv(saveSlotDiv);
    await setInfoForSlotDiv(loadSlotDiv);

    return button.disabled;
}

export async function updateLoadButtons() {
    //TODO: aktualnie wszystkie load-buttony są aktualizowane po kliknięciu w guzik. A wystarczyłoby zaktualizować tylko ten, który akurat zapisano

    const loadButtons = Array.from(document.getElementsByClassName('button-load'));
    let emptySlotsCounter = 0;

    for (const loadButton of loadButtons) {
        if (await updateLoadButton(loadButton)) emptySlotsCounter++;
    }
    await updateLoadButton(getMappedElementById('q-load-btn'));

    console.log('empty slots: ', emptySlotsCounter, ' number of load buttons: ', loadButtons.length)

    const deleteSaveButton = getMappedElementById('save-delete-btn');
    if (emptySlotsCounter == loadButtons.length) {
        exitSaveDeletion();
        deleteSaveButton.disabled = true;
    } else {
        deleteSaveButton.disabled = false;
    }
}

export async function updateButtons() {
    //TODO: Axios umożliwia wysyłanie setu zapytań - można więc zamiast w każdej funkcji poniżej kilka razy odpytywać serwer (axios.all()),
    // to raz zrobić większe odpytanie, a potem wyniki odpytania powrzucać do funkcji updatujących guziki
    updateSpawnButton();
    updateMoveButton();
    updateDirectionalButtons();
    updateActionButton();
}

export function notify(text) {
    const notificationDiv = document.createElement('div');
    const root = document.documentElement;
    const notificationTime = parseInt(window.getComputedStyle(root).getPropertyValue(`--notification-time`));

    
    notificationDiv.innerText = text;
    notificationDiv.classList.add('notification');//, 'hidden');
    
    document.body.insertBefore(notificationDiv, getMappedElementById('toolbar'));

    setTimeout(function () {
        notificationDiv.remove();
    }, notificationTime)


}


