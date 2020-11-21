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