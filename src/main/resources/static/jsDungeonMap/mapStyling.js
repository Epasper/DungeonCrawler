const SIZE_REF = 80

export let mapHeight = getMapHeight()
export let mapWidth = getMapWidth()

function getMapWidth() {
    var width = 0
    var checkedTile

    do {
        checkedTile = document.getElementById(width + '-0')
        width++
    } while (checkedTile != null)
    return width - 1
}

function getMapHeight() {
    var height = 0
    var checkedTile

    do {
        checkedTile = document.getElementById('0-' + height)
        height++
    } while (checkedTile != null)
    return height - 1
}

function adaptMapAndFogGrids() {
    var grid = document.getElementById('grid');
    // var gridFog = document.getElementById(`fog-grid`);
    var gridStyle = grid.style;
    // var gridFogStyle = gridFog.style;

    gridStyle.gridTemplateColumns = `repeat(${mapWidth}, 1fr)`;
    // gridFogStyle.gridTemplateColumns = `repeat(${mapWidth}, 1fr)`;
    gridStyle.gridTemplateRows = `repeat(${mapHeight}, 1fr)`;
    // gridFogStyle.gridTemplateRows = `repeat(${mapHeight}, 1fr)`;
}

function adaptTopLegend() {
    var gridStyle = document.getElementById(`coord-top`).style
    gridStyle.gridTemplateColumns = `repeat(${mapWidth}, 1fr)`
}

function adaptSideLegend() {
    var gridStyle = document.getElementById(`coord-side`).style
    gridStyle.gridTemplateRows = `repeat(${mapHeight}, 1fr)`
}

export function adaptGrids()
{
    adaptContainerGrid();
    adaptMapAndFogGrids();
    adaptTopLegend();
    adaptSideLegend();
}

function adaptContainerGrid() {
    var grid = document.getElementById(`container-grid`)
    var gridStyle = grid.style
    gridStyle.gridTemplateRows = `1fr ${mapHeight}fr`
    gridStyle.gridTemplateColumns = `1fr ${mapWidth}fr`

    var conteinerHeight = mapHeight + 1
    var containerWidth = mapWidth + 1

    gridStyle.height = `${SIZE_REF}vh`
    gridStyle.width = `${containerWidth / conteinerHeight * SIZE_REF}vh`
}