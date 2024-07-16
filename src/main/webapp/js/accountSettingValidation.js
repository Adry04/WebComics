function dataValidation() {
    const emailPattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]+\.[a-zA-Z]{2,}$/;
    const email = document.getElementById("email-input").value
    const nome = document.getElementById("nome-input").value
    const cognome = document.getElementById("cognome-input").value
    const error = document.getElementById("error-text")
    if(email === "" || nome === "" || cognome === "") {
        error.classList.remove("remove-item")
        error.innerHTML = "Nessun campo può essere vuoto"
        return false
    }
    if(!emailPattern.test(email)) {
        error.classList.remove("remove-item")
        error.innerHTML = "Email non valida"
        return false
    }
    return true
}

function passwordValidation() {
    const passwordPattern = /^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{6,}$/;
    const oldPassword = document.getElementById("old-password").value
    const newPassword = document.getElementById("new-password").value
    const confirmNewPassword = document.getElementById("confirm-new-password").value
    const error = document.getElementById("error-password-text")
    if(oldPassword === "" || newPassword === "" || confirmNewPassword === "") {
        error.classList.remove("remove-item")
        error.innerHTML = "Nessun campo può essere vuoto"
        return false
    }
    if(newPassword !== confirmNewPassword) {
        error.classList.remove("remove-item")
        error.innerHTML = "Le due password devono corrispondere"
        return false
    }
    if(!passwordPattern.test(newPassword)) {
        error.classList.remove("remove-item")
        error.innerHTML = "La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero"
        return false
    }
    return true
}