import {injectTileListeners, injectButtonsListeners} from './mapEvents.js'
import {adaptGrids} from './mapStyling.js'

if (document.readyState == "loading") {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready() {
    adaptGrids()
    injectTileListeners()
    injectButtonsListeners()
}