import * as utils from './mapUtils.js'

export let currentMenu = null;
let currentButtonAwaitingConfirmation = null;
let confirmTextBackup = '';
let cancelTextBackup = '';
let tooltipTextBackups = new Map();

//========================= TOOLTIPS =========================

function getActiveTooltip() {
    if (!currentButtonAwaitingConfirmation) return null;

    const currentSlotDiv = currentButtonAwaitingConfirmation.parentElement;
    const currentSlotLabels = utils.getChildNodeByIdPartialString(currentSlotDiv, 'labels');

    return utils.getChildNodeByIdPartialString(currentSlotLabels, 'tooltip');
}

function showTooltip(tooltipText = 'default') {
    if (!currentButtonAwaitingConfirmation) return;
    const tooltipDiv = getActiveTooltip();

    if (tooltipText !== 'default') {
        const p = tooltipDiv.querySelector('p');
        tooltipTextBackups.set(tooltipDiv.id, p.innerText);
        p.innerText = tooltipText;
    }
    tooltipDiv.classList.remove('hidden');
}

function hideTooltip() {
    if (!currentButtonAwaitingConfirmation) return;
    const tooltipDiv = getActiveTooltip();

    tooltipDiv.classList.add('hidden');
    const p = tooltipDiv.querySelector('p');
    if (tooltipTextBackups.get(tooltipDiv.id)) {
        p.innerText = tooltipTextBackups.get(tooltipDiv.id);
        tooltipTextBackups.delete(tooltipDiv.id);
    }
}


//========================= SLOT ACTIONS =========================

function showConfirmationButtons(targetButton) {
    currentButtonAwaitingConfirmation = targetButton;
    targetButton.classList.add('button-await-confirmation');
}

export function exitConfirmation() {
    if (!currentButtonAwaitingConfirmation) return;

    const currentSlotDiv = currentButtonAwaitingConfirmation.parentElement;
    let cancelButton = utils.getChildNodeByIdPartialString(currentSlotDiv, 'cancel');

    const cancelledBtnId = cancelButton.dataset.cancelling;
    const cancelledButton = utils.getMappedElementById(cancelledBtnId);
    cancelledButton.classList.remove('button-await-confirmation');

    hideTooltip();

    currentButtonAwaitingConfirmation = null;
}

export function confirmableButtonClicked({ target: clickedButton }) {
    exitConfirmation();
    showConfirmationButtons(clickedButton);

    if (clickedButton.id.includes('save')) {
        const slotNumber = clickedButton.parentElement.dataset.slot;
        const correspondingLoadButton = utils.getMappedElementById(`load-${slotNumber}-btn`);
        if (correspondingLoadButton.disabled) {
            showTooltip(`Create new save file in ${clickedButton.innerText}`);
        } else {
            showTooltip();
        }
    } else {
        showTooltip();
    }
}

async function getSaveInfo(saveSlotNumber) {
    const response = await axios.get(`http://localhost:8080/getSaveInfo?saveSlotNumber=${saveSlotNumber}`);
    const saveInfo = response.data;

    console.log(saveInfo);
    return saveInfo;
}

async function setInfoForSlotDiv(slotDiv, infoText = 'default') {
    const labelsDiv = utils.getChildNodeByIdPartialString(slotDiv, 'labels');
    const infoDiv = utils.getChildNodeByIdPartialString(labelsDiv, 'info');
    const p = infoDiv.querySelector('p');

    if (infoText === 'default') {
        p.innerText = await getSaveInfo(slotDiv.dataset.slot)
    } else {
        p.innerText = infoText;
    }
}
//========================= SAVE BUTTON ACTIONS =========================

