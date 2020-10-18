function initialize() {
    document.onload = heroes.forEach(element => {
        const id = 'hero:' + element.encounterXPosition + '---' + element.encounterYPosition;
        const image = '../images/' + element.heroClass + '.png';
        console.log(id + "|||" + image);
        let heroDiv = document.getElementById(id);
        heroDiv.style.zIndex = "3";
        heroDiv.src = image;
    });
    console.log('initialized!');
}