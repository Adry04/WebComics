const noWish = document.getElementById("no-wish");
const wish = document.getElementById("wish");

noWish.onclick = function () {
    noWish.classList.add("no-display")
    wish.classList.remove("no-display")
}

wish.onclick = function () {
    noWish.classList.remove("no-display")
    wish.classList.add("no-display")
}

