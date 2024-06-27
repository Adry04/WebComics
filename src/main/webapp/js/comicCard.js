const noWish = document.getElementById("no-wish");
const wish = document.getElementById("wish");
const wishContainer = document.getElementById("wish-container")

 function toggleWish() {
     let isWished = wishContainer.getAttribute('data-is-wished')
     let isbn = wishContainer.getAttribute("data-isbn")
     if(isWished === "false") {
         let xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function () {
             if (this.readyState === 4 && this.status === 200) {
                 noWish.classList.add("no-display");
                 wish.classList.remove("no-display");
             }
         }
         const url = window.location.origin + "/tswProject_war_exploded/wish-operation"
         xhttp.open("POST", url, true);
         xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         xhttp.send("ISBN=" + isbn + "&requestType=add")
         wishContainer.setAttribute('data-is-wished', 'true')
     } else {
         let xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function () {
             if (this.readyState === 4 && this.status === 200) {
                 noWish.classList.remove("no-display");
                 wish.classList.add("no-display");
             }
         }
         const url = window.location.origin + "/tswProject_war_exploded/wish-operation"
         xhttp.open("POST", url, true);
         xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         xhttp.send("ISBN=" + isbn + "&requestType=remove")
         wishContainer.setAttribute('data-is-wished', 'false')
     }
}