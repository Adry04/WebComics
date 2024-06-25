//Pulizia e correttezza delle stringhe email e password
const validateForm = function () {
    const email = document.getElementById("email-input").value;
    const password = document.getElementById("password-input").value;
    const emailPattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]+\.[a-zA-Z]{2,}$/;
    const passwordPattern = /^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z]).{6,}$/;
    if (!emailPattern.test(email)) {
        alert("L'email non è valida");
        return false;
    } if (!passwordPattern.test(password)) {
        alert("La password deve contenere almeno 6 caratteri, una maiuscola, un carattere speciale e un numero");
        return false;
    }
    return true;
}