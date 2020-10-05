//import * as test from './mapSelection.js'
import {select} from './mapSelection.js'

const SIZE_REF = 90
const mapGrid = document.getElementById(`grid`)
var mapHeight
var mapWidth
var colorBackup

if (document.readyState == "loading") {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready() {
    mapWidth = getMapWidth()
    mapHeight = getMapHeight()

    resizeContainerGrid()
    updateGrid()
    updateTopLegend()
    injectTileListeners()
}

function injectTileListeners() {
    var tiles = document.getElementsByClassName("tile")
    for (var tile of tiles) {
        tile.addEventListener('click', function (event) {
            clickedOnTile(event.target)
        })
    }
    for (var tile of tiles) {
        tile.addEventListener('mouseenter', function (event) {
            mouseEnteredOverTile(event.target)
        })
    }
    for (var tile of tiles) {
        tile.addEventListener('mouseleave', function (event) {
            mouseLeftTheTile(event.target)
        })
    }
}

function clickedOnTile(clickedTile) {
    var tileId = clickedTile.id
    var tileType = clickedTile.classList[1]
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/clickTile', true)
    request.send('Tile: ' + tileId + ' (' + tileType + ') has been selected!')
    console.log('click!', clickedTile)

    select(clickedTile, mapGrid)
}

function mouseEnteredOverTile(hoveredTile) {
    var tileId = ``
    tileId = hoveredTile.id
    var tileType = hoveredTile.classList[1]

    var grid = hoveredTile.parentElement

    var x = tileId.split(`-`)[0]
    var y = tileId.split(`-`)[1]

    var row = getRowOfElements(y)
    var col = getColOfElements(x)

    row[0].style.color = (`rgb(220,220,220)`)
    col[0].style.color = (`rgb(220,220,220)`)
    row[0].style.fontWeight = (`bold`)
    col[0].style.fontWeight = (`bold`)
    row[0].style.fontSize = (`1.5vh`)
    col[0].style.fontSize = (`1.5vh`)

    row.forEach(element => {
        brightness(element, 20)
    });

    col.forEach(element => {
        brightness(element, 20)
    });

    console.log('on!', x, `/`, y)
}

function mouseLeftTheTile(exitedTile) {
    var tileId = ``
    tileId = exitedTile.id

    var x = tileId.split(`-`)[0]
    var y = tileId.split(`-`)[1]

    var row = getRowOfElements(y)
    var col = getColOfElements(x)

    row[0].style.color = (``)
    col[0].style.color = (``)
    row[0].style.fontWeight = (``)
    col[0].style.fontWeight = (``)
    row[0].style.fontSize = (``)
    col[0].style.fontSize = (``)

    row.forEach(element => {
        resetColor(element)
    });

    col.forEach(element => {
        resetColor(element)
    });
}

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

function updateGrid() {
    var grid = document.getElementById('grid')
    var gridStyle = grid.style
    gridStyle.gridTemplateColumns = `repeat(${mapWidth}, 1fr)`
    gridStyle.gridTemplateRows = `repeat(${mapHeight}, 1fr)`
}

function updateTopLegend() {
    var gridStyle = document.getElementById(`coord-top`).style
    gridStyle.gridTemplateColumns = `repeat(${mapWidth}, 1fr)`
}

function resizeContainerGrid() {
    var grid = document.getElementById(`container-grid`)
    var gridStyle = grid.style
    gridStyle.gridTemplateRows = `1fr ${mapHeight}fr`
    gridStyle.gridTemplateColumns = `1fr ${mapWidth}fr`

    var conteinerHeight = mapHeight + 1
    var containerWidth = mapWidth + 1

    gridStyle.width = `${containerWidth / conteinerHeight * SIZE_REF}vh`
}

function getRowOfElements(rowNumber) {
    var row = [document.getElementById(`row-${rowNumber}`)]
    for (var i = 0; i < mapWidth; i++) {
        var tile = document.getElementById(`${i}-${rowNumber}`)
        row.push(tile)
    }
    return row
}

function getColOfElements(colNumber) {
    var col = [document.getElementById(`col-${colNumber}`)]
    for (var i = 0; i < mapHeight; i++) {
        var tile = document.getElementById(`${colNumber}-${i}`)
        col.push(tile)
    }
    return col
}

function brightness(tile, value) {
    var currentColor = window.getComputedStyle(tile).backgroundColor
    colorBackup = currentColor
    var style = tile.style

    var r = 0
    var g = 0
    var b = 0

    r = currentColor.split(`(`)[1].split(`)`)[0].split(',')[0] * 1
    g = currentColor.split(`(`)[1].split(`)`)[0].split(',')[1] * 1
    b = currentColor.split(`(`)[1].split(`)`)[0].split(',')[2] * 1

    r += value
    g += value + 20
    b += value

    style.backgroundColor = `rgb(${r},${g},${b})`
}

function resetColor(tile) {
    tile.style.backgroundColor = ``
}