export function prepareSaveDeletion({ target: deleteButton }) {
    deleteButton.style.backgroundColor = 'red';
    deleteButton.dataset.status = "active";

    exitConfirmation();

    const saveSlotButtons = Array.from(document.getElementsByClassName('button-save'));
    saveSlotButtons.forEach(saveButton => {
        const currentSlotDiv = saveButton.parentElement;
        const currentSlotNumber = currentSlotDiv.dataset.slot;
        saveButton.classList.add('button-delete');

        const confirmButton = utils.getChildNodeByIdPartialString(currentSlotDiv, 'confirm');
        confirmButton.removeEventListener('click', saveButtonConfirmed);
        confirmButton.addEventListener('click', saveButtonDelete);
        confirmTextBackup = confirmButton.dataset.text;
        confirmButton.dataset.text = ' Confirm ';
        confirmButton.innerText = '🗑️';

        const cancelButton = utils.getChildNodeByIdPartialString(currentSlotDiv, 'cancel');
        cancelTextBackup = cancelButton.dataset.text;
        cancelButton.dataset.text = ' Keep ';
        cancelButton.innerText = '✅';

        const tooltipDiv = utils.getMappedElementById(`save-${currentSlotNumber}-tooltip`)
        const p = tooltipDiv.querySelector('p');
        tooltipTextBackups.set(tooltipDiv.id, p.innerText);
        p.innerText = `Delete save file from ${saveButton.innerText}`;

        saveButton.disabled = utils.getMappedElementById(`load-${currentSlotNumber}-btn`).disabled;
    })

    deleteButton.removeEventListener('click', prepareSaveDeletion);
    deleteButton.addEventListener('click', exitSaveDeletion);
}

export function exitSaveDeletion() {
    const deleteButton = utils.getMappedElementById('save-delete-btn');
    const delBtnStatus = deleteButton.dataset.status;
    if (delBtnStatus != "active") return;

    exitConfirmation();

    deleteButton.style.backgroundColor = '';
    deleteButton.dataset.status = "inactive";

    const saveSlotButtons = Array.from(document.getElementsByClassName('button-save'));
    saveSlotButtons.forEach(saveButton => {
        const currentSlotDiv = saveButton.parentElement;
        const currentSlotNumber = currentSlotDiv.dataset.slot;
        saveButton.classList.remove('button-delete');
        saveButton.disabled = false;

        const confirmButton = utils.getChildNodeByIdPartialString(currentSlotDiv, 'confirm');
        confirmButton.removeEventListener('click', saveButtonDelete);
        confirmButton.addEventListener('click', saveButtonConfirmed);
        confirmButton.dataset.text = confirmTextBackup;
        confirmButton.innerText = '✔️';

        const cancelButton = utils.getChildNodeByIdPartialString(currentSlotDiv, 'cancel');
        cancelButton.dataset.text = cancelTextBackup;
        cancelButton.innerText = '❌';

        const tooltipDiv = utils.getMappedElementById(`save-${currentSlotNumber}-tooltip`)
        const p = tooltipDiv.querySelector('p');
        p.innerText = tooltipTextBackups.get(tooltipDiv.id);
        tooltipTextBackups.delete(tooltipDiv.id);
    })
    confirmTextBackup = '';
    cancelTextBackup = '';
    deleteButton.removeEventListener('click', exitSaveDeletion);
    deleteButton.addEventListener('click', prepareSaveDeletion);
}

export async function saveButtonConfirmed({ target: saveConfirmationBtn }) {
    const saveSlotNumber = saveConfirmationBtn.parentElement.dataset.slot;
    console.log('save: ', saveConfirmationBtn, 'number: ', saveSlotNumber)
    await axios.get(`http://localhost:8080/saveMap?saveSlotNumber=${saveSlotNumber}`);

    const correspondingLoadButton = utils.getMappedElementById(`load-${saveSlotNumber}-btn`);
    if (correspondingLoadButton.disabled) {
        notify('Save created!');
    } else {
        notify('Save overwritten!');
    }

    updateLoadButtons();
    exitConfirmation();
}

async function saveButtonDelete({ target: clickedConfirmButton }) {
    const currentSlotDiv = clickedConfirmButton.parentElement;
    const saveSlotNumber = currentSlotDiv.dataset.slot;
    console.log('delete slot number: ', saveSlotNumber);

    await axios.get(`http://localhost:8080/deleteSave?saveSlotNumber=${saveSlotNumber}`);

    const saveButton = utils.getMappedElementById(`save-${saveSlotNumber}-btn`);
    saveButton.disabled = true;

    notify('Save file deleted!');
    updateLoadButtons();
    exitConfirmation();
}


//========================= LOAD BUTTON ACTIONS =========================

export async function loadButtonConfirmed({ target: loadSlotBtn }) {
    const loadSlotNumber = loadSlotBtn.parentElement.dataset.slot;
    console.log('load: ', loadSlotBtn, 'number: ', loadSlotNumber);

    window.location.replace(`http://localhost:8080/loadMap?loadSlotNumber=${loadSlotNumber}`);
}

