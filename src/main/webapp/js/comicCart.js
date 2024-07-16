const changeQuantityUrl = window.location.origin + "/tswProject_war_exploded/cart"
let requestInProgress = false; // Variabile per tracciare lo stato della richiesta
const counterCarts = document.getElementById("counter-carts");
const cartComicContainer = document.getElementById("cart-comics-container")
const checkoutSection = document.getElementById("checkout-section")

//Incremento della quantità di un prodotto (non nel database) nel carrello in maniera asincrona
function incrementQuantity (isbn, comic, price) {
    if (!requestInProgress) {
        const prezzo = document.getElementById("prezzo");
        requestInProgress = true;
        const quantity = document.getElementById("quantity-cart-" + isbn);
        let totalPrice = checkoutSection.getAttribute("data-price")
        let actualQuantity = quantity.getAttribute("data-quantity");
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
            checkErrorDisplay("Errore generale riprovare più tardi")
        }
        let newQuantity = parseInt(quantity.innerHTML, 10) + 1;
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4) {
                requestInProgress = false;
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) + 1;
                quantity.setAttribute("data-quantity", (parseInt(actualQuantity, 10) + 1))
                quantity.innerHTML = newQuantity.toString();
                let newPrezzo = parseFloat(totalPrice) + parseFloat(price);
                newPrezzo = newPrezzo.toFixed(2);
                checkoutSection.setAttribute("data-price", newPrezzo)
                newPrezzo = newPrezzo.replace('.', ',');
                prezzo.innerHTML = newPrezzo + " €";
                prezzo.setAttribute("data-prezzo", newPrezzo);
                let sizeCartComics = cartComicContainer.getAttribute("data-size-cart-comics")
                cartComicContainer.setAttribute("data-size-cart-comics", parseInt(sizeCartComics, 10) +1)
            } else if (this.readyState === 4 && this.status === 400) {
                checkErrorDisplay("Errore di quantità");
            }
        }
        xhttp.open("POST", changeQuantityUrl, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn + "&quantita=1&comic=" + JSON.stringify(comic) + "&requestType=add&actual-quantity=" + quantity.innerHTML);
    }
}

//Decremento della quantità di un prodotto (non nel database) nel carrello in maniera asincrona
function decrementQuantity (isbn, comic, price) {
    if(!requestInProgress) {
        const prezzo = document.getElementById("prezzo");
        requestInProgress = true;
        const quantity = document.getElementById("quantity-cart-" + isbn);
        const comicCard = document.getElementById("cart-card-comic-" + isbn);
        let totalPrice = checkoutSection.getAttribute("data-price")
        let actualQuantity = quantity.getAttribute("data-quantity");
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
            checkErrorDisplay("Errore generale riprovare più tardi")
        }
        let newQuantity = actualQuantity - 1;
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if(this.readyState === 4) {
                requestInProgress = false;
            }
            if (this.readyState === 4 && this.status === 200) {
                counterCarts.innerHTML = parseInt(counterCarts.innerHTML, 10) - 1;
                quantity.setAttribute("data-quantity", (parseInt(actualQuantity, 10) - 1))
                quantity.innerHTML = newQuantity.toString();
                if(newQuantity <= 0) {
                    comicCard.style.display = 'none';
                }
                let newPrezzo = parseFloat(totalPrice) - parseFloat(price);
                newPrezzo = newPrezzo.toFixed(2);
                checkoutSection.setAttribute("data-price", newPrezzo)
                newPrezzo = newPrezzo.replace('.', ',');
                prezzo.innerHTML = newPrezzo + " €";
                prezzo.setAttribute("data-prezzo", newPrezzo);
                let sizeCartComics = cartComicContainer.getAttribute("data-size-cart-comics")
                cartComicContainer.setAttribute("data-size-cart-comics", parseInt(sizeCartComics, 10) -1)
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

//Rimozione del prodotto (non nel database) nel carrello in maniera asincrona
function remove (isbn, comic, price) {
    if(!requestInProgress) {
        requestInProgress = true;
        const prezzo = document.getElementById("prezzo");
        const quantity = document.getElementById("quantity-cart-" + isbn);
        const comicCard = document.getElementById("cart-card-comic-" + isbn);
        let totalPrice = checkoutSection.getAttribute("data-price")
        if (isNaN(parseInt(quantity.innerHTML, 10))) {
            console.log("Errore, Il valore non è un numero");
            checkErrorDisplay("Errore generale riprovare più tardi")
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
                let newPrezzo = parseFloat(totalPrice) - (parseFloat(price) * actualQuantity);
                newPrezzo = newPrezzo.toFixed(2);
                checkoutSection.setAttribute("data-price", newPrezzo)
                newPrezzo = newPrezzo.replace('.', ',');
                prezzo.innerHTML = newPrezzo + " €";
                prezzo.setAttribute("data-prezzo", newPrezzo);
                let sizeCartComics = cartComicContainer.getAttribute("data-size-cart-comics")
                cartComicContainer.setAttribute("data-size-cart-comics", parseInt(sizeCartComics, 10) - parseInt(quantity.getAttribute("data-quantity"), 10))
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