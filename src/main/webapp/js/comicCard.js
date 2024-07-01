const counterWishes = document.getElementById("counter-wishes");
let numberWishes = parseInt(counterWishes.getAttribute("data-wishes"), 10);
const url = window.location.origin + "/tswProject_war_exploded/wishlist"

 function toggleWish(isbn) {
     const wishContainer = document.getElementById("wish-container-"+isbn);
     const noWish = document.getElementById("no-wish-" + isbn);
     const wish = document.getElementById("wish-" + isbn);
     let isWished = wishContainer.getAttribute('data-is-wished');
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
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            counterCarts.innerHTML = parseInt(numberCarts, 10) + quantita;
            counterCarts.setAttribute("data-carts", parseInt(numberCarts, 10) + quantita);
        }
    }
    xhttp.open("POST", urlCart, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("ISBN=" + isbn + "&quantita=" + quantita + "&comic=" + JSON.stringify(comic) + "&requestType=add");
}