export async function updateLoadButtons() {
    //TODO: aktualnie wszystkie load-buttony są aktualizowane po kliknięciu w guzik. A wystarczyłoby zaktualizować tylko ten, który akurat zapisano

    const loadButtons = Array.from(document.getElementsByClassName('button-load'));
    let emptySlotsCounter = 0;

    for (const loadButton of loadButtons) {
        if (await updateLoadButton(loadButton)) emptySlotsCounter++;
    }
    await updateLoadButton(utils.getMappedElementById('load-q-btn'));

    console.log('empty slots: ', emptySlotsCounter, ' number of load buttons: ', loadButtons.length)

    const deleteSaveButton = utils.getMappedElementById('save-delete-btn');
    if (emptySlotsCounter == loadButtons.length) {
        exitSaveDeletion();
        deleteSaveButton.disabled = true;
    } else {
        deleteSaveButton.disabled = false;
    }
}

async function updateLoadButton(button) {
    let elementWithSlotInfo;
    if (button.parentElement.dataset.slot) {
        elementWithSlotInfo = button.parentElement;
    } else {
        elementWithSlotInfo = button;
    }

    const slotNumber = elementWithSlotInfo.dataset.slot;
    const { data: slotExists } = await axios.get(`http://localhost:8080/checkIfSaveExists?slotNumber=${slotNumber}`);

    button.disabled = !slotExists;

    const saveSlotDiv = utils.getMappedElementById(`save-${slotNumber}-slot`);
    const loadSlotDiv = utils.getMappedElementById(`load-${slotNumber}-slot`);
    await setInfoForSlotDiv(saveSlotDiv);
    await setInfoForSlotDiv(loadSlotDiv);

    //additional setting info for quick-load button in main menu
    if (slotNumber === '0')
    {
        const loadSlotDiv = utils.getMappedElementById(`load-q-slot`);
        await setInfoForSlotDiv(loadSlotDiv);
    }

    return button.disabled;
}


//========================= MAIN MENU ACTIONS =========================

export function menuButtonEntered() {
    if (currentMenu) return;

    const menu = utils.getMappedElementById('main-menu');

    if (menu.classList.length == 1) return;

    menu.classList.remove('hidden');
    menu.classList.add('partial');
}

export function menuButtonExited() {
    const menu = utils.getMappedElementById('main-menu');

    if (menu.classList.length == 1) return;

    menu.classList.remove('partial');
    menu.classList.add('hidden');
}

export function menuClicked() {
    const mainMenu = utils.getMappedElementById('main-menu');

    //hideMenu(saveMenu);
    if (currentMenu) {
        exitSaveDeletion();
        exitConfirmation();
        mainMenu.classList.add('partial');
        hideMenu(currentMenu);
        currentMenu = null;
    } else {
        showMenu(mainMenu);
    }
}

export async function requestQuickSave() {
    await axios.get(`http://localhost:8080/saveMap?saveSlotNumber=${0}`);
    updateLoadButtons();
    notify('Quick save successful!');
}

export async function requestQuickLoad() {
    window.location.replace(`http://localhost:8080/loadMap?loadSlotNumber=${0}`);
}

export function saveMenuClicked() {
    const mainMenu = utils.getMappedElementById('main-menu');
    const saveMenu = utils.getMappedElementById('save-menu');

    hideMenu(mainMenu);
    showMenu(saveMenu);
}

export function loadClicked() {
    const mainMenu = utils.getMappedElementById('main-menu');
    const loadMenu = utils.getMappedElementById('load-menu');

    hideMenu(mainMenu);
    showMenu(loadMenu);
}

export function backButtonClicked() {

    exitSaveDeletion();
    exitConfirmation();
    hideMenu(currentMenu);

    if (currentMenu === utils.getMappedElementById('main-menu')) {
        currentMenu = null;
    } else {
        showMenu(utils.getMappedElementById('main-menu'));
    }
}

function showMenu(menu) {
    menu.classList.remove('hidden');
    menu.classList.remove('partial');
    currentMenu = menu;
}

function hideMenu(menu) {
    menu.classList.add('hidden');
}

