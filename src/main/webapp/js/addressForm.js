//Validazione dell'indirizzo front-end con RegExp
function addressFormValidation() {
    const capPattern = /^\d{5}$/;
    const cap = document.getElementById("cap")
    const errorText = document.getElementById("error-text")
    if(!capPattern.test(cap.value) || cap.value.trim() === '') {
        errorText.classList.remove("remove-item")
        errorText.innerHTML = "CAP non valido"
        return false;
    } else {
        return true
    }
}