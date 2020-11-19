import { updateButtons } from './mapActions.js'
import { injectTileListeners, injectButtonsListeners } from './mapEvents.js'
import { draw, loadVisitedFog } from './mapRender.js'
import { adaptGrids } from './mapStyling.js'
import * as utils from './mapUtils.js'
import * as menus from './mapMenuActions.js'

if (document.readyState == "loading") {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

async function ready() {
    utils.mapUniqueIDsWithTheirElements();
    adaptGrids();
    injectTileListeners();
    injectButtonsListeners();
    menus.updateLoadButtons();
    
    await updateMap();
    utils.makeCorridorsTemoraryVisible();
    await loadVisitedFog();
}

export async function updateMap() {
    console.log(`================= UPDATE ==============`)
    await draw();
    await updateButtons();
}