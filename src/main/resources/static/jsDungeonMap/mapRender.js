import { addPartyManager, party } from './jsMapClasses/partyManager.js'
import * as utils from './mapUtils.js'

let prevFacingDirection = 'none';

export async function draw() {
    //TODO: mapa jest przerysowywana co kliknięcie, ale to powoduje nieładne efekty wizualne na zaznaczonej drużynie
    // może by nie rysować party na nowo z każdym kliknięciem, jeśli nie zmiania się status samej drużyny

    console.log('---DRAWING---')
    await drawParty();
    await drawFogOfWar();
}

async function drawParty() {
    const response = await axios.get(`http://localhost:8080/getPartyData`);
    const backendParty = response.data['partyData'];
    const encounterStatus = response.data['encounterStatus'];

    debugger;

    if (!backendParty) return;

    const [xCoord, yCoord, dir] = [backendParty.occupiedTile.x, backendParty.occupiedTile.y, backendParty.direction];
    console.log(`---drawing party: x:${xCoord}/ y:${yCoord} facing: ${dir}---`);

    let partyTile = utils.getMappedElementById(`party`);
    let partyImg = utils.getMappedElementById('party-img');
    if (!partyTile) {
        console.log('Creating Party div')
        partyTile = document.createElement(`div`)
        partyTile.id = `party`;
        utils.idMap.set(partyTile.id, partyTile);

        partyImg = document.createElement('img');
        partyImg.id = 'party-img';
        utils.idMap.set(partyImg.id, partyImg);
        partyImg.src = '../images/dungeonMap/arrow.svg';
        partyImg.style.height = ('60%');
        partyImg.style.width = ('60%');
        partyTile.appendChild(partyImg);
    }

    //TODO: sprawdzić czy zmieniła się pozycja - jeśli nie - to nic nie robić dalej
    // można też wywoływanie drawFogOfWar() dorzucić tutaj - jeśli pozycja party się nie zmieni, to i fog się nie musi rysować na nowo
    // ale uwaga z drzwiami. Prawdopodobnie nie może się zmienić ani drużyna, ani pola wokół niej

    partyTile.style.gridRow = `${yCoord + 1}`;
    partyTile.style.gridColumn = `${xCoord + 1}`;

    const standingTileDiv = utils.getDivFromBackendTile(backendParty.occupiedTile);

    if (partyImg) animatePartyRotation(partyImg, dir);

    const grid = utils.getMappedElementById('grid');
    grid.appendChild(partyTile);

    if (!party.exists()) addPartyManager(partyTile, standingTileDiv, backendParty.occupiedTile); //tworzy Party, jeśli nie istnieje

    if (standingTileDiv) party.update(standingTileDiv, backendParty.occupiedTile, dir);  //updatuje Party
}

function animatePartyRotation(partyImg, dir) {
    let standardAnimationDuration = 0.1;
    const longerAnimationMultiplier = 2.5;

    switch (dir) {
        case 'UP':
            partyImg.style.opacity = ('0.4');
            if (prevFacingDirection == 'RIGHT') {
                partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, right-to-up`
                break;
            } else if (prevFacingDirection == 'DOWN') {
                standardAnimationDuration *= longerAnimationMultiplier;
            }
            partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, up`
            break;
        case 'LEFT':
            partyImg.style.opacity = ('0.4');
            if (prevFacingDirection == 'RIGHT') {
                standardAnimationDuration *= longerAnimationMultiplier;
            }
            partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, left`
            break;
        case 'RIGHT':
            partyImg.style.opacity = ('0.4');
            if (prevFacingDirection == 'UP') {
                partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, up-to-right`
                break;
            } else if (prevFacingDirection == 'LEFT') {
                standardAnimationDuration *= longerAnimationMultiplier;
            }
            partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, right`
            break;
        case 'DOWN':
            partyImg.style.opacity = ('0.4');
            if (prevFacingDirection == 'UP') {
                standardAnimationDuration *= longerAnimationMultiplier;
            }
            partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, down`
            break;
        default:
            partyImg.style.opacity = ('0');
            partyImg.style.transition = 'all 0.5s'
    }
    partyImg.style.animationDuration = `0s, ${standardAnimationDuration}s`
    if (dir) {
        prevFacingDirection = dir;
    }
}

