export let idMap = new Map();

export function getChildNodeByIdPartialString(node, searchedString) {
    if (!node.children) return null;

    let children = Array.from(node.children);
    return children.find(node => node.id.includes(searchedString));
}

export function mapUniqueIDsWithTheirElements() {
    if (idMap.size > 0) return;
    let allElementsWithId = document.querySelectorAll('*[id]');

    for (const element of allElementsWithId) {
        idMap.set(element.id, element);
    }
}

export function getMappedElementById(id) {
    if (idMap?.size == 0) mapUniqueIDsWithTheirElements();
    return idMap.get(id);
}

export function makeCorridorsTemoraryVisible() {
    if (party) return;
    const corridorFogDivs = Array.from(document.getElementsByClassName('fog-CORRIDOR'));
    corridorFogDivs.forEach(div => div.style.backgroundColor = 'rgb(50,50,100');
}

export function getXCoord(tile, separator = '-') {
    var tileId = ``
    tileId = tile.id

    var x = tileId.split(separator)[0]
    return x * 1
}

export function getYCoord(tile, separator = '-') {
    var tileId = ``
    tileId = tile.id

    var y = tileId.split(separator)[1]
    return y * 1
}

export function getDivFromBackendTile({ x, y }, distinguisherSign = '-') {
    return getMappedElementById(`${x}${distinguisherSign}${y}`);
}

export function replaceClass(element, oldClassStr, newClassStr) {
    if(!element.classList.contains(oldClassStr)) return;

    const fullClassName = element.className;
    const newClassName = fullClassName.replace(oldClassStr, newClassStr);

    element.className = newClassName;
}

export function getDivFromCoordinateString(str, distinguisherSign = '-') {
    const coordinates = str.split(distinguisherSign);
    const x = coordinates[0];
    const y = coordinates[1];

    return getMappedElementById(`${x}${distinguisherSign}${y}`);
}