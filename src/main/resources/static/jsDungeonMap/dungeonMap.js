import { updateButtons } from './mapActions.js'
import { injectTileListeners, injectButtonsListeners } from './mapEvents.js'
import { draw, loadVisitedFog, loadVisitedRooms } from './mapRender.js'
import { adaptGrids } from './mapStyling.js'
import {updateLogic } from './dungeonLogic.js'
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
    await loadVisitedRooms();
}

export async function updateMap() {
    console.log(`================= UPDATE ==============`)
    await draw();
    await updateButtons();
    //await updateLogic();
}