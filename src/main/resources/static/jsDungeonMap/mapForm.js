let form = document.forms[0]

function validate() {

    let width = form["width"].value
    let height = form["height"].value

    if (width == '' || height == '') {
        let inputs = form.elements

        for (const element of inputs) {
            if (element.value == '') alertByBorderColor(element)
        }

        alert('Please enter the missing dimensions')
        return false
    }

}

function alertByBorderColor(element) {
    element.style.border = (`2px solid red`)
    element.addEventListener('change', inputChange)
}

function inputChange(event) {
    if (event.target.value != '') {
        event.target.style.border = (``)
        event.target.removeEventListener('change', inputChange)
    }
}
