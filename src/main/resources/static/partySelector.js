let selectedCharacters = [];

function selectCharacter(characterNameAndSurname) {
    let currentElement = document.getElementById(characterNameAndSurname);
    console.log(currentElement.className);
    currentElement.className === 'unselected' ? addCharacterToParty(characterNameAndSurname) : removeCharacterFromParty(characterNameAndSurname);
}

function addCharacterToParty(characterNameAndSurname) {
    let currentElement = document.getElementById(characterNameAndSurname);
    selectedCharacters.push(characterNameAndSurname);
    currentElement.className = 'selected';
    console.log(currentElement.className);
    console.log('HeroAdded' + selectedCharacters.join('\n'));
    return 'selected';
}

function removeCharacterFromParty(characterNameAndSurname) {
    let currentElement = document.getElementById(characterNameAndSurname);
    selectedCharacters = selectedCharacters.filter(e => e !== characterNameAndSurname);
    currentElement.className = 'unselected';
    console.log(currentElement.className);
    console.log('HeroRemoved' + selectedCharacters.join('\n'));
    return 'unselected';
}

function accept() {
    let request = new XMLHttpRequest();
    const url = `http://localhost:8080/partySelector`
    request.open("POST", url, true);
    request.setRequestHeader('Content-Type', 'application/json');
    const json = JSON.stringify(selectedCharacters);
    request.send(json)
    //todo: redirect to main menu
}