const validateComic = function () {
    const ISBN = document.getElementById("email-input").value;
    const ISBNPattern = /^[0-9]{13}$/;
    if (!ISBNPattern.test(ISBN)) {
        alert("ISBN non conforme");
        return false;
    }
    return true;
}