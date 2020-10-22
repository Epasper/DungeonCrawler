import { addParty, party } from './partyManager.js'
import { getDivFromBackendTile } from './mapSelection.js'

let prevFacingDirection = 'none';

export async function draw() {
    //TODO: mapa jest przerysowywana co kliknięcie, ale to powoduje nieładne efekty wizualne na zaznaczonej drużynie
    // może by nie rysować party na nowo z każdym kliknięciem, jeśli nie zmiania się status samej drużyny

    console.log('---DRAWING---')
    drawParty();
    drawFogOfWar();
}

async function drawParty() {
    const response = await axios.get(`http://localhost:8080/getParty`);
    const backendParty = response.data;

    if (!backendParty) return;

    //debugger;
    const [xCoord, yCoord, dir] = [backendParty.occupiedTile.x, backendParty.occupiedTile.y, backendParty.direction];
    console.log(`drawing party: x:${xCoord}/ y:${yCoord} facing: ${dir}`);
    //debugger;

    //if (dir) party.direction = directions[dir];

    let partyTile = document.getElementById(`party`);
    let partyImg = document.getElementById('party-img');
    if (!partyTile) {
        console.log('Creating Party div')
        partyTile = document.createElement(`div`)
        partyTile.id = `party`;
        // const grid = document.getElementById('grid');
        // grid.appendChild(partyTile);

        partyImg = document.createElement('img');
        partyImg.id = 'party-img';
        partyImg.src = '../images/dungeonMap/arrow.svg';
        partyImg.style.height = ('60%');
        partyImg.style.width = ('60%');
        partyTile.appendChild(partyImg);
    }

    //TODO: sprawdzić czy zmieniła się pozycja - jeśli nie - to nic nie robić dalej

    partyTile.style.gridRow = `${yCoord + 1}`;
    partyTile.style.gridColumn = `${xCoord + 1}`;

    // const grid = document.getElementById('grid');
    // grid.appendChild(partyTile);

    if (partyImg) {
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

    const grid = document.getElementById('grid');
    grid.appendChild(partyTile);

    if (!party) addParty(partyTile);
}

async function drawFogOfWar() {
    const response = await axios.get(`http://localhost:8080/getVisibilityData`);
    const visibilityData = response.data;
    let fogDiv;

    //console.log(visibilityData)
    visibilityData.forEach(tile => {
        //console.log(tile);
        fogDiv = getDivFromBackendTile(tile,'x');
        //console.log(fogDiv)
        if (tile.visibility == 0) {
            fogDiv.style.opacity = '';
        } else {
            fogDiv.style.opacity = 1 - tile.visibility;
        }
    });
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