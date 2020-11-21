let globalHeroClass = '';
let isWeaponSelected = false;
let isArmorSelected = false;
let isHeroClassSelected = false;
let areAllAttributePointsSpent = false;
let errorMessages = [];
let availableAttributePoints;
let allAttributeNames = [];
let allAttributes = {};

function initialize() {
    allAttributeNames.forEach(e => {
        let attrDiv = document.getElementById('attribute:' + e);
        attrDiv.value = 2;
        allAttributes[e] = 2;
    })
}

function selectHeroClass(heroClass) {
    sendAjaxArmorRequest(heroClass);
    sendAjaxWeaponRequest(heroClass);
    globalHeroClass = heroClass;
    isHeroClassSelected = true;
    isArmorSelected = false;
    isWeaponSelected = false;
}

function changeAttributePoints(attributeName) {
    const backupCurrentAttributePoints = allAttributes[attributeName];
    let attrDiv = document.getElementById('attribute:' + attributeName);
    let allPointsDiv = document.getElementById('number-of-points');
    console.log(allAttributes);
    console.log(availableAttributePoints);
    allAttributes[attributeName] = attrDiv.valueAsNumber;
    availableAttributePoints = 22 - Object.values(allAttributes).reduce((prev, curr) => {
        return prev + curr - 2;
    })
    console.log(availableAttributePoints);
    if (availableAttributePoints < 0) {
        availableAttributePoints = 0;
        attrDiv.value = backupCurrentAttributePoints;
        allAttributes[attributeName] = backupCurrentAttributePoints;
    }
    allPointsDiv.innerHTML = availableAttributePoints;
}

function validatePoints(attributeName) {
    return availableAttributePoints > 0 && allAttributes[attributeName] > 1 && allAttributes[attributeName] < 12;
}

function sendAjaxArmorRequest(heroClass) {
    let outerSelection;
    fetch(`http://localhost:8080/charClassToArmor/` + heroClass, {
        method: 'get'
    })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            outerSelection = data;
            data.forEach(e => {
                let selection = document.getElementById('selectArmor' + e);
                selection.disabled = false;
                selection.className = 'selection selection-primary armorImg';
            });
            const response = await fetch(`http://localhost:8080/charClassToArmor/`);
            const result = await response.json();
            result.forEach(elem => {
                let chainedSelect = document.getElementById('selectArmor' + elem.name);
                if (chainedSelect.className !== 'disabled' && !outerSelection.includes(elem.name)) {
                    chainedSelect.className = 'disabled';
                }
            });
        });
}

function sendAjaxWeaponRequest(heroClass) {
    let outerSelection;
    fetch(`http://localhost:8080/charClassToWeapon/` + heroClass, {
        method: 'get'
    })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            outerSelection = data;
            data.forEach(e => {
                let selection = document.getElementById('selectWeapon' + e);
                selection.disabled = false;
                selection.className = 'selection selection-primary weaponImg';
            });
            const response = await fetch(`http://localhost:8080/charClassToWeapon/`);
            const result = await response.json();
            result.forEach(elem => {
                let chainedSelect = document.getElementById('selectWeapon' + elem.name);
                if (chainedSelect.className !== 'disabled' && !outerSelection.includes(elem.name)) {
                    chainedSelect.className = 'disabled';
                }
            });
        });
}

function selectArmor(armorName) {
    fetch(`http://localhost:8080/charClassToArmor`, {
        method: 'get'
    })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            data.forEach(e => {
                let firstSelect = document.getElementById('selectArmor' + e.name);
                firstSelect.className = 'disabled';
            });
            let firstSelect = document.getElementById('selectArmor' + armorName);
            firstSelect.className = 'selection selection-success armorImg';
        });
    isArmorSelected = true;
}

function selectWeapon(weaponName) {
    fetch(`http://localhost:8080/charClassToWeapon`, {
        method: 'get'
    })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            data.forEach(e => {
                let firstSelect = document.getElementById('selectWeapon' + e.name);
                firstSelect.className = 'disabled';
            });
            let firstSelect = document.getElementById('selectWeapon' + weaponName);
            firstSelect.className = 'selection selection-success weaponImg';
        });
    isWeaponSelected = true;
}

function saveCharacter() {
    if (validateCharacter()) {
        let request = new XMLHttpRequest();
        const url = `http://localhost:8080/saveCharacter`
        request.open("POST", url, true);
        request.setRequestHeader('Content-Type', 'application/json');
        const name = document.getElementById('heroName').value;
        const surname = document.getElementById('heroSurname').value;
        const weaponName = document.getElementsByClassName('selection selection-success weaponImg')[0].innerText;
        const armorName = document.getElementsByClassName('selection selection-success armorImg')[0].innerText;
        const heroClass = globalHeroClass;
        console.log(allAttributes);
        const hero = {
            name: name,
            surname: surname,
            weaponName: weaponName,
            armorName: armorName,
            className: heroClass,
            strength: allAttributes.Strength,
            dexterity: allAttributes.Dexterity,
            stamina: allAttributes.Stamina,
            intelligence: allAttributes.Intelligence,
            willpower: allAttributes.Willpower,
            resilience: allAttributes.Resilience,
        }
        const heroJSON = JSON.stringify(hero);
        request.send(heroJSON);
        window.alert(`Character ${name} ${surname} has been saved to the database`);
    }
    else {
        window.alert(errorMessages.join('\n'));
    }
}

function validateCharacter() {
    errorMessages = [];
    let name = document.getElementById('heroName').value ? true : false;
    let surname = document.getElementById('heroSurname').value ? true : false;
    if (!name) {
        errorMessages.push('Please choose a name for your character.');
    }
    if (!surname) {
        errorMessages.push('Please choose a surname for your character.');
    }
    if (!isHeroClassSelected) {
        errorMessages.push('Please select a hero class for your character');
    }
    if (!isWeaponSelected) {
        errorMessages.push('Please select a starting weapon for your character');
    }
    if (!isArmorSelected)
        errorMessages.push('Please select a starting armor for your character');
    if (name && surname && isWeaponSelected && isArmorSelected && isHeroClassSelected) return true;
    return false;
}