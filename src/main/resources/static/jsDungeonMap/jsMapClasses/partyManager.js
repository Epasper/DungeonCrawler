import { updateMap } from "../dungeonMap.js";
import { tileMouseEntered } from '../mapEvents.js'

export const directions = {
    UP: 'UP',
    LEFT: 'LEFT',
    RIGHT: 'RIGHT',
    DOWN: 'DOWN',
    NONE: 'NONE'
}

export class Party {
    constructor(partyDiv, standingTileDiv, standingTileBackend) {
        this.partyDiv = partyDiv;
        this.standingTileDiv = standingTileDiv;
        this.direction = directions.NONE;
        this.isSelected = false;
        this.standingTileBackend = standingTileBackend;

        if (partyDiv) {
            partyDiv.addEventListener('click', partyClick);
            partyDiv.addEventListener('mouseenter', mouseEnteredPartyDiv);
        }
    }

    update(standingTileDiv, standingTileBackend, dir) {
        this.standingTileDiv = standingTileDiv;
        this.standingTileBackend = standingTileBackend;
        this.direction = dir;
    }

    select() {
        this.isSelected = true;
        this.partyDiv.classList.add('selected')
        console.log(`Party was selected`);
    }

    deselect() {
        this.isSelected = false;
        this.partyDiv.classList.remove('selected')
        console.log(`Party was deselected`);
    }

    exists() {
        console.log(this?.partyDiv);
        return this?.partyDiv;
    }
}

export let party = new Party();


export function addPartyManager(partyDiv, standingTileDiv, standingTileBackend) {
    party = new Party(partyDiv, standingTileDiv, standingTileBackend);
}

function mouseEnteredPartyDiv() {
    tileMouseEntered({ target: party.standingTileDiv }, 'party')
}

async function partyClick({ target: partyDiv }) {     //Destrukturyzacja obiektu. Do funkcji wchodzi obiekt Event i bierzemy z niego Event.target i przypisujemy do partyDiv. 
    //{} oznacza że go destrukturyzujemy i pobieramy jego pole targetm a następnie przypisujemy je do zmiennej heroElement

    console.log(party.isSelected);

    if (party.isSelected) {
        party.deselect();
    } else {
        party.select();
    }

    await updateMap();
}