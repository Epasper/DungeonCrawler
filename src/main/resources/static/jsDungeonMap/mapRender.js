import { addParty, party } from './partyManager.js'

let prevFacingDirection = 'none';

export function draw() {
    //TODO: mapa jest przerysowywana co kliknięcie, ale to powoduje nieładne efekty wizualne na zaznaczonej drużynie
    // może by nie rysować party na nowo z każdym kliknięciem, jeśli nie zmiania się status samej drużyny

    console.log('---DRAWING---')
    drawParty();
}

async function drawParty() {
    const response = await axios.get(`http://localhost:8080/getParty`);
    const backendParty = response.data;

    if (!backendParty) return;

    const [xCoord, yCoord, direction] = [backendParty.occupiedTile.x, backendParty.occupiedTile.y, backendParty.direction];
    console.log(`drawing party: x:${xCoord}/ y:${yCoord} facing: ${direction}`);
    //debugger;

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

    if(partyImg){
        let standardAnimationDuration = 0.1;
        const longerAnimationMultiplier = 2.5;
        
        switch (direction) {
            case 'UP':
                partyImg.style.opacity = ('0.4');
                if(prevFacingDirection == 'RIGHT') {
                    partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, right-to-up`
                    break;
                } else if(prevFacingDirection == 'DOWN') {
                    standardAnimationDuration *= longerAnimationMultiplier;
                }
                partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, up`
                break;
            case 'LEFT':
                partyImg.style.opacity = ('0.4');
                if(prevFacingDirection == 'RIGHT') {
                    standardAnimationDuration *= longerAnimationMultiplier;
                }
                partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, left`
                break;
            case 'RIGHT':
                partyImg.style.opacity = ('0.4');
                if(prevFacingDirection == 'UP') {
                    partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, up-to-right`
                    break;
                } else if (prevFacingDirection == 'LEFT') {
                    standardAnimationDuration *= longerAnimationMultiplier;
                }
                partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, right`
                break;
            case 'DOWN':
                partyImg.style.opacity = ('0.4');
                if(prevFacingDirection == 'UP') {
                    standardAnimationDuration *= longerAnimationMultiplier;
                }
                partyImg.style.animationName = `${prevFacingDirection.toLowerCase()}, down`
                break;
            default:
                partyImg.style.opacity = ('0');
                partyImg.style.transition = 'all 0.5s'
        }
        partyImg.style.animationDuration = `0s, ${standardAnimationDuration}s`
        if(direction){
            prevFacingDirection = direction;
        }
    }

    const grid = document.getElementById('grid');
    grid.appendChild(partyTile);

    if (!party) addParty(partyTile);
}