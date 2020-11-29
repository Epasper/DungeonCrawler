import { notify } from '../mapMenuActions.js'
import * as utils from '../mapUtils.js'

export class EncounterOperator {

    constructor(occupiedTileDiv, encounterStatus) {
        this.states = [new EncounterAfter(), new EncounterBefore()];
        console.log('constructing encounter operator on: ', occupiedTileDiv);

        this.occupiedTileDiv = occupiedTileDiv;
        const type = encounterStatus;
        const index = this.states.findIndex(encounter => encounter.state == type)
        this.current = this.states[index];
    }

    update(occupiedTileDiv, encounterStatus) {
        console.log('updating encounter operator for: ', encounterStatus);

        this.occupiedTileDiv = occupiedTileDiv;
        this.states.forEach(encounter => encounter.update(occupiedTileDiv));
        const type = encounterStatus;
        const index = this.states.findIndex(encounter => encounter.state == type)
        this.current = this.states[index];
    }

    changeState(targetState) {
        const index = this.states.findIndex(encounter => encounter.state == targetState);
        this.current = this.states[index];
    }
    
    async actionA() {
        console.log('ACTION A from EncounterOperator');
        console.log(this);
        console.log(this.current);
        this.changeState(await this.current.actionA(this.occupiedTileDiv));
    }

    async actionB() {
        console.log('ACTION B from EncounterOperator');
        console.log(this);
        console.log(this.current);
        this.changeState(await this.current.actionB(this.occupiedTileDiv));
    }

    giveButtonAnAction(btnElement, actionLetter) {
        btnElement.textContent = this.current[`action${actionLetter}Prompt`];
        btnElement.disabled = this.current[`action${actionLetter}isDisabled`];
    }
}

class Encounter {
    constructor(state, actionA, actionB, actionButtonPrompt, occupiedTileDiv) {
        this.state = state;
        this.actionAPrompt = actionA;
        this.actionBPrompt = actionB;
        this.actionButtonPrompt = actionButtonPrompt
        this.occupiedTileDiv = occupiedTileDiv;
    }

    update(occupiedTileDiv) {
        this.occupiedTileDiv = occupiedTileDiv;
    }
}

class EncounterBefore extends Encounter {
    constructor() {
        super('BEFORE', '⚔️ Engage', 'Sabotage 💣', 'Encounter ready');
        this.actionAisDisabled = false;
        this.actionBisDisabled = false;
    }

    async actionA(occupiedTileDiv) {
        console.log('Starting an encounter');
        const newState = 'AFTER';

        //occupiedTileDiv.classList.add('encountered');

        //await generateRoomEncounter(this.occupiedTileDiv);
        await changeRoomEncounter(newState, this.occupiedTileDiv);
        await saveBeforeEncounter();

        return newState;
    }

    async actionB() {
        console.log('Sabotaging enemies');
        return this.state;
    }
}

class EncounterAfter extends Encounter {
    constructor() {
        super('AFTER', '💤 Rest', 'Repeat 🔁', 'Encounter finished');
        this.actionAisDisabled = false;
        this.actionBisDisabled = false;
    }

    async actionA(occupiedTileDiv) {
        console.log('Resting');
        return this.state;
    }

    async actionB() {
        console.log('Reseting encounter');
        const newState = 'BEFORE';

        //await resetEncounter(this.occupiedTileDiv);
        await changeRoomEncounter(newState, this.occupiedTileDiv);
        return newState;
    }
}

async function changeRoomEncounter(newEncounterStatus, occupiedTileDiv) {
    const coordX = utils.getXCoord(occupiedTileDiv)
    const coordY = utils.getYCoord(occupiedTileDiv)
    const {data: roomTilesBackend} = await axios.get(`http://localhost:8080/roomEncounter?coordX=${coordX}&coordY=${coordY}&newEncounterState=${newEncounterStatus}`);
    roomTilesBackend.forEach(tile => {
        const tileDiv = utils.getDivFromBackendTile(tile);
        tileDiv.classList.add(newEncounterStatus);
        tileDiv.dataset.encounterStatus = newEncounterStatus;
    })
}

export async function saveBeforeEncounter() {
    const saveSlotIdentifier = 'e';
    await axios.get(`http://localhost:8080/saveMap?saveSlotIdentifier=${saveSlotIdentifier}`);

    notify('Auto save!');
}