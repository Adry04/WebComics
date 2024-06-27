const quantity = document.getElementById("quantity")
function incrementQuantity () {
    quantity.innerHTML = parseInt(quantity.innerHTML, 10) + 1
    if (isNaN(quantity.innerHTML)) {
        console.log("Il valore non è un numero")
    }
}

function decrementQuantity () {
    if(parseInt(quantity.innerHTML) > 1) {
        quantity.innerHTML = parseInt(quantity.innerHTML, 10) - 1
        if (isNaN(quantity.innerHTML)) {
            console.log("Il valore non è un numero")
        }
    }
}