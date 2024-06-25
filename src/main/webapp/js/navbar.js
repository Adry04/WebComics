//Menu a cascata della navbar
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("category-manga").onmouseenter = function () {
        document.getElementById("list-category-manga").classList.add("display-on");
    }
    document.getElementById("category-fumetti").onmouseenter = function () {
        document.getElementById("list-category-fumetti").classList.add("display-on");
    }
    document.getElementById("category-manga").onmouseleave = function() {
        document.getElementById("list-category-manga").classList.remove("display-on");
    }
    document.getElementById("category-fumetti").onmouseleave = function () {
        document.getElementById("list-category-fumetti").classList.remove("display-on");
    }
})