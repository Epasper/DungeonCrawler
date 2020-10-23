import { getMappedElementById, idMap} from './dungeonMap.js'
import { addParty, party } from './partyManager.js'
import { getDivFromBackendTile, getX, getY } from './mapSelection.js'

let prevFacingDirection = 'none';

export async function draw() {
    //TODO: mapa jest przerysowywana co kliknięcie, ale to powoduje nieładne efekty wizualne na zaznaczonej drużynie
    // może by nie rysować party na nowo z każdym kliknięciem, jeśli nie zmiania się status samej drużyny

    console.log('---DRAWING---')
    await drawParty();
    await drawFogOfWar();
}

async function drawParty() {
    const response = await axios.get(`http://localhost:8080/getParty`);
    const backendParty = response.data;

    if (!backendParty) return;

    //debugger;
    const [xCoord, yCoord, dir] = [backendParty.occupiedTile.x, backendParty.occupiedTile.y, backendParty.direction];
    console.log(`---drawing party: x:${xCoord}/ y:${yCoord} facing: ${dir}---`);
    //debugger;
    

    //if (dir) party.direction = directions[dir];

    let partyTile = getMappedElementById(`party`);
    let partyImg = getMappedElementById('party-img');
    if (!partyTile) {
        console.log('Creating Party div')
        partyTile = document.createElement(`div`)
        partyTile.id = `party`;
        idMap.set(partyTile.id, partyTile);
        // const grid = document.getElementById('grid');
        // grid.appendChild(partyTile);

        partyImg = document.createElement('img');
        partyImg.id = 'party-img';
        idMap.set(partyImg.id, partyImg);
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

    const standingTileDiv = getDivFromBackendTile(backendParty.occupiedTile);
    
    // const grid = document.getElementById('grid');
    // grid.appendChild(partyTile);

    if (partyImg) animatePartyRotation(partyImg, dir);

    const grid = getMappedElementById('grid');
    grid.appendChild(partyTile);

    if (!party) addParty(partyTile, standingTileDiv);
    if (standingTileDiv) party.standingTileDiv = standingTileDiv;
}

function animatePartyRotation(partyImg, dir){
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

async function drawFogOfWar() {

    console.log(`---DRAWING FOG START---`)
    const response = await axios.get(`http://localhost:8080/getVisibilityData`);
    console.log(`---DRAWING FOG after await---`)
    const visibilityData = response.data;
    let fogDiv;
    let visibleFogDivs = [];
    let hiddenCounter = 0;
    let shownCounter = 0;
    //console.log(visibilityData)

    visibilityData.forEach(tile => {
        //console.log(tile);
        fogDiv = getDivFromBackendTile(tile, 'x');
        //console.log(fogDiv)
        if (tile.visibility == 0) {
            fogDiv.style.opacity = '';
            fogDiv.classList.remove('visible');
            hiddenCounter++;
        } else {
            fogDiv.style.opacity = 1 - tile.visibility;
            fogDiv.classList.add('visible');
            visibleFogDivs.push(fogDiv);
            shownCounter++;
        }
    });

    
    //TODO: uncomment below to avoid bugs with drawing fog of war, when events are triggered too quickly (but slows down site)
    // let visibleTiles = Array.from(document.getElementsByClassName('visible'));
    // visibleTiles.forEach(fDiv => {

    //     if (!visibleFogDivs.some(div => div === fDiv)) {
    //         fDiv.style.opacity = '';
    //         fogDiv.classList.remove('visible');
    //     }
    // })
}

export async function animateRoomChange(changedTilesData) {
    const keys = Object.keys(changedTilesData);
    console.log('keys: ', keys);
    const { type: newType } = changedTilesData[0][0];
    const currentType = (newType == 'ROOM') ? 'ROOM_LOCKED' : 'ROOM';
    let roomDiv;
    let cascades = new Map();
    keys.forEach(key => {
        //group here:
        let cascade = changedTilesData[key];
        let divs = [];
        cascade.forEach(backendTile => {
            console.log(backendTile);
            roomDiv = getDivFromBackendTile(backendTile);
            console.log(roomDiv);

            divs.push(roomDiv);
            ////roomDiv.classList.remove(currentType);
            ////roomDiv.classList.add(newType);
        });
        cascades.set(key, divs);

    });

    console.log(cascades)

    for (let i = 0; i < cascades.size; i++) {
        console.log(cascades.get(`${i}`));
        setTimeout(function () {
            cascades.get(`${i}`).forEach(div => {
                div.classList.remove(currentType);
                div.classList.add(newType);
            })
        }, (i + 1) * 100)
    }




    // setInterval(function () {
    //     cascades.get(0).forEach(div => {
    //         div.classList.remove(currentType);
    //         div.classList.add(newType);
    //     })
    //     cascades.delete(0);
    //     if (cascades.size == 0) clearInterval(this);
    // }, 1000)

    setTimeout(function () {

    }, 1000)
    // changedTilesData.forEach(cascade => {
    //     cascade.forEach(backendTile => {
    //         console.log(backendTile);
    //     });
    // });
}