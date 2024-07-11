package Model;

public class BankAccount {
    private String intenstatario;
    private String IBAN;
    private String bic;
    private int id;

    public BankAccount(String intenstatario, String IBAN, String bic, int id) {
        this.intenstatario = intenstatario;
        this.IBAN = IBAN;
        this.bic = bic;
        this.id = id;
    }

    public BankAccount(String intenstatario, String IBAN, String bic) {
        this.intenstatario = intenstatario;
        this.IBAN = IBAN;
        this.bic = bic;
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

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}