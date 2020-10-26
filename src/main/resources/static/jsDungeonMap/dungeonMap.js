import { updateButtons } from './mapButtons.js'
import { injectTileListeners, injectButtonsListeners } from './mapEvents.js'
import { draw } from './mapRender.js'
import { adaptGrids } from './mapStyling.js'

export let finished = true;
export let idMap = new Map();

if (document.readyState == "loading") {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

async function ready() {
    // getMapObject()
    mapUniqueIDsWithTheirElements();
    adaptGrids()
    injectTileListeners()
    injectButtonsListeners()
    makeCorridorsTemoraryVisible()
    await updateMap();
    // await draw()
    // updateButtons()
}

function makeCorridorsTemoraryVisible() {
    const corridorFogDivs = Array.from(document.getElementsByClassName('fog-CORRIDOR'));
    corridorFogDivs.forEach(div => div.style.backgroundColor = 'rgb(50,50,100');
}

export async function updateMap() {
    console.log(`================= UPDATE ==============`)
    await draw();
    await updateButtons();
}

// async function getMapObject() {
//     let {data: map} = await axios.get('http://localhost:8080/getMap');
//     console.log(map)
// }

function mapUniqueIDsWithTheirElements() {
    if (idMap.size > 0) return;
    let allElementsWithId = document.querySelectorAll('*[id]');
    // console.log(allElementsWithId);

    for (const element of allElementsWithId) {
        idMap.set(element.id, element);
    }
}

export function getMappedElementById(id) {
    if (idMap?.size == 0) mapUniqueIDsWithTheirElements();
    return idMap.get(id);
}