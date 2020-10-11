import { party } from './partyManager.js';
import { getX, getY, selectedGridTileDiv } from './mapSelection.js'


async function updateSpawnButton() {
    let coordX = getX(selectedGridTileDiv)
    let coordY = getY(selectedGridTileDiv)

    //AXIOS method:
    const response = await axios.get(`http://localhost:8080/checkSpawnability?coordX=${coordX}&coordY=${coordY}`);
    let isSpawnable = response.data;

    //let isSpawnable = data;
    let isSelection = (document.getElementById('selection') != null)
    let spawnButton = document.getElementById('spawn-party-btn');
    if (isSpawnable && isSelection) {
        spawnButton.disabled = false;
    } else {
        spawnButton.disabled = true;
    }

    return isSpawnable
}

function updateMoveButton() {
    //TODO: button jest aktywny kiedy kliknięte jest pole, na ktore i tak nie można postawić drużyny
    let moveButton = document.getElementById('move-party-btn');
    
    if(!party){
        moveButton.disabled = true;
        return;
    }
    
    if (party.isSelected) {
        moveButton.disabled = false;
    } else {
        moveButton.disabled = true;
    }

    //TODO: sprawdzać czy kliknięte docelowe pole nadaje się do postawienia bohatera
}

export function updateButtons() {

    updateSpawnButton();
    updateMoveButton();

}

