let currentlySelectedStartingSlot;
let currentlySelectedEquipment;
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
    currentlySelectedStartingSlot = currentId;
    if (currentId.includes('baggage')) {
        const baggageNumber = currentId.substring(8);
        console.log(baggageNumber);
        currentlySelectedEquipment = currentHeroBackpack.baggage[parseInt(baggageNumber)];
    } else {
        currentlySelectedEquipment = currentHeroBackpack[currentId];
    }
    // console.log(currentId)
    // console.log(currentlySelectedEquipment)
}

function drop(event) {
    const currentId = event.target.id;
    if (currentId.includes('baggage')) {
        const baggageNumber = currentId.substring(8);
        currentHeroBackpack.baggage[parseInt(baggageNumber)] = currentlySelectedEquipment;
    } else {
        currentHeroBackpack[currentId] = currentlySelectedEquipment;
    }
    if (currentlySelectedStartingSlot.includes('baggage')) {
        const baggageNumber = currentlySelectedStartingSlot.substring(8);
        currentHeroBackpack.baggage[parseInt(baggageNumber)] = emptySlot;
    } else {
        currentHeroBackpack[currentlySelectedStartingSlot] = emptySlot;
    }
    currentlySelectedEquipment = null;
    currentlySelectedStartingSlot = null;
    event.preventDefault();
    // console.log(event)
    console.log(currentHeroBackpack)
    // console.log(event.target.id)
}