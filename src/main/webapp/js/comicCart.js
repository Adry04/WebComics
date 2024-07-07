const changeQuantityUrl = window.location.origin + "/tswProject_war_exploded/cart"
let requestInProgress = false; // Variabile per tracciare lo stato della richiesta
const counterCarts = document.getElementById("counter-carts");
const cartComicContainer = document.getElementById("cart-comics-container")
const checkoutSection = document.getElementById("checkout-section")

function incrementQuantity (isbn, comic, price) {
    if (!requestInProgress) {
        const prezzo = document.getElementById("prezzo");
        const dataPrezzo = prezzo.getAttribute("data-prezzo");
        requestInProgress = true;
        const quantity = document.getElementById("quantity-cart-" + isbn);
        console.log(quantity.innerHTML);
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        let newQuantity = parseInt(quantity.innerHTML, 10) + 1;
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4) {
                requestInProgress = false;
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) + 1;
                quantity.innerHTML = newQuantity.toString();
                let newPrezzo = parseFloat(dataPrezzo) + parseFloat(price);
                newPrezzo = newPrezzo.toFixed(2);
                newPrezzo = newPrezzo.replace('.', ',');
                prezzo.innerHTML = newPrezzo + " €";
                prezzo.setAttribute("data-prezzo", newPrezzo);
                let sizeCartComics = cartComicContainer.getAttribute("data-size-cart-comics")
                cartComicContainer.setAttribute("data-size-cart-comics", parseInt((sizeCartComics + 1), 10))
            } else if (this.readyState === 4 && this.status === 400) {
                checkErrorDisplay("Errore di quantità");
            }
        }
        xhttp.open("POST", changeQuantityUrl, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn + "&quantita=1&comic=" + JSON.stringify(comic) + "&requestType=add&actual-quantity=" + quantity.innerHTML);
    }
}

function decrementQuantity (isbn, comic, price) {
    if(!requestInProgress) {
        const prezzo = document.getElementById("prezzo");
        const dataPrezzo = prezzo.getAttribute("data-prezzo");
        requestInProgress = true;
        const quantity = document.getElementById("quantity-cart-" + isbn);
        const comicCard = document.getElementById("cart-card-comic-" + isbn);
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        let newQuantity = parseInt(quantity.innerHTML, 10) - 1;
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if(this.readyState === 4) {
                requestInProgress = false;
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) - 1;
                quantity.innerHTML = newQuantity.toString();
                if(newQuantity <= 0) {
                    comicCard.style.display = 'none';
                }
                let newPrezzo = parseFloat(dataPrezzo) - parseFloat(price);
                newPrezzo = newPrezzo.toFixed(2);
                newPrezzo = newPrezzo.replace('.', ',');
                prezzo.innerHTML = newPrezzo + " €";
                prezzo.setAttribute("data-prezzo", newPrezzo);
                let sizeCartComics = cartComicContainer.getAttribute("data-size-cart-comics")
                cartComicContainer.setAttribute("data-size-cart-comics", parseInt((sizeCartComics - 1), 10))
                if(parseInt(cartComicContainer.getAttribute("data-size-cart-comics")) === 0) {
                    checkoutSection.style.display = 'none'
                }
            } else if (this.readyState === 4 && this.status === 400) {
                checkErrorDisplay("Errore di quantità");
            }
        }
        xhttp.open("POST", changeQuantityUrl, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn + "&quantita=1&comic=" + JSON.stringify(comic) + "&requestType=decrease&actual-quantity=" + quantity.innerHTML);
    }
}

function remove (isbn, comic, totalQuantity, price) {
    if(!requestInProgress) {
        requestInProgress = true;
        const prezzo = document.getElementById("prezzo");
        const dataPrezzo = prezzo.getAttribute("data-prezzo");
        const quantity = document.getElementById("quantity-cart-" + isbn);
        const comicCard = document.getElementById("cart-card-comic-" + isbn);
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
        }
        let actualQuantity = quantity.getAttribute("data-quantity");
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if(this.readyState === 4) {
                requestInProgress = false;
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) - actualQuantity;
                comicCard.style.display = 'none';
                let newPrezzo = parseFloat(dataPrezzo) - (parseFloat(price) * totalQuantity);
                newPrezzo = newPrezzo.toFixed(2);
                newPrezzo = newPrezzo.replace('.', ',');
                prezzo.innerHTML = newPrezzo + " €";
                prezzo.setAttribute("data-prezzo", newPrezzo);
                let sizeCartComics = cartComicContainer.getAttribute("data-size-cart-comics")
                cartComicContainer.setAttribute("data-size-cart-comics", parseInt((sizeCartComics - totalQuantity), 10))
                console.log(cartComicContainer.getAttribute("data-size-cart-comics"))
                if(parseInt(cartComicContainer.getAttribute("data-size-cart-comics")) === 0) {
                    checkoutSection.style.display = 'none'
                }
            }
        }
        xhttp.open("POST", changeQuantityUrl, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn + "&quantita=0&comic=" + JSON.stringify(comic) + "&requestType=remove");
    }
}

function onOrder () {
    if (!requestInProgress) {
        const urlOrder = window.location.origin + "/tswProject_war_exploded/order";
        requestInProgress = true;
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if(this.readyState === 4 && this.status === 200) {
                location.href = 'order';
            } else if(this.readyState === 4 && this.status === 400) {
                checkErrorDisplay("Errore con la creazione dell'ordine")
            } else if(this.readyState === 4 && this.status === 401) {
                checkErrorDisplay("Non ci sono prodotti nel carrello")
            }
        }
        xhttp.open("POST", urlOrder, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send();
    }
}