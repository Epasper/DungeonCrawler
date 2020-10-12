import { addParty, party } from './partyManager.js'

export function draw() {
    //TODO: mapa jest przerysowywana co kliknięcie, ale to powoduje nieładne efekty wizualne na zaznaczonej drużynie
    // może by nie rysować party na nowo z każdym kliknięciem, jeśli nie zmiania się status samej drużyny
    
    console.log('---DRAWING---')
    drawParty();
}

async function drawParty() {
    const response = await axios.get(`http://localhost:8080/getParty`);
    const backendParty = response.data;

    //debugger;

    if (!backendParty) return;

    const [xCoord, yCoord] = [backendParty.occupiedTile.x, backendParty.occupiedTile.y];
    console.log(`drawing party: x:${xCoord} y:${yCoord}`);

    let partyTile = document.getElementById(`party`);
    if (!partyTile) {
        console.log('Creating Party div')
        partyTile = document.createElement(`div`)
        partyTile.id = `party`;
    }

    //TODO: sprawdzić czy zmieniła się pozycja - jeśli nie - to nic nie robić dalej

    partyTile.style.gridRow = `${yCoord + 1}`;
    partyTile.style.gridColumn = `${xCoord + 1}`;
    
    const grid = document.getElementById('grid');
    grid.appendChild(partyTile);

    if (!party) addParty(partyTile);
}