package Model;

public class CreditCard {
    private String numero;
    private String intestatario;
    private String cvc;
    private String dataScadenza;
    private boolean isExpired;
    private int id;

    public CreditCard(String numero, String intestatario, String cvc, String dataScadenza) {
        this.numero = numero;
        this.intestatario = intestatario;
        this.cvc = cvc;
        this.dataScadenza = dataScadenza;
    }

    public CreditCard(String numero, String intestatario, String cvc, String dataScadenza, int id) {
        this.numero = numero;
        this.intestatario = intestatario;
        this.cvc = cvc;
        this.dataScadenza = dataScadenza;
        this.id = id;
    }

    public CreditCard(String numero, String intestatario, String cvc, String dataScadenza, boolean isExpired, int id) {
        this.numero = numero;
        this.intestatario = intestatario;
        this.cvc = cvc;
        this.dataScadenza = dataScadenza;
        this.isExpired = isExpired;
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(String intestatario) {
        this.intestatario = intestatario;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}