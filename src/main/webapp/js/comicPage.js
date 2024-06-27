const quantity = document.getElementById("quantity")
function incrementQuantity () {
    quantity.innerHTML = parseInt(quantity.innerHTML) + 1
}

function decrementQuantity () {
    if(parseInt(quantity.innerHTML) > 0) {
        quantity.innerHTML = parseInt(quantity.innerHTML) - 1
    }
}