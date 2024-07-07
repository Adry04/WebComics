package Model;

public class BankAccount {
    private String intenstatario;
    private String IBAN;

    public BankAccount(String intenstatario, String IBAN) {
        this.intenstatario = intenstatario;
        this.IBAN = IBAN;
    }

    public String getIntenstatario() {
        return intenstatario;
    }

    public void setIntenstatario(String intenstatario) {
        this.intenstatario = intenstatario;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }
}