if (document.readyState == "loading") {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready() {
    updateGrid()
    injectTileListeners()
}

function injectTileListeners() {
    var tiles = document.getElementsByClassName("tile")
    for (var tile of tiles) {
        tile.addEventListener('click', function (event) {
            clickedOnTile(event.target)
        })
    }
}

function clickedOnTile(clickedTile) {
    var tileId = clickedTile.id
    var tileType = clickedTile.classList[1]
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/clickTile', true)
    request.send('Tile: ' + tileId + ' (' + tileType + ') has been clicked!')
    console.log('click!', clickedTile)
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
    var width = getMapWidth()
    var height = getMapHeight()
    var mapHeightPercentage = 80
    var mapWidthPercentage = width / height * mapHeightPercentage
    gridStyle.gridTemplateColumns = `repeat(${width}, 1fr)`
    gridStyle.gridTemplateRows = `repeat(${height}, 1fr)`
    gridStyle.height = `${mapHeightPercentage}vh`
    gridStyle.width = `${mapWidthPercentage}vh`
}