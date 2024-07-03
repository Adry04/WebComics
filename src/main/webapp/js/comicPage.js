const quantity = document.getElementById("quantity");

function incrementQuantity () {
    if (isNaN(parseInt(quantity.innerHTML, 10))) {
        console.log("Errore, Il valore non è un numero");
    }
    quantity.innerHTML = parseInt(quantity.innerHTML, 10) + 1;
}

function decrementQuantity () {
    if(parseInt(quantity.innerHTML) > 1) {
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        quantity.innerHTML = parseInt(quantity.innerHTML, 10) - 1;
    }
}