function sendAjaxRequest(heroClass) {
    sendAjaxArmorRequest(heroClass);
    sendAjaxWeaponRequest(heroClass);
}

function sendAjaxArmorRequest(heroClass) {
    fetch(`http://localhost:8080/charClassToArmor`, {
            method: 'get'
        })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            data.forEach(e => {
                let firstSelect = document.getElementById('selectArmor' + e);
                firstSelect.className = 'disabled';
            });
            const response = await fetch(`http://localhost:8080/charClassToArmor/` + heroClass);
            const result = await response.json();
            console.log(result);
            result.forEach(elem => {
                let chainedSelect = document.getElementById('selectArmor' + elem);
                chainedSelect.disabled = false;
                chainedSelect.className = 'selection selection-primary armorImg';
            });
        });
}

function sendAjaxWeaponRequest(heroClass) {
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
            const response = await fetch(`http://localhost:8080/charClassToWeapon/` + heroClass);
            const result = await response.json();
            console.log(result);
            result.forEach(elem => {
                let chainedSelect = document.getElementById('selectWeapon' + elem);
                chainedSelect.disabled = false;
                chainedSelect.className = 'selection selection-primary weaponImg';
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
                let firstSelect = document.getElementById('selectArmor' + e);
                firstSelect.className = 'disabled';
            });
            let firstSelect = document.getElementById('selectArmor' + armorName);
            firstSelect.className = 'selection selection-success armorImg';
        });
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
}

function saveCharacter(){
    const name = document.getElementById('heroName').value;
    const surname = document.getElementById('heroSurname').value;
    const weaponName = document.getElementsByClassName('selection selection-success weaponImg')[0].innerText;
    const armorName = document.getElementsByClassName('selection selection-success armorImg')[0].innerText;
    debugger;
}