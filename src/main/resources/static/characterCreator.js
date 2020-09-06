function sendAjaxRequest(heroClass) {
    sendAjaxArmorRequest(heroClass);
    sendAjaxWeaponRequest(heroClass);
}

function sendAjaxArmorRequest(heroClass) {
    fetch(`http://localhost:8080/charClassToArmor`, { method: 'get' })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            data.forEach(e => {
                let firstSelect = document.getElementById('selectArmor' + e);
                firstSelect.disabled = true;
                firstSelect.className = 'btn btn-secondary';
            });
            const response = await fetch(`http://localhost:8080/charClassToArmor/` + heroClass);
            const result = await response.json();
            console.log(result);
            result.forEach(elem => {
                let chainedSelect = document.getElementById('selectArmor' + elem);
                chainedSelect.disabled = false;
                chainedSelect.className = 'btn btn-primary';
            });
        });
}

function sendAjaxWeaponRequest(heroClass) {
    fetch(`http://localhost:8080/charClassToWeapon`, { method: 'get' })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            data.forEach(e => {
                let firstSelect = document.getElementById('selectWeapon' + e);
                firstSelect.disabled = true;
                firstSelect.className = 'btn btn-secondary';
            });
            const response = await fetch(`http://localhost:8080/charClassToWeapon/` + heroClass);
            const result = await response.json();
            console.log(result);
            result.forEach(elem => {
                let chainedSelect = document.getElementById('selectWeapon' + elem);
                chainedSelect.disabled = false;
                chainedSelect.className = 'btn btn-primary';
            });
        });
}

function selectArmor(armorName) {
    fetch(`http://localhost:8080/charClassToArmor`, { method: 'get' })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            data.forEach(e => {
                let firstSelect = document.getElementById('selectArmor' + e);
                firstSelect.disabled = true;
                firstSelect.className = 'btn btn-secondary';
            });
            let firstSelect = document.getElementById('selectArmor' + armorName);
            firstSelect.className = 'btn btn-success';
        });
}

function selectWeapon(weaponName) {
    fetch(`http://localhost:8080/charClassToWeapon`, { method: 'get' })
        .then(function (response) {
            return response.json();
        }).then(async function (data) {
            data.forEach(e => {
                let firstSelect = document.getElementById('selectWeapon' + e);
                firstSelect.disabled = true;
                firstSelect.className = 'btn btn-secondary';
            });
            let firstSelect = document.getElementById('selectWeapon' + weaponName);
            firstSelect.className = 'btn btn-success';
        });
}