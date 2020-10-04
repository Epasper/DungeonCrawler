// setTimeout(function() {
//   location.reload();
// }, 500);

if (document.readyState == "loading") {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready() {
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
    if (clickedTile.style.backgroundColor == ``)
    {
        clickedTile.style.backgroundColor = `#800000`
    }
    else
    {
        clickedTile.style.backgroundColor = ``
    }
    
}