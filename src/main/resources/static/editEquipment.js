let draggedFromId;
let currentlySelectedEquipment;
let draggedFromElement;

const emptySlot = {
    name: "NO EQUIPMENT",
    weight: 0,
    price: 0,
    classRestrictions: null,
    imageLink: "../images/null"
}

function initialize(){
    currentHeroBackpack.armsSlot = currentHeroBackpack.armsSlot || emptySlot;
    currentHeroBackpack.chestSlot = currentHeroBackpack.chestSlot || emptySlot;
    currentHeroBackpack.feetSlot = currentHeroBackpack.feetSlot || emptySlot;
    currentHeroBackpack.headSlot = currentHeroBackpack.headSlot || emptySlot;
    currentHeroBackpack.leftHandSlot = currentHeroBackpack.leftHandSlot || emptySlot;
    currentHeroBackpack.rightHandSlot = currentHeroBackpack.rightHandSlot || emptySlot;
}

function saveAndExit() {
    let request = new XMLHttpRequest();
    const url = `http://localhost:8080/editEquipment`
    request.open("POST", url, true);
    request.setRequestHeader('Content-Type', 'application/json');
    const backpackJSON = JSON.stringify(currentHeroBackpack);
    request.send(backpackJSON);
}

function allowDrop(event) {
    event.preventDefault();
}

function drag(event) {
    const currentId = event.target.id;
    if (defineDraggedFromEmptySlot(currentId)) {
        draggedFromId = null;
        currentlySelectedEquipment = null;
        draggedFromElement = null;
        return;
    }
    draggedFromElement = document.getElementById(currentId);
    draggedFromId = currentId;
    if (currentId.includes('baggage')) {
        const baggageNumber = currentId.substring(8);
        currentlySelectedEquipment = currentHeroBackpack.baggage[parseInt(baggageNumber)];
    } else {
        currentlySelectedEquipment = currentHeroBackpack[currentId];
    }
}
function drop(event) {
    const currentId = event.target.id;
    if (!draggedFromElement || currentId === draggedFromId || !isDropValid(currentId)) {
        draggedFromId = null;
        currentlySelectedEquipment = null;
        draggedFromElement = null;
        return;
    }
    let draggedToElement = document.getElementById(currentId);
    draggedToElement.src = draggedFromElement.src;
    draggedFromElement.src = defineEmptyImageSrc(draggedFromId);
    if (currentId.includes('baggage')) {
        const baggageNumber = currentId.substring(8);
        currentHeroBackpack.baggage[parseInt(baggageNumber)] = currentlySelectedEquipment;
    } else {
        currentHeroBackpack[currentId] = currentlySelectedEquipment;
    }
    if (draggedFromId?.includes('baggage')) {
        const baggageNumber = draggedFromId.substring(8);
        currentHeroBackpack.baggage[parseInt(baggageNumber)] = emptySlot;
    } else {
        currentHeroBackpack[draggedFromId] = emptySlot;
    }
    currentlySelectedEquipment = null;
    draggedFromId = null;
    event.preventDefault();
}

function isDropValid(currentId) {
    console.log(currentId)
    console.log(currentlySelectedEquipment)
    let draggedToElement = document.getElementById(currentId);
    if (currentId.includes('baggage')) {
        const baggageNumber = currentId.substring(8);
        if (currentHeroBackpack.baggage[parseInt(baggageNumber)].name === "NO EQUIPMENT"){
            return true;
        } else {
            return false;
        }
    }
    if (!currentlySelectedEquipment) return false;
    if (currentId.toLowerCase().includes(currentlySelectedEquipment.occupyingSlot.replace(/_/g, "").toLowerCase())){
        return true;
    } else {
        return false;
    }
}

function defineDraggedFromEmptySlot(currentId) {
    if (!currentId.includes('baggage') && !currentHeroBackpack[currentId]) return true;
    if (currentId.includes('baggage')) {
        const baggageNumber = currentId.substring(8);
        if (currentHeroBackpack.baggage[parseInt(baggageNumber)].name === "NO EQUIPMENT") {
            return true;
        } else {
            return false;
        }
    } else {
        if (currentHeroBackpack[currentId].name === "NO EQUIPMENT") {
            return true;
        } else {
            return false;
        }
    }
}

function defineEmptyImageSrc(currentId) {
    switch (currentId) {
        case 'headSlot':
            return '../images/Equipment/emptyHead.png';
        case 'rightHandSlot':
            return '../images/Equipment/emptyWeapon.png';
        case 'chestSlot':
            return '../images/Equipment/emptyArmor.png';
        case 'armsSlot':
            return '../images/Equipment/emptyArms.png';
        case 'leftHandSlot':
            return '../images/Equipment/emptyShield.png';
        case 'feetSlot':
            return '../images/Equipment/emptyFeet.png';
        default:
            return '../images/Equipment/emptyEquipmentSlot.png';
    }
}