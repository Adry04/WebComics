const counterWishes = document.getElementById("counter-wishes");
let numberWishes = parseInt(counterWishes.getAttribute("data-wishes"), 10);
const url = window.location.origin + "/tswProject_war_exploded/wishlist"

 function toggleWish(isbn) {
     const wishContainer = document.getElementById("wish-container-"+isbn);
     const noWish = document.getElementById("no-wish-" + isbn);
     const wish = document.getElementById("wish-" + isbn);
     let isWished = wishContainer.getAttribute('data-is-wished');
     const comicCard = document.getElementById("comic-card-"+isbn);
     if(isWished === "false") {
         let xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function () {
             if (this.readyState === 4 && this.status === 200) {
                 noWish.classList.add("no-display");
                 wish.classList.remove("no-display");
                 wishContainer.setAttribute('data-is-wished', 'true');
                 numberWishes += 1
                 counterWishes.setAttribute("data-wishes", numberWishes);
                 counterWishes.innerHTML = numberWishes;
                 checkDisplay("ELEMENTO AGGIUNTO ALLA WISHLIST") //si trova dentro nav.js
             } else if (this.readyState === 4 && this.status === 500) {
                 alert("È necessario eseguire l'accesso")
             }
         }
         xhttp.open("POST", url, true);
         xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         xhttp.send("ISBN=" + isbn + "&requestType=add");
     } else {
         let xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function () {
             if (this.readyState === 4 && this.status === 200) {
                 noWish.classList.remove("no-display");
                 wish.classList.add("no-display");
                 wishContainer.setAttribute('data-is-wished', 'false');
                 numberWishes -= 1
                 counterWishes.setAttribute("data-wishes", numberWishes);
                 counterWishes.innerHTML = numberWishes;
                 if(document.URL.includes("wishlist")) {
                     comicCard.classList.add("no-display");
                 }
                 checkDisplay("ELEMENTO TOLTO DALLA WISHLIST") //si trova dentro nav.js
             }
         }
         xhttp.open("POST", url, true);
         xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         xhttp.send("ISBN=" + isbn + "&requestType=remove");
     }
}

const urlCart = window.location.origin + "/tswProject_war_exploded/cart"

function addCart(isbn, quantita, comic) {
    let counterCarts = document.getElementById("counter-carts")
    let numberCarts = counterCarts.getAttribute("data-carts")
    let quantity = 0;
    if(quantita > 0) {
        quantity = quantita;
    } else if (quantita === -1) {
        quantity = parseInt(document.getElementById("quantity").innerHTML, 10);
    }
    if (quantity < 1) {
        checkErrorDisplay("QUANTITA' NON VALIDA")
        return;
    }
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            counterCarts.innerHTML = parseInt(numberCarts, 10) + quantity;
            counterCarts.setAttribute("data-carts", parseInt(numberCarts, 10) + quantity)
            checkDisplay("ELEMENTO AGGIUNTO AL CARRELLO") //si trova dentro nav.js
        } else if (this.readyState === 4 && this.status === 400) {
            checkErrorDisplay("LA QUANTITA' NON PUO ESSERE NEGATIVA")
        }
    }
    xhttp.open("POST", urlCart, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("ISBN=" + isbn + "&quantita=" + quantity + "&comic=" + JSON.stringify(comic) + "&requestType=add");
}