const changeQuantityUrl = window.location.origin + "/tswProject_war_exploded/cart"
let requestInProgress = false; // Variabile per tracciare lo stato della richiesta
const counterCarts = document.getElementById("counter-carts")

function incrementQuantity (isbn, comic) {
    if(!requestInProgress) {
        requestInProgress = true
        const quantity = document.getElementById("quantity-cart-" + isbn)
        console.log(quantity.innerHTML)
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        let newQuantity = parseInt(quantity.innerHTML, 10) + 1;
        const xhttp = new XMLHttpRequest()
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4) {
                requestInProgress = false
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) + 1
                quantity.innerHTML = newQuantity.toString();
            } else if (this.readyState === 4 && this.status === 400) {
                checkErrorDisplay("Errore di quantità")
            }
        }
        xhttp.open("POST", changeQuantityUrl, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn + "&quantita=1&comic=" + JSON.stringify(comic) + "&requestType=add&actual-quantity=" + quantity.innerHTML);
    }
}

function decrementQuantity (isbn, comic) {
    if(!requestInProgress) {
        requestInProgress = true
        const quantity = document.getElementById("quantity-cart-" + isbn)
        const comicCard = document.getElementById("cart-card-comic-" + isbn)
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        let newQuantity = parseInt(quantity.innerHTML, 10) - 1;
        const xhttp = new XMLHttpRequest()
        xhttp.onreadystatechange = function () {
            if(this.readyState === 4) {
                requestInProgress = false
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) - 1
                quantity.innerHTML = newQuantity.toString();
                if(newQuantity <= 0) {
                    comicCard.style.display = 'none'
                }
            } else if (this.readyState === 4 && this.status === 400) {
                checkErrorDisplay("Errore di quantità")
            }
        }
        xhttp.open("POST", changeQuantityUrl, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn + "&quantita=1&comic=" + JSON.stringify(comic) + "&requestType=decrease&actual-quantity=" + quantity.innerHTML);
    }
}

function remove (isbn, comic) {
    if(!requestInProgress) {
        requestInProgress = true
        const quantity = document.getElementById("quantity-cart-" + isbn)
        const comicCard = document.getElementById("cart-card-comic-" + isbn)
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        let actualQuantity = quantity.getAttribute("data-quantity")
        const xhttp = new XMLHttpRequest()
        xhttp.onreadystatechange = function () {
            if(this.readyState === 4) {
                requestInProgress = false
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) - actualQuantity
                comicCard.style.display = 'none'
            }
        }
        xhttp.open("POST", changeQuantityUrl, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn + "&quantita=0&comic=" + JSON.stringify(comic) + "&requestType=remove");
    }
}