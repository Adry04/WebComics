const quantity = document.getElementById("quantity");

//Incremento della quantità dell'elemento nella pagina del fumetto
function incrementQuantity () {
    if (isNaN(parseInt(quantity.innerHTML, 10))) {
        console.log("Errore, Il valore non è un numero");
    }
    quantity.innerHTML = parseInt(quantity.innerHTML, 10) + 1;
}

//Decremento della quantità dell'elemento nella pagina del fumetto
function decrementQuantity () {
    if(parseInt(quantity.innerHTML) > 1) {
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        quantity.innerHTML = parseInt(quantity.innerHTML, 10) - 1;
    }
}