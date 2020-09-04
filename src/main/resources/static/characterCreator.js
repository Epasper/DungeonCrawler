function sendAjaxRequest(heroClass){
    sendAjaxArmorRequest(heroClass);
    sendAjaxWeaponRequest(heroClass);
}

function sendAjaxArmorRequest(heroClass) {
    let select = document.getElementById("armors");
    select.innerHTML = ''

    fetch(`http://localhost:8080/charClassToArmor/` + heroClass)
        .then(function (response) {
            return response.json();
        })
        .then(function (result) {
            console.log(result);
            result.forEach(element => {
                let opt = element;
                let el = document.createElement("option");
                el.textContent = opt;
                el.value = opt;
                select.appendChild(el);
            })
        });;
}

function sendAjaxWeaponRequest(heroClass) {
    let select = document.getElementById("weapons");
    select.innerHTML = ''

    fetch(`http://localhost:8080/charClassToWeapon/` + heroClass)
        .then(function (response) {
            return response.json();
        })
        .then(function (result) {
            console.log(result);
            result.forEach(element => {
                let opt = element;
                let el = document.createElement("option");
                el.textContent = opt;
                el.value = opt;
                select.appendChild(el);
            })
        });;
}