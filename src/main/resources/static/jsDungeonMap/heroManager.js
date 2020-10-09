import { updateButtons } from "./mapButtons.js";
import { draw } from "./mapRender.js";

let heroes = [];
export let selectedHero;

export function addHero(heroElement) {
    const alreadyIn = heroes.some(hero => { return hero.heroEl === heroElement });
    console.log(alreadyIn)
    console.log(heroes)

    if (!alreadyIn) {
        heroes.push({
            heroEl: heroElement,
            isSelected: false
        });
        heroElement.addEventListener('click', heroClick);
    }

    // selectionTile.addEventListener('click', function (event) {
    //     clickedOnTile(event.target)
    // })
    // selectionTile.addEventListener('mouseenter', function (event) {
    //     var ev = new Event('mouseenter')
    //     selectedTile.dispatchEvent(ev)
    // })
    // selectionTile.addEventListener('mouseleave', function (event) {
    //     var ev = new Event('mouseleave')
    //     selectedTile.dispatchEvent(ev)
    // })
}

function heroClick({ target: heroElement }) {     //Destrukturyzacja obiektu. Do funkcji wchodzi obiekt Event. 
    //{} oznacza że go destrukturyzujemy i pobieramy jego pole targetm a następnie przypisujemy je do zmiennej heroElement
    //console.log(event)
    
    //console.log(heroes);
    let [hero] = heroes.filter((hero) => {      //znowu destrukturyzacja - pobiera pierwszy element z przefiltrowanej tablicy i wrzuca go do zmiennej hero
        //console.log('checking for hero');
        return hero.heroEl === heroElement;
    });
    //console.log(hero);

    if (hero.isSelected) {
        hero.isSelected = false;
        console.log(`Hero: ${heroElement.id} was deselected`);
        selectedHero = null;
    } else {
        hero.isSelected = true;
        console.log(`Hero: ${heroElement.id} was selected`);
        selectedHero = heroElement;
    }

    updateButtons();
    draw();

}