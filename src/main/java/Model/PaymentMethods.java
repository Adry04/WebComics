//Bean dei metodi di pagamento
package Model;

import java.util.List;

public class PaymentMethods {
    private List<CreditCard> creditCards;
    private List<BankAccount> bankAccounts;

    public PaymentMethods(List<CreditCard> creditCards, List<BankAccount> bankAccounts) {
        this.creditCards = creditCards;
        this.bankAccounts = bankAccounts;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
