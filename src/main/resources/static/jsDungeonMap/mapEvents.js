import {makeSelection, getX, getY, selectedTile} from './mapSelection.js'
import {mapWidth, mapHeight} from './mapStyling.js'
import {updateButtons} from './mapButtons.js'
import {draw} from './mapRender.js'
import { selectedHero } from './heroManager.js'

const mapGrid = document.getElementById(`grid`)

export function injectTileListeners() {
    var tiles = document.getElementsByClassName("tile")
    for (var tile of tiles) {
        tile.addEventListener('click', function (event) {
            tileClicked(event.target)
        })
    }
    for (var tile of tiles) {
        tile.addEventListener('mouseenter', function (event) {
            tileMouseEntered(event.target)
        })
    }
    for (var tile of tiles) {
        tile.addEventListener('mouseleave', function (event) {
            tileMouseLeft(event.target)
        })
    }
}

export function injectButtonsListeners() {
    let spawnBtn = document.getElementById('spawn-hero-btn');
    //console.log('spawn injected')
    spawnBtn.addEventListener('click', spawnHero);
    
    let moveBtn = document.getElementById('move-hero-btn');
    moveBtn.addEventListener('click', moveHero);
}

function spawnHero() {
    let coordX = getX(selectedTile)
    let coordY = getY(selectedTile)
    //console.log('spawn event')

    //XMLHttpRequest method
    var request = new XMLHttpRequest();
    request.onload = function(){
        let data = JSON.parse(this.responseText);
        ////console.log(data);
    };

    request.open('GET', `http://localhost:8080/spawnHero?coordX=${coordX}&coordY=${coordY}`, true)
    request.send()

    updateButtons();
    draw();
}

function moveHero() {
    let coordX = getX(selectedTile);
    let coordY = getY(selectedTile);
    let [heroId] = selectedHero.id.split('-').slice(-1); //slice (-1) zwroci tablicę o długosci 1 elementu od końca

    var request = new XMLHttpRequest();
    request.open('GET', `http://localhost:8080/moveHero?coordX=${coordX}&coordY=${coordY}&id=${heroId}`, true)
    request.send()

    updateButtons();
    draw();

}

function tileClicked(clickedTile) {
    var tileId = clickedTile.id
    var tileType = clickedTile.classList[1]
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/clickTile', true)
    request.send('Tile: ' + tileId + ' (' + tileType + ') has been selected!')
    console.log('click!', clickedTile)

    
    request.open('POST', 'http://localhost:8080/getClickedTile', true)
    request.setRequestHeader('Content-Type', 'application/json');

    let coordinates = {
        x: getX(clickedTile),
        y: getY(clickedTile)
    }
    const coordinatesJSON = JSON.stringify(coordinates)
    request.send(coordinatesJSON)

    makeSelection(clickedTile, mapGrid)

    updateButtons()
    draw()
}

function tileMouseEntered(hoveredTile) {
    var tileId = ``
    tileId = hoveredTile.id

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
        brightness(element, 10)
    });

    col.forEach(element => {
        brightness(element, 10)
    });

    //console.log('on!', x, `/`, y)
}

function tileMouseLeft(exitedTile) {
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
    var style = tile.style

    var r = 0
    var g = 0
    var b = 0

    r = currentColor.split(`(`)[1].split(`)`)[0].split(',')[0] * 1
    g = currentColor.split(`(`)[1].split(`)`)[0].split(',')[1] * 1
    b = currentColor.split(`(`)[1].split(`)`)[0].split(',')[2] * 1

    r += value
    g += value + 10
    b += value

    style.backgroundColor = `rgb(${r},${g},${b})`
}

function resetColor(tile) {
    tile.style.backgroundColor = ``
}