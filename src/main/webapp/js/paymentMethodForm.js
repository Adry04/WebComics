const creditCard = document.getElementById("credit-card");
const bankAccount = document.getElementById("bank-account");
const errorText = document.getElementById("error-text");

const cvcPattern = /^[0-9]{3}$/;
const numberPattern = /^(?:[0-9]{4}[-\s]?){3}[0-9]{4}$/;
const datePattern = /^\d{2}\/\d{2}\/\d{4}$/;
const bankIbanPattern = /^[A-Z]{2}[0-9A-Z]{25}$/;
const ownerPattern = /^[a-zA-Zà-ÿÀ-ÿ'’\- ]+$/;
const bicPattern = /^[A-Z]{6}[0-9]{2}$/;

//Mostra il metodo di pagamento Carta di Credito
function showCreditCard() {
    creditCard.classList.add("show-form")
    creditCard.classList.remove("no-display-form")
    bankAccount.classList.remove("show-form")
    bankAccount.classList.add("no-display-form")
    errorText.classList.add("remove-item");
    errorText.innerHTML = "";
}

//Mostra il metodo di pagamento Conto Corrente
function showBankAccount() {
    bankAccount.classList.add("show-form")
    bankAccount.classList.remove("no-display-form")
    creditCard.classList.remove("show-form")
    creditCard.classList.add("no-display-form")
    errorText.classList.add("remove-item");
    errorText.innerHTML = "";
}

//Scelta mutuamente esclusiva del metodo di pagamento
window.onload = function() {
    let radios = document.getElementsByName('choose-method');
    for (let i = 0; i < radios.length; i++) {
        radios[i].addEventListener('change', getSelectedPaymentMethod);
    }
}

//Prende il metodo di pagamento scelto con il Radio Button
function getSelectedPaymentMethod() {
    let radios = document.getElementsByName('choose-method');
    for (let i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
}

//Validazione dei metodi di pagamento Front-end con RegExp
function controlForm() {
    const selectedMethod = getSelectedPaymentMethod()

    if(selectedMethod === "CreditCard") {
        // Card Attributes
        const cardNumber = document.getElementById("card-number")
        const cardOwner = document.getElementById("card-owner")
        const cardCvc = document.getElementById("cvc")
        const cardExpirationForm = document.getElementById("expiring")
        const [year, month, day] = cardExpirationForm.value.split("-");
        const cardExpiration = `${day}/${month}/${year}`;
        console.log(cardExpiration)
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
        } else if(!datePattern.test(cardExpiration) || cardExpiration.trim() === '') {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "La data inserita non è valida";
            return false
        } else {
            return true
        }
    } else if(selectedMethod === "BankAccount") {
        // BackAccount Attribute
        const bankAccountOwner = document.getElementById("bank-account-owner");
        const IBAN = document.getElementById("iban");
        const bic = document.getElementById("bic");
        if(!ownerPattern.test(bankAccountOwner.value) || bankAccountOwner.value.trim() === '') {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "Credenziali del proprietario non valide";
            return false;
        } else if (IBAN.value.trim() === '' || !bankIbanPattern.test(IBAN.value)) {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "IBAN non valido";
            return false;
        } else if (bic.value.trim() === '' || !bicPattern.test(bic.value)) {
            errorText.classList.remove("remove-item");
            errorText.innerHTML = "Codice BIC non valido";
            return false;
        } else {
            return true
        }
    } else {
        return false
    }
}