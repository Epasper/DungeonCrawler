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
    constructor(partyDiv, standingTileDiv) {
        this.partyDiv = partyDiv;
        this.standingTileDiv = standingTileDiv;
        this.direction = directions.NONE;
        this.isSelected = false;

        if (partyDiv) {
            partyDiv.addEventListener('click', partyClick);
            partyDiv.addEventListener('mouseenter', mouseEnteredPartyDiv);
        }
    }

    exists() {
        debugger;
        console.log(this?.partyDiv);
        return this?.partyDiv;
    }
}

export let party = new Party();


export function addPartyManager(partyTile, standingTile) {
    party = new Party(partyTile, standingTile);
}

function mouseEnteredPartyDiv() {
    tileMouseEntered({ target: party.standingTileDiv }, 'party')
}

async function partyClick({ target: partyDiv }) {     //Destrukturyzacja obiektu. Do funkcji wchodzi obiekt Event i bierzemy z niego Event.target i przypisujemy do partyDiv. 
    //{} oznacza że go destrukturyzujemy i pobieramy jego pole targetm a następnie przypisujemy je do zmiennej heroElement

    console.log(party.isSelected);

    if (party.isSelected) {
        party.isSelected = false;
        party.partyDiv.classList.remove('selected')
        console.log(`Party was deselected`);
    } else {
        party.isSelected = true;
        party.partyDiv.classList.add('selected')
        console.log(`Party was selected`);
    }

    await updateMap();
}