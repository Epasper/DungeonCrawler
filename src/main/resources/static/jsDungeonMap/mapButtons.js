import { directions, party } from './partyManager.js';
import { getX, getY, selectedGridTileDiv, getDivFromBackendTile } from './mapSelection.js'
import { animateRoomChange } from './mapRender.js'


async function updateSpawnButton() {
    let spawnButton = document.getElementById('spawn-party-btn');
    if(document.getElementById('party')) {
        spawnButton.style.opacity = '0';
        spawnButton.style.pointerEvents = 'none';
    }

    if (!selectedGridTileDiv) return false;

    let coordX = getX(selectedGridTileDiv)
    let coordY = getY(selectedGridTileDiv)

    //AXIOS method:
    const response = await axios.get(`http://localhost:8080/checkSpawnability?coordX=${coordX}&coordY=${coordY}`);
    let isSpawnable = response.data;

    //let isSpawnable = data;
    let isSelection = (document.getElementById('selection') != null)
    
    if (isSpawnable && isSelection) {
        spawnButton.disabled = false;
    } else {
        spawnButton.disabled = true;
    }

    return isSpawnable
}

function isPartySelected() {
    if (!party) return false;
    return party.isSelected;
}

function updateMoveButton() {
    let moveButton = document.getElementById('move-party-btn');
    let moveButtonsContainer = document.getElementById('move-ctrl');

    console.log('updateMoveButton - party element: ', document.getElementById('party'));
    // if(!document.getElementById('party')) {
    //     moveButton.style.opacity = '0';
    // } else {
    //     moveButton.style.opacity = '';
    // }

    //dodawanie i usuwanie sub-klasy 'move-container-hover' ostylowanej w CSS wyłącza reakcję interfacu, kiedy button "Move Party" jest nieaktywny
    if (isPartySelected()) {
        moveButton.disabled = false;
        moveButtonsContainer.classList.add('move-container-hover')
    } else {
        moveButton.disabled = true;
        moveButtonsContainer.classList.remove('move-container-hover')
    }
}

async function updateDirectionalButtons() {
    //TODO: buttony się nie updatują po otwarciu drzwi
    if (!isPartySelected()) return;

    let directionalButtons = Array.from(document.getElementsByClassName('move-button'));

    const { data: movabilityData } = await axios.get(`http://localhost:8080/movability`);
    console.log('movability: ', movabilityData);

    directionalButtons.forEach(dirBtn => {
        let [dir] = dirBtn.id.split('-').splice(-1);
        dir = dir.toUpperCase();
        if (movabilityData[dir] == false) {        //pobiera z obiektu wartość pola 'dir' - nie poprzez syntax obiekt.pole, ale obiekt['nazwa_pola']
            dirBtn.classList.add('move-button-blocked');
        } else {
            dirBtn.classList.remove('move-button-blocked');
        }
    });
}

async function updateActionButton() {
    let actionBtn = document.getElementById('action-btn');
    if (!isPartySelected()) return;

    actionBtn.disabled = true;

    //debugger;
    if (party.direction == directions.NONE) return;

    const { data: pointedTile } = await axios.get(`http://localhost:8080/getPointedTile?dir=${party.direction}`);
    console.log('pointed tile: ', pointedTile);

    let pointedDiv = getDivFromBackendTile(pointedTile);
    console.log('pointedDiv: ', pointedDiv);

    pointedDiv = { div: pointedDiv, type: pointedTile.type };
    console.log('pointedDiv: ', pointedDiv);

    if (pointedTile.type.includes('DOOR')) {
        actionBtn.disabled = false;
        let doorOperator = new DoorOperator(pointedDiv);
        console.log('current door status: ', doorOperator.current.state);
        doorOperator.open();
        console.log('current door status: ', doorOperator.current.state);
        console.log('pointed tile_22222222222222: ', pointedTile);
        console.log(pointedTile.x);
        console.log(pointedTile.y);
        const { data: roomData } = await axios.get(`http://localhost:8080/openDoor?coordX=${pointedTile.x}&coordY=${pointedTile.y}
            &newType=DOOR_${doorOperator.current.state}`);
        const changedTilesData = roomData[1];

        console.log('changedTilesData: ' ,changedTilesData)
        if (changedTilesData) animateRoomChange(changedTilesData);
        // doorOperator.open();
        // doorOperator.close();
        // doorOperator.close();
        // doorOperator.close();
    }

}

export function updateButtons() {
    //TODO: Axios umożliwia wysyłanie setu zapytań - można więc zamiast w każdej funkcji poniżej kilka razy odpytywać serwer (axios.all()),
    // to raz zrobić większe odpytanie, a potem wyniki odpytania powrzucać do funkcji updatujących guziki
    updateSpawnButton();
    updateMoveButton();
    updateDirectionalButtons();
    updateActionButton();
}

class DoorOperator {

    constructor(pointedTile) {
        console.log('constructing on: ', pointedTile);
        this.div = pointedTile.div;
        this.states = [new DoorOpened(), new DoorClosed(), new DoorLocked()];
        const [type] = pointedTile.type.split('_').splice(-1);
        const index = this.states.findIndex(door => door.state == type)
        this.current = this.states[index];
    }

    open() {
        this.changeState(this.current.open(this.div));
    }

    close() {
        this.changeState(this.current.close(this.div));
    }

    changeState(targetState) {
        const index = this.states.findIndex(door => door.state == targetState);
        this.current = this.states[index];
    }
}

class Door {
    constructor(state) {
        this.state = state;
    }
}

class DoorOpened extends Door {
    constructor() {
        super('OPENED');
    }

    open(div) {
        console.log('door already opened');
        return this.state;
    }

    close(div) {
        console.log('closing the door');
        const newState = 'CLOSED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)
        return newState;
    }

}

class DoorClosed extends Door {
    constructor() {
        super('CLOSED');
    }

    open(div) {
        console.log('opening the door');
        const newState = 'OPENED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)
        return newState;
    }

    close(div) {
        console.log('locking the door');
        const newState = 'LOCKED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)
        return newState;
    }

}

class DoorLocked extends Door {
    constructor() {
        super('LOCKED');
    }

    open(div) {
        console.log('unlocking the door');
        const newState = 'CLOSED';
        div.classList.remove(`DOOR_${this.state}`)
        div.classList.add(`DOOR_${newState}`)
        return newState;
    }

    close(div) {
        console.log('door are already locked');
        return this.state;
    }
}