import { updateButtons } from './mapButtons.js'
import {injectTileListeners, injectButtonsListeners} from './mapEvents.js'
import { draw } from './mapRender.js'
import {adaptGrids} from './mapStyling.js'

if (document.readyState == "loading") {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready() {
    // getMapObject()
    adaptGrids()
    injectTileListeners()
    injectButtonsListeners()
    draw()
    updateButtons()
}

// async function getMapObject() {
//     let {data: map} = await axios.get('http://localhost:8080/getMap');
//     console.log(map)
// }