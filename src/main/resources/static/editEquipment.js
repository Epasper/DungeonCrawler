let currentlySelectedStartingSlot;
let currentlySelectedEquipment;

function test() {
    console.log(currentHeroBackpack)
}

function allowDrop(event) {
    event.preventDefault();
}

function drag(event) {
    let targetSlot
    const currentId = event.target.id;
    if (currentId.includes('baggage')){
        const baggageNumber = currentId.substring(8);
        console.log(baggageNumber);
        currentlySelectedEquipment = currentHeroBackpack.baggage[parseInt(baggageNumber)];
    } else {
        currentlySelectedEquipment = currentHeroBackpack[currentId];
    }
    // console.log(event)
    // console.log(currentHeroBackpack)
    console.log(currentId)
    console.log(currentlySelectedEquipment)
    event.dataTransfer.setData("text", event.target.id);
}

function drop(event) {
    event.preventDefault();
    console.log(event)
    console.log(event.target.id)
    var data = event.dataTransfer.getData("text");
    event.target.appendChild(document.getElementById(data));
}