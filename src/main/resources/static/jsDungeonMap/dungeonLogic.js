import { party } from "./jsMapClasses/partyManager.js";
import * as utils from "./mapUtils.js"


export async function updateLogic() {
    await generateRoomEncounter();
}


async function generateRoomEncounter() {
    if (!party) return;
    const standingTileDiv = party.occupiedTileDiv;

    if (!await isRoomTileEncounterReady(standingTileDiv)) return;

    const coordX = utils.getXCoord(standingTileDiv)
    const coordY = utils.getYCoord(standingTileDiv)
    const {data: roomTilesBackend} = await axios.get(`http://localhost:8080/visitRoom?coordX=${coordX}&coordY=${coordY}`);
    roomTilesBackend.forEach(tile => {
        const tileDiv = utils.getDivFromBackendTile(tile);
        tileDiv.classList.add('visited');
    })
}

async function isRoomTileEncounterReady(tileDiv) {
    const classListArr = Array.from(tileDiv.classList);
    const isRoom = classListArr.some(cls => cls === 'ROOM');

    if (!isRoom) return false;

    const coordX = utils.getXCoord(tileDiv)
    const coordY = utils.getYCoord(tileDiv)
    const { data: wasRoomVisited } = await axios.get(`http://localhost:8080/wasRoomVisited?coordX=${coordX}&coordY=${coordY}`);
    return !wasRoomVisited;
}