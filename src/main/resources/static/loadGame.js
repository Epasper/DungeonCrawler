
function loadGame(numberToLoad) {
    let numberOfSavedGame = numberToLoad.slice(11);
    location.href = `http://localhost:8080/getAllSaves?numberToLoad=${numberOfSavedGame}`;
}