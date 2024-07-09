const creditCard = document.getElementById("credit-card")
const bankAccount = document.getElementById("bank-account")
const errorText = document.getElementById("error-text")

const cvcPattern = /^[0-9]{3}$/;
const numberPattern = /^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]$/;
const datePattern = /^\d{2}-\d{2}-\d{4}$/;
const bankIbanPattern = /^[A-Z]{2}\+[0-9]{25}]$/;
const ownerPattern = /^[A-Za-z]$/;

function showCreditCard() {
    creditCard.classList.add("show-form")
    creditCard.classList.remove("no-display-form")
    bankAccount.classList.remove("show-form")
    bankAccount.classList.add("no-display-form")
    errorText.classList.add("remove-item");
    errorText.innerHTML = "";
}
function showBankAccount() {
    bankAccount.classList.add("show-form")
    bankAccount.classList.remove("no-display-form")
    creditCard.classList.remove("show-form")
    creditCard.classList.add("no-display-form")
    errorText.classList.add("remove-item");
    errorText.innerHTML = "";
}

window.onload = function() {
    let radios = document.getElementsByName('choose-method');
    for (let i = 0; i < radios.length; i++) {
        radios[i].addEventListener('change', getSelectedPaymentMethod);
    }
}

function getSelectedPaymentMethod() {
    let radios = document.getElementsByName('choose-method');
    for (let i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            // Puoi anche fare altro con il valore selezionato, ad esempio assegnarlo a una variabile o mostrarlo in una pagina
            return radios[i].value;
        }
    }
}

function controlForm() {
    const selectedMethod = getSelectedPaymentMethod()

    if(selectedMethod === "CreditCard") {
        // Card Attribute
        const cardNumber = document.getElementById("card-number")
        const cardOwner = document.getElementById("card-owner")
        const cardCvc = document.getElementById("cvc")
        const cardExpiration = document.getElementById("expiring")
        if (!ownerPattern.test(cardOwner.value) || cardOwner.value.trim() === ''){
            errorText.classList.remove("remove-item")
            errorText.innerHTML = "Credenziali del proprietario non valide";
            return false
        } else if (!numberPattern.test(cardNumber.value) || cardNumber.value.trim() === '') {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "Numero della carta non valido";
            return false
        } else if (!cvcPattern.test(cardCvc.value) || cardCvc.value.trim() === '') {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "Codice CVC non valido";
            return false;
        } else if(!datePattern.test(cardExpiration.value) || cardExpiration.value.trim() === '') {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "La data inserita non è valida";
            return false
        } else {
            return true
        }
    } else if(selectedMethod === "BankAccount") {
        // BackAccount Attribute
        const bankAccountOwner = document.getElementById("bank-account-owner")
        const IBAN = document.getElementById("iban")
        if(!ownerPattern.test(bankAccountOwner.value) || bankAccountOwner.value.trim() === '') {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "Credenziali del proprietario non valide";
            return false
        } else if (IBAN.value.trim() === '' || !bankIbanPattern.test(IBAN.value)) {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "IBAN non valido";
            return false
        } else {
            return true
        }
    } else {
        return false
    }
}