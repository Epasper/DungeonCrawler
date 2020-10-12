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

function test() {
    console.log(currentHeroBackpack)
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
        console.log(baggageNumber);
        currentlySelectedEquipment = currentHeroBackpack.baggage[parseInt(baggageNumber)];
    } else {
        currentlySelectedEquipment = currentHeroBackpack[currentId];
    }
}
function drop(event) {
    const currentId = event.target.id;
    if (!draggedFromElement || currentId === draggedFromId) {
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

function defineDraggedFromEmptySlot(currentId) {
    console.log(currentHeroBackpack);
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
    console.log(currentId);
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