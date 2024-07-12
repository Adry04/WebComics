function deleteCreditCard (id) {
    if(confirm("Vuoi davvero eliminare questa carta di credito?")){
        const creditCard = document.getElementById("credit-card-" + id)
        const titleCredit = document.getElementById("title-credit")
        const dataSize = titleCredit.getAttribute("data-size")
        const xhttp = new XMLHttpRequest();
        const url = window.location.origin + "/tswProject_war_exploded/payment-method";
        xhttp.onreadystatechange = function (){
            if(this.readyState ===4 && this.status === 200){
                creditCard.style.display = 'none';
                if((dataSize - 1) === 0) {
                    titleCredit.style.display = "none"
                } else {
                    titleCredit.setAttribute("data-size", (parseInt(dataSize, 10) - 1))
                }
            }
        }
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("id=" + id + "&requestType=delete&paymentType=carta");
    }
}

function deleteBankAccount (id) {
    if(confirm("Vuoi davvero eliminare questo conto?")){
        const bankAccount = document.getElementById("bank-account-" + id)
        const xhttp = new XMLHttpRequest();
        const titleBank = document.getElementById("title-conto")
        const dataSize = titleBank.getAttribute("data-size")
        const url = window.location.origin + "/tswProject_war_exploded/payment-method";
        xhttp.onreadystatechange = function (){
            if(this.readyState ===4 && this.status === 200){
                bankAccount.style.display = 'none';
            }
            if((dataSize - 1) === 0) {
                titleBank.style.display = "none"
            } else {
                titleBank.setAttribute("data-size", (parseInt(dataSize, 10) - 1))
            }
        }
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("id=" + id + "&requestType=delete&paymentType=conto");
    }
}