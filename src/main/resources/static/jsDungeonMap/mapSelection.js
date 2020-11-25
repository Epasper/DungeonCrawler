import { updateButtons } from './mapActions.js'
import { tileMouseEntered } from './mapEvents.js'
import * as utils from './mapUtils.js'

export let selectedGridTileDiv

export function makeSelection(targetTile, mapGrid) {
    let existingSelection = document.getElementById(`selection`)
    if (existingSelection) {
        console.log('remove!')
        existingSelection.remove()
    } else console.log('create!')

    selectedGridTileDiv = targetTile
    const x = utils.getXCoord(targetTile) + 1
    const y = utils.getYCoord(targetTile) + 1

    const selectionDivElement = document.createElement(`div`);
    selectionDivElement.style.gridRow = (`${y}`);
    selectionDivElement.style.gridColumn = (`${x}`);
    selectionDivElement.id = 'selection';
    utils.idMap.set(selectionDivElement.id, selectionDivElement);

    console.log(utils.getXCoord(targetTile), `---`, utils.getXCoord(targetTile))
    mapGrid.appendChild(selectionDivElement)

    selectionDivElement.addEventListener('click', clickedOnSelectionDiv);
    selectionDivElement.addEventListener('mouseenter', mouseEnteredSelectionDiv);
}

function mouseEnteredSelectionDiv() {
    tileMouseEntered({target: selectedGridTileDiv})
}

async function clickedOnSelectionDiv({ target: clickedSelectionDiv }) {
    let tileId = selectedGridTileDiv.id;
    let tileType = selectedGridTileDiv.classList[0];

    const coordX = utils.getXCoord(selectedGridTileDiv);
    const coordY = utils.getXCoord(selectedGridTileDiv);
    const message = `Tile: ${tileId} (${tileType}) has been de-selected!`;
    await axios.get(`http://localhost:8080/getClickedTile?coordX=${coordX}&coordY=${coordY}&message=${message}`);

    await updateButtons();
    await deleteSelection(clickedSelectionDiv);
}

export async function deleteSelection(selectionDiv = utils.getMappedElementById('selection')) {
    selectedGridTileDiv = null;
    utils.idMap.delete(selectionDiv.id);
    selectionDiv.remove();
}