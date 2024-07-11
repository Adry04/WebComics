package Model;

public class BankAccount {
    private String intenstatario;
    private String IBAN;
    private int id;

    public BankAccount(String intenstatario, String IBAN, int id) {
        this.intenstatario = intenstatario;
        this.IBAN = IBAN;
        this.id = id;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}