let selectedCharacters = [];

function selectCharacter(characterNameAndSurname){
    selectedCharacters.push(characterNameAndSurname)
    this.disabled=true;
    console.log(characterNameAndSurname);
}