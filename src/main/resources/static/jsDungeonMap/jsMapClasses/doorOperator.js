import * as utils from '../mapUtils.js'
import { animateRoomChange } from '../mapRender.js'

export class DoorOperator {

    constructor(pointedBackendTile) {
        this.states = [new DoorOpened(), new DoorClosed(), new DoorLocked()];

        if (!pointedBackendTile) return;
        console.log('constructing door operator on: ', pointedBackendTile);
        this.pointedBackendTile = pointedBackendTile;
        this.div = utils.getDivFromBackendTile(pointedBackendTile);
        const [type] = pointedBackendTile.type.split('_').splice(-1);
        const index = this.states.findIndex(door => door.state == type)
        this.current = this.states[index];
    }

    update(pointedBackendTile) {
        console.log('updating door operator for: ', pointedBackendTile);
        this.pointedBackendTile = pointedBackendTile;
        this.div = utils.getDivFromBackendTile(pointedBackendTile);
        //////////this.states = [new DoorOpened(), new DoorClosed(), new DoorLocked()];
        this.states.forEach(doorState => doorState.update(pointedBackendTile))

        const [type] = pointedBackendTile.type.split('_').splice(-1);
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

class Door {
    constructor(state, actionA, actionB, actionButtonPrompt, pointedBackendTile) {
        this.state = state;
        this.actionAPrompt = actionA;
        this.actionBPrompt = actionB;
        this.actionButtonPrompt = actionButtonPrompt
        this.pointedBackendTile = pointedBackendTile;
    }

    update(pointedBackendTile) {
        this.pointedBackendTile = pointedBackendTile;
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

        await activateDoorBackend(newState, this.pointedBackendTile)
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

        await activateDoorBackend(newState, this.pointedBackendTile)
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

        await activateDoorBackend(newState, this.pointedBackendTile)
        return newState;
    }

    async actionB(div) {
        console.log('bashing the door');
        return this.state;
    }
}

async function activateDoorBackend(newDoorState, pointedBackendTile, descendingOrder = false) {
    console.log('======================= ACTIVATE DOOR BACKEND ==========================');

    // const pointedTile = doorOperator.pointedTile;
    const url = `http://localhost:8080/activateDoor?coordX=${pointedBackendTile.x}&coordY=${pointedBackendTile.y}&newType=DOOR_${newDoorState}`
    const { data: roomData } = await axios.get(url);
    const changedTilesData = roomData[1];
    if (changedTilesData) await animateRoomChange(changedTilesData, descendingOrder);
    console.log('======================= STOP DOOR BACKEND ==========================');
}