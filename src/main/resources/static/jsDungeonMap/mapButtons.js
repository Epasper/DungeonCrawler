import { selectedHero } from './heroManager.js';
import { getX, getY, selectedTile } from './mapSelection.js'

// async function isSpawnable() {
//     const response = await fetch(`http://localhost:8080/isSpawnable?coordX=${coordX}&coordY=${coordY}`);
//     const data = await response.json;

//     return data;
// }

async function updateSpawnButton() {
    let coordX = getX(selectedTile)
    let coordY = getY(selectedTile)

    //FETCH method
    const response = await fetch(`http://localhost:8080/checkSpawnability?coordX=${coordX}&coordY=${coordY}`);
    const data = await response.json();

    let isSpawnable = data;
    let isSelection = (document.getElementById('selection') != null)
    let spawnButton = document.getElementById('spawn-hero-btn');
    if (isSpawnable && isSelection) {
        spawnButton.disabled = false;
    } else {
        spawnButton.disabled = true;
    }
    

    //XMLHttpRequest method
    // var request = new XMLHttpRequest();
    // request.onload = function(){
    //     console.log(this);
    //     let data = JSON.parse(this.responseText);
    //     console.log(data);
    // };

    // request.open('GET', `http://localhost:8080/checkSpawnability?coordX=${coordX}&coordY=${coordY}`, true)
    // request.send()

    return data
}

function updateMoveButton() {
    let moveButton = document.getElementById('move-hero-btn');
    if (selectedHero) {
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

