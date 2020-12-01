import { updateMap } from "../dungeonMap.js";
import { tileMouseEntered } from '../mapEvents.js'
import * as utils from '../mapUtils.js'

export const directions = {
    UP: 'UP',
    LEFT: 'LEFT',
    RIGHT: 'RIGHT',
    DOWN: 'DOWN',
    NONE: 'NONE'
}

export class Party {
    constructor(occupiedTileBackend, encounterStatus) {
        this.partyDiv = null;
        this.occupiedTileBackend = occupiedTileBackend;
        if (occupiedTileBackend) this.occupiedTileDiv = utils.getDivFromBackendTile(occupiedTileBackend);
        this.direction = directions.NONE;
        this.isSelected = false;
        this.isSpawned = false;
        this.encounterStatus = encounterStatus;
    }

    update(occupiedTileBackend, encounterStatus, dir) {
        this.occupiedTileBackend = occupiedTileBackend;
        this.occupiedTileDiv = utils.getDivFromBackendTile(occupiedTileBackend);
        this.encounterStatus = encounterStatus;
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

    createPartyDiv() {
        console.log('Creating Party div')
    
        const partyDiv = document.createElement(`div`)
        partyDiv.id = `party`;
        utils.idMap.set(partyDiv.id, partyDiv);
    
        const partyImg = document.createElement('img');
        partyImg.id = 'party-img';
        utils.idMap.set(partyImg.id, partyImg);
    
        partyImg.src = '../images/dungeonMap/arrow.svg';
        partyImg.style.height = ('60%');
        partyImg.style.width = ('60%');
        partyDiv.appendChild(partyImg);
    
        this.partyDiv = partyDiv;
        this.partyDiv.addEventListener('click', partyClick);
        this.partyDiv.addEventListener('mouseenter', mouseEnteredPartyDiv);

        // const grid = utils.getMappedElementById('grid');
        // grid.appendChild(partyDiv);
    }
}

export let party = new Party();

// export function addPartyManager(partyDiv, occupiedTileDiv, occupiedTileBackend) {
//     party = new Party(partyDiv, occupiedTileDiv, occupiedTileBackend);
// }

function mouseEnteredPartyDiv() {
    tileMouseEntered({ target: party.occupiedTileDiv }, 'party')
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