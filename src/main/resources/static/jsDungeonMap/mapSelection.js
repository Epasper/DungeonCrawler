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
    const x = getX(targetTile) + 1
    const y = getY(targetTile) + 1

    const selectionDivElement = document.createElement(`div`);
    selectionDivElement.style.gridRow = (`${y}`);
    selectionDivElement.style.gridColumn = (`${x}`);
    selectionDivElement.id = 'selection';
    utils.idMap.set(selectionDivElement.id, selectionDivElement);

    console.log(getX(targetTile), `---`, getY(targetTile))
    mapGrid.appendChild(selectionDivElement)

    selectionDivElement.addEventListener('click', clickedOnSelectionDiv);
    selectionDivElement.addEventListener('mouseenter', mouseEnteredSelectionDiv);
}

function mouseEnteredSelectionDiv() {
    tileMouseEntered({target: selectedGridTileDiv})
}

export function getX(tile, separator = '-') {
    var tileId = ``
    tileId = tile.id

    var x = tileId.split(separator)[0]
    return x * 1
}

export function getY(tile, separator = '-') {
    var tileId = ``
    tileId = tile.id

    var y = tileId.split(separator)[1]
    return y * 1
}

async function clickedOnSelectionDiv({ target: clickedSelectionDiv }) {
    //selectedGridTileDiv.dispatchEvent(new Event('mouseleave'));
    let tileId = selectedGridTileDiv.id;
    let tileType = selectedGridTileDiv.classList[1];

    const coordX = getX(selectedGridTileDiv);
    const coordY = getY(selectedGridTileDiv);
    const message = `Tile: ${tileId} (${tileType}) has been de-selected!`;
    await axios.get(`http://localhost:8080/getClickedTile?coordX=${coordX}&coordY=${coordY}&message=${message}`);

    await updateButtons();
    await deleteSelection(clickedSelectionDiv);

    // selectedGridTileDiv = null;
    // utils.idMap.delete(clickedSelectionDiv.id);
    // clickedSelectionDiv.remove();
}

export async function deleteSelection(selectionDiv = utils.getMappedElementById('selection')) {
    selectedGridTileDiv = null;
    utils.idMap.delete(selectionDiv.id);
    selectionDiv.remove();
}

export function getDivFromBackendTile({ x, y }, distinguisherSign = '-') {

    // return document.getElementById(`${x}-${y}`);
    return utils.getMappedElementById(`${x}${distinguisherSign}${y}`);
}