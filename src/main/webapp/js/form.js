const svgOpen = document.getElementById("openEye");
const svgClose = document.getElementById("closeEye");
const password = document.getElementById("password-input");
const passwordConfirm = document.getElementById("password-confirm")

//Visualizza la password del'utente quando inserita nel form
function displayPassword() {
    svgOpen.classList.add("no-display")
    svgClose.classList.remove("no-display")
    password.type = "text"
    if(passwordConfirm) {
        passwordConfirm.type = "text"
    }
}

//Nascondi la password dell'utente quando inserita nel form
function hidePassword() {
    svgOpen.classList.remove("no-display");
    svgClose.classList.add("no-display");
    password.type = "password";
    if(passwordConfirm) {
        passwordConfirm.type = "password"
    }
}