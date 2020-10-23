import { update, getMappedElementById } from "./dungeonMap.js";
import { updateButtons } from "./mapButtons.js";
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

export function addParty(partyTile) {
    party = {
        partyTileDiv: partyTile,
        direction: directions.NONE,
        isSelected: false
    }
    partyTile.addEventListener('click', partyClick);
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
    
    await update();

    // updateButtons();
    // await draw();
}