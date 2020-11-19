import { updateMap } from "./dungeonMap.js";
import { tileMouseEntered } from './mapEvents.js'
import { updateButtons } from "./mapActions.js";
import { draw } from "./mapRender.js";

export const directions = {
    UP: 'UP',
    LEFT: 'LEFT',
    RIGHT: 'RIGHT',
    DOWN: 'DOWN',
    NONE: 'NONE'
}

//let heroes = [];
export let party;

export function addParty(partyTile, standingTile) {
    party = {
        partyTileDiv: partyTile,
        standingTileDiv: standingTile,
        direction: directions.NONE,
        isSelected: false
    }
    partyTile.addEventListener('click', partyClick);
    partyTile.addEventListener('mouseenter', mouseEnteredPartyDiv);

}

function mouseEnteredPartyDiv() {
    tileMouseEntered({target: party.standingTileDiv}, 'party')
}



async function partyClick({ target: partyTileDiv }) {     //Destrukturyzacja obiektu. Do funkcji wchodzi obiekt Event i bierzemy z niego Event.target i przypisujemy do partyTileDiv. 
    //{} oznacza że go destrukturyzujemy i pobieramy jego pole targetm a następnie przypisujemy je do zmiennej heroElement

    console.log(party.isSelected);
    
    if (party.isSelected) {
        party.isSelected = false;
        party.partyTileDiv.classList.remove('selected')
        console.log(`Party was deselected`);
    } else {
        party.isSelected = true;
        party.partyTileDiv.classList.add('selected')
        console.log(`Party was selected`);
    }
    
    await updateMap();

    // updateButtons();
    // await draw();
}