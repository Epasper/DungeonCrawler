import {updateButtons} from './mapButtons.js'

export let selectedTile

export function makeSelection(tile, grid) {
    let existingSelection = document.getElementById(`selection`)
    if(existingSelection != null){
        console.log('remove!')
        existingSelection.remove()
    } else console.log('create!')
    
    selectedTile = tile
    const x = getX(tile) + 1
    const y = getY(tile) + 1

    const selectionTile = document.createElement(`div`)
    selectionTile.style.gridRow = (`${y}`)
    selectionTile.style.gridColumn = (`${x}`)
    selectionTile.id = 'selection'

    console.log(getX(tile), `---`, getY(tile))

    grid.appendChild(selectionTile)

    selectionTile.addEventListener('click', function (event) {
        clickedOnTile(event.target)
    })
    selectionTile.addEventListener('mouseenter', function (event) {
        var ev = new Event('mouseenter')
        selectedTile.dispatchEvent(ev)
    })
    selectionTile.addEventListener('mouseleave', function (event) {
        var ev = new Event('mouseleave')
        selectedTile.dispatchEvent(ev)
    })

    if (isHeroSpawnable(selectedTile)) {

    }

}

export function getX(tile) {
    var tileId = ``
    tileId = tile.id

    var x = tileId.split(`-`)[0]
    return x*1
}

export function getY(tile) {
    var tileId = ``
    tileId = tile.id

    var y = tileId.split(`-`)[1]
    return y*1
}

function clickedOnTile(clickedElement) {
    var ev = new Event('mouseleave')
    selectedTile.dispatchEvent(ev)

    var tileId = selectedTile.id
    var tileType = selectedTile.classList[1]
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/clickTile', true)
    request.send('Tile: ' + tileId + ' (' + tileType + ') has been de-selected!')

    updateButtons();

    selectedTile = null
    clickedElement.remove();
}

function isHeroSpawnable(selectedTile) {

}