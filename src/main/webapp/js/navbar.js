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

function checkDisplay(text){
    let check = document.getElementById("check-box")
    let checkText = document.getElementById("check-box-text")
    if(!check.classList.contains("check-box-display")) {
        check.classList.add('check-box-display')
        checkText.innerHTML = text
        setTimeout(function () {
            check.classList.remove('check-box-display')
        }, 4000);
    }
}

function checkErrorDisplay(text){
    let check = document.getElementById("check-error-box")
    let checkText = document.getElementById("check-error-box-text")
    if(!check.classList.contains("check-box-display")) {
        check.classList.add('check-box-display')
        checkText.innerHTML = text
        setTimeout(function () {
            check.classList.remove('check-box-display')
        }, 4000);
    }
}