export async function loadVisitedFog() {
    const response = await axios.get(`http://localhost:8080/getSeenTiles`);
    const visitedTiles = response.data;

    visitedTiles.forEach(tile => {
        let tileDiv = utils.getDivFromBackendTile(tile);
        tileDiv.classList.add('seen');
    })
}

export async function loadVisitedRooms() {
    const response = await axios.get(`http://localhost:8080/getVisitedRoomTiles`);
    const visitedTiles = response.data;

    visitedTiles.forEach(tile => {
        let tileDiv = utils.getDivFromBackendTile(tile);
        tileDiv.classList.add('visited');
    })
}

async function drawFogOfWar() {

    // if (!party) return;
    if (!party.exists()) return;

    console.log('=============================== DRAWING FOG ====================================');

    console.log(`---DRAWING FOG START---`)
    const response = await axios.get(`http://localhost:8080/getVisibilityData`, { responseType: 'json' });
    console.log(`---DRAWING FOG after await---`)

    const previouslyShownTiles = response.data['previouslyVisibleTiles'];
    const newlyShownTiles = response.data['currentlyVisibleTiles'];
    const visibleTilesSortedByDistance = response.data['tilesSortedByDistance'];
    const distancesSorted = response.data['distancesSorted'];
    const newlySeenTiles = response.data['newlySeenTiles'];

    //console.log('newly shown tiles: ', newlyShownTiles);

    let i = 0;
    let root = document.documentElement;
    const frameDuration = parseInt(window.getComputedStyle(root).getPropertyValue(`--visibility-frame-duration`));
    
    visibleTilesSortedByDistance.forEach(tile => {
        let tileDiv = utils.getDivFromBackendTile(tile);
        let distance = distancesSorted[i] - partyVisibilityRadius;
        distance = distance < 0 ? 0 : distance;
        let distFunction = distance * 1;
        tileDiv.style.transitionDelay = `${distFunction * frameDuration}ms`
        i++;
    })

    previouslyShownTiles.forEach(tile => {
        let tileDiv = utils.getDivFromBackendTile(tile);
        tileDiv.style.transitionDelay = '';
        tileDiv.style.opacity = '';
        tileDiv.classList.remove('visible');
    })

    newlyShownTiles.forEach(tile => {
        let tileDiv = utils.getDivFromBackendTile(tile);
        tileDiv.style.opacity = tile.visibility;
        tileDiv.classList.add('visible');
    })

    newlySeenTiles.forEach(tile => {
        let tileDiv = utils.getDivFromBackendTile(tile);
        tileDiv.classList.add('seen');
    })

    console.log('=============================== STOP DRAWING FOG ====================================');
}

export async function animateRoomChange(changedTilesData, descendingOrder = false) {
    const keys = Object.keys(changedTilesData);
    const { type: newType } = changedTilesData[0][0];
    const currentType = (newType == 'ROOM') ? 'ROOM_LOCKED' : 'ROOM';
    let roomDiv;
    let cascades = new Map();
    keys.forEach(key => {
        //group here:
        let cascade = changedTilesData[key];
        let divs = [];
        cascade.forEach(backendTile => {
            roomDiv = utils.getDivFromBackendTile(backendTile);
            divs.push(roomDiv);
        });
        cascades.set(key, divs);

    });

    for (let i = 0; i < cascades.size; i++) {
        let counter = i;
        if (descendingOrder) counter = cascades.size - 1 - i;

        setTimeout(function () {
            cascades.get(`${counter}`).forEach(div => {
                div.classList.remove(currentType);
                div.classList.add(newType);
            })
        }, (i + 1) * 100)
    }
}