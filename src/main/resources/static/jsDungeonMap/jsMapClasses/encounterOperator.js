import * as utils from '../mapUtils.js'

export class EncounterOperator {

    constructor(partyStandingBackendTile, encounterStatus) {
        this.states = [new EncounterAfter(), new EncounterBefore()];

        if (!partyStandingBackendTile) return;
        console.log('constructing encounter operator on: ', partyStandingBackendTile);
        this.standingBackendTile = partyStandingBackendTile;
        this.standingDiv = utils.getDivFromBackendTile(partyStandingBackendTile);
        // const [type] = partyStandingBackendTile.type.split('_').splice(-1);
        const type = encounterStatus;
        const index = this.states.findIndex(encounter => encounter.state == type)
        this.current = this.states[index];
    }

    update(partyStandingBackendTile, encounterStatus) {
        console.log('updating encounter operator for: ', partyStandingBackendTile);
        this.standingBackendTile = partyStandingBackendTile;
        this.standingDiv = utils.getDivFromBackendTile(partyStandingBackendTile);
        //////////this.states = [new DoorOpened(), new DoorClosed(), new DoorLocked()];
        this.states.forEach(encounter => encounter.update(partyStandingBackendTile))

        // const [type] = pointedBackendTile.type.split('_').splice(-1);
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
        this.changeState(await this.current.actionA(this.standingDiv));
    }

    async actionB() {
        console.log('ACTION B from EncounterOperator');
        console.log(this);
        console.log(this.current);
        this.changeState(await this.current.actionB(this.standingDiv));
    }

    giveButtonAnAction(btnElement, actionLetter) {
        btnElement.textContent = this.current[`action${actionLetter}Prompt`];
        btnElement.disabled = this.current[`action${actionLetter}isDisabled`];
    }
}

class Encounter {
    constructor(state, actionA, actionB, actionButtonPrompt, partyStandingBackendTile) {
        this.state = state;
        this.actionAPrompt = actionA;
        this.actionBPrompt = actionB;
        this.actionButtonPrompt = actionButtonPrompt
        this.partyStandingBackendTile = partyStandingBackendTile;
    }

    update(partyStandingBackendTile) {
        this.partyStandingBackendTile = partyStandingBackendTile;
    }
}

class EncounterBefore extends Encounter {
    constructor() {
        super('BEFORE', '⚔️ Engage', 'Sabotage 💣', 'Encounter ready');
        this.actionAisDisabled = true;
        this.actionBisDisabled = true;
    }

    async actionA(standingDiv) {
        console.log('Starting an encounter');
        const newState = 'AFTER';

        standingDiv.classList.add('encountered');

        await generateEncounter(this.partyStandingBackendTile);

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
        this.actionAisDisabled = true;
        this.actionBisDisabled = true;
    }

    async actionA(standingDiv) {
        console.log('Resting');
        return this.state;
    }

    async actionB() {
        console.log('Reseting encounter');
        const newState = 'BEFORE';

        await resetEncounter(this.partyStandingBackendTile);
        return newState;
    }
}