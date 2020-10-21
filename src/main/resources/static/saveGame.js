let heroTeam = [];

function loadAllSelectedHeroes(){
    const selectedElements = document.getElementsByClassName('selected');
    for (let item of selectedElements){
        heroTeam.push(item.id);
    }
}

function saveGame(heroTeam){
    let request = new XMLHttpRequest();
    const url = `http://localhost:8080/saveGame`
    request.open("POST", url, true);
    request.setRequestHeader('Content-Type', 'application/json');
    const json = JSON.stringify(heroTeam);
    request.send(json)
}