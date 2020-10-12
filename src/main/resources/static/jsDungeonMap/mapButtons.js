import { party } from './partyManager.js';
import { getX, getY, selectedGridTileDiv } from './mapSelection.js'


async function updateSpawnButton() {
    if(!selectedGridTileDiv) return false;

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
    let moveButtonsContainer = document.getElementById('move-ctrl');

    if(!party){
        moveButton.disabled = true;
        moveButtonsContainer.classList.remove('move-container-hover')
        return;
    }
    
    if (party.isSelected) {
        moveButton.disabled = false;
        moveButtonsContainer.classList.add('move-container-hover')
    } else {
        moveButton.disabled = true;
        moveButtonsContainer.classList.remove('move-container-hover')
    }

    //TODO: sprawdzać czy kliknięte docelowe pole nadaje się do postawienia bohatera
}

async function updateDirectionalButtons() {
    if (!party) return;
    
    let directionalButtons = Array.from(document.getElementsByClassName('move-button'));

    const {data: movabilityData} = await axios.get(`http://localhost:8080/movability`);
    console.log('movability: ', movabilityData);

    directionalButtons.forEach(dirBtn => {
        let [dir] = dirBtn.id.split('-').splice(-1);
        dir = dir.toUpperCase();
        if(movabilityData[dir] == false) {        //pobiera z obiektu wartość pola 'dir' - nie poprzez syntax obiekt.pole, ale obiekt['nazwa_pola']
            dirBtn.classList.add('move-button-blocked');
        } else {
            dirBtn.classList.remove('move-button-blocked');
        }
    });
}

export function updateButtons() {
    updateSpawnButton();
    updateMoveButton();
    updateDirectionalButtons();

}

