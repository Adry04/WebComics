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