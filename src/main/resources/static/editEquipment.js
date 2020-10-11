function test() {
    console.log(currentHeroBackpack)
}

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    console.log(ev)
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    console.log(ev)
    var data = ev.dataTransfer.getData("text